package com.psybrainy.producer.service.producer

import com.psybrainy.producer.config.CompanionLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessageProducer {

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, String>? = null

    fun sendMessage(topic: String?, message: String) {
        log.info("Sending message to Kafka topic: $topic, message: $message")
        kafkaTemplate?.send(topic!!, message)
        log.info("Sent message to Kafka topic: $topic, message: $message")
    }

    companion object : CompanionLogger()
}