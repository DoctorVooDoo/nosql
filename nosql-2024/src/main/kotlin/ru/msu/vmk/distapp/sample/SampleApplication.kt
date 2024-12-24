package ru.msu.vmk.distapp.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(
	exclude = [ElasticsearchDataAutoConfiguration::class]
)
class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}
