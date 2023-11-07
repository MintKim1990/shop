package product.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.domain.Product
import product.domain.ProductRepository
import product.service.request.ProductCreateRequest

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    suspend fun send() {
        kafkaTemplate.send("product-create", "makeProduct")
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