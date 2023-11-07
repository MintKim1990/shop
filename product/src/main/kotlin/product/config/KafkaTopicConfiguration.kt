package product.config

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.config.TopicConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfiguration {

    private val RETENTION_TIME = 1000 * 60 * 60

//    @Bean
//    fun productCreateTopic(): NewTopic {
//        return TopicBuilder.name("product-create")
//            .partitions(1)
//            .replicas(10)
//            .config(TopicConfig.RETENTION_MS_CONFIG, RETENTION_TIME.toString()) // 리텐션 설정
//            .build()
//    }

}