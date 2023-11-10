package product.service

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.mono
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import product.domain.Product
import product.domain.ProductRepository


@Component
class ProductCreateConsumer(
    private val objectMapper: ObjectMapper,
    private val productRepository: ProductRepository,
) {

    @KafkaListener(
        id = "product-create-id", // 컨슈머 그룹 ID
        topics = ["product-create"], // 토픽명
        clientIdPrefix = "product-create-listener", // 컨슈머 클라이언트 ID 프리픽스 설정
        concurrency = "1" // Listener 스레드 개수
    )
    fun listen(
        message: String,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String, // 헤더에서 메타정보 추출 가능
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) partitionId: Int, // 헤더에서 메타정보 추출 가능
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) messageKey: String, // 헤더에서 메타정보 추출 가능
        @Header(KafkaHeaders.OFFSET) offset: Long, // 헤더에서 메타정보 추출 가능
    ) {
        mono {
            val product = objectMapper.readValue(message, Product::class.java) // TODO Data Class로 변경
            productRepository.save(product)
            // TODO 상품정보 캐싱로직 추가
        }
    }
}
