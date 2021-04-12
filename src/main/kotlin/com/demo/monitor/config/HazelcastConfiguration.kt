package com.demo.monitor.config

import com.hazelcast.config.*
import com.hazelcast.spi.merge.PutIfAbsentMergePolicy
import de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration.DEFAULT_NAME_EVENT_STORE_MAP
import de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration.DEFAULT_NAME_SENT_NOTIFICATIONS_MAP
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class HazelcastConfiguration {
    @Value("\${monitor.k8s.service}")
    private lateinit var service: String

    @Bean
    fun hazelcastConfig(): Config {
        val eventStoreMap: MapConfig = MapConfig(DEFAULT_NAME_EVENT_STORE_MAP).setInMemoryFormat(InMemoryFormat.OBJECT)
            .setBackupCount(1)
            .setMergePolicyConfig(MergePolicyConfig(PutIfAbsentMergePolicy::class.java.name, 100))

        val sentNotificationsMap: MapConfig = MapConfig(DEFAULT_NAME_SENT_NOTIFICATIONS_MAP)
            .setInMemoryFormat(InMemoryFormat.OBJECT).setBackupCount(1)
            .setMaxSizeConfig(MaxSizeConfig().setMaxSizePolicy(MaxSizeConfig.MaxSizePolicy.PER_NODE))
            .setEvictionPolicy(EvictionPolicy.LRU)
            .setMergePolicyConfig(MergePolicyConfig(PutIfAbsentMergePolicy::class.java.name, 100))

        val config = Config()
        config.addMapConfig(eventStoreMap)
        config.addMapConfig(sentNotificationsMap)
        config.setProperty("hazelcast.jmx", "true")

        config.networkConfig.join.multicastConfig.isEnabled = false
        config.networkConfig.join.kubernetesConfig.isEnabled = true
        config.networkConfig.join.kubernetesConfig.properties["service-name"] = service
        config.networkConfig.join.kubernetesConfig.properties["use-node-name-as-external-address"] = "true"
        return config
    }
}