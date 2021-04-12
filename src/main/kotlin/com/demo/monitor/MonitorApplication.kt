package com.demo.monitor

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAdminServer
@EnableScheduling
@EnableDiscoveryClient
class MonitorApplication

fun main(args: Array<String>) {
    runApplication<MonitorApplication>(*args)
}
