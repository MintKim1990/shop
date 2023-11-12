package product.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.domain.Product
import product.domain.ProductRepository
import product.service.request.ProductCreateRequest
import java.math.BigDecimal

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {

    suspend fun send() {
        val product = Product(
            name = "상품명",
            quantity = 100,
            description = "상품설명",
            sellerNo = 1L,
            consumerPrice = BigDecimal.valueOf(1000L),
            imageUrl = ""
        )
        kafkaTemplate.send("product-create", "create", objectMapper.writeValueAsString(product))
    }

    @Transactional
    suspend fun create(request: ProductCreateRequest) {
        val product = Product(
            name = request.name,
            description = request.description,
            imageUrl = request.imageUrl,
            consumerPrice = request.consumerPrice,
            quantity = request.quantity,
            sellerNo = request.sellerNo
        )

        productRepository.save(product)
    }

}