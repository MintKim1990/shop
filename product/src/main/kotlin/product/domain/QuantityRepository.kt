package product.domain

import reactor.core.publisher.Mono

interface QuantityRepository {

    suspend fun save(quantity: Quantity): Mono<Boolean>

    suspend fun increase(quantities: List<Quantity>)

    suspend fun decrease(quantities: List<Quantity>)

    suspend fun delete(quantity: Quantity): Mono<Boolean>

}