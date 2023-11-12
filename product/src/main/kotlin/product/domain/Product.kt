package product.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("PRODUCT")
class Product(
    @Id
    var id: Long? = null,
    var name: String,
    var description: String? = null,
    var imageUrl: String,
    var consumerPrice: BigDecimal,
    var quantity: Int,
    val sellerNo: Long,
): BaseDate() {
}