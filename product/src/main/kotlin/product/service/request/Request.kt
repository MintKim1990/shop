package product.service.request

import java.math.BigDecimal

data class ProductCreateRequest(
    var name: String,
    var description: String? = null,
    var imageUrl: String,
    var consumerPrice: BigDecimal,
    var quantity: Int,
    val sellerNo: Long,
)