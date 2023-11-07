package product.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ProductCreateConsumer {

    @KafkaListener(id = "product-create-id", topics = ["product-create"])
    fun listen(message: String) {
        println(message)
    }

}