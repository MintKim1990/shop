package product.domain

data class Quantity(
    val id: Long,
    var quantity: Int,
) {

    init {
        check(quantity < 0) { "상품수량은 0보다 커야합니다." }
    }

}