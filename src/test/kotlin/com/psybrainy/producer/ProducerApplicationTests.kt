package com.psybrainy.producer

import com.psybrainy.producer.service.ProducerKafkaService
import kotlinx.coroutines.test.runTest
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["test-topic"])
class ProducerApplicationTests {

	@Autowired
	private lateinit var producerKafkaService: ProducerKafkaService

	@Autowired
	private lateinit var broker: EmbeddedKafkaBroker

	private lateinit var consumer: Consumer<String, String>

	@BeforeEach
	fun setup() {
		val consumerProps: MutableMap<String, Any> = KafkaTestUtils.consumerProps(
			"consumer_group_id",
			"true",
			broker
		)
		consumerProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
		val cf: ConsumerFactory<String, String> = DefaultKafkaConsumerFactory(consumerProps)
		consumer = cf.createConsumer()
		broker.consumeFromAnEmbeddedTopic(consumer, "test-topic")
	}

	@Test
	fun `test send message integrates with Kafka`() = runTest {

		val testMessage = "Hello Kafka!"
		producerKafkaService.execute(testMessage)

		val replies: ConsumerRecords<String, String> = KafkaTestUtils.getRecords(consumer)

		assertThat(replies.count()).isGreaterThanOrEqualTo(1)
		assertThat(replies.first().value()).isEqualTo(testMessage)
	}
}

