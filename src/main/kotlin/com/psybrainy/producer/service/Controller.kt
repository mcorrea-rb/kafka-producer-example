package com.psybrainy.producer.service

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class Controller(
    val producerKafkaService: ProducerKafkaService
) {

    @PostMapping("/kafka")
    suspend fun kafka() {
        producerKafkaService.execute("Hello Kafka!")
    }
}