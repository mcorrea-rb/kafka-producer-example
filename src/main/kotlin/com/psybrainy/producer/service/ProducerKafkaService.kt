package com.psybrainy.producer.service

import com.psybrainy.producer.config.CompanionLogger
import com.psybrainy.producer.service.producer.MessageProducer
import org.springframework.stereotype.Component

@Component
class ProducerKafkaService(
    private val messageProducer: MessageProducer,
) {

    fun execute(message: String) {
        val topic = "hola"
        try {

            log.info("Send risk assessment response: {} to Kafka topic: {}", message, topic)
            messageProducer.sendMessage(topic, message)
            log.info("Risk assessment response: {} sent to Kafka topic: {}", message, topic)
        } catch (ex: Exception){
            return
        }
    }

    companion object : CompanionLogger()
}