package com.psybrainy.producer

import com.psybrainy.producer.service.ProducerKafkaService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.test.context.EmbeddedKafka
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["hola"])
class ProducerApplicationTests {

	@Autowired
	private lateinit var producerKafkaService: ProducerKafkaService

	private val records: BlockingQueue<String> = LinkedBlockingQueue()

	@KafkaListener(topics = ["hola"], groupId = "consumer_group_id" )
	fun listen(record: ConsumerRecord<String, String>) {
		records.add(record.value())
	}

	@Test
	fun `test send message integrates with Kafka`() {
		val testMessage = "Hello Kafka!"
		producerKafkaService.execute(testMessage)

		val receivedMessage = records.poll(10, TimeUnit.SECONDS)
		assert(testMessage == receivedMessage)
	}

}
