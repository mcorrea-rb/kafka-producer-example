package com.psybrainy.producer.service

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class Controler(
    val producerKafkaService: ProducerKafkaService
) {

    @PostMapping("/kafka")
    fun kafka() {

        producerKafkaService.execute("Hello Kafka!")
    }
}