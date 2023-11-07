package product.domain

import java.time.LocalDateTime

open class BaseDate(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
)