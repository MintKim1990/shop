package product.infra

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Repository
import product.domain.Quantity
import product.domain.QuantityRepository
import product.kLogger
import reactor.core.publisher.Mono

@Repository
class RedisQuantityRepository(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val reactiveValueOperations: ReactiveValueOperations<String, String> = reactiveRedisTemplate.opsForValue(),
    private val decreaseScript: RedisScript<String>,
    private val increaseScript: RedisScript<String>,
): QuantityRepository {


    private val log = kLogger()
    private val SUCCESSED = "OK"
    private val NOTEXIST = "NOTEXIST"
    private val SOLDOUT = "SOLDOUT"

    override suspend fun save(quantity: Quantity): Mono<Boolean> {
        return reactiveValueOperations.set(quantity.id.toString(), quantity.quantity.toString())
    }

    override suspend fun increase(quantities: List<Quantity>) {
        val execute = reactiveRedisTemplate.execute(increaseScript, getProductQuantityKeys(quantities), getProductQuantityValues(quantities))
            .awaitSingle()

        when(execute) {
            SUCCESSED -> log.debug("RedisQuantityRepository increase Successed")
            NOTEXIST -> throw IllegalArgumentException("상품수량정보가 존재하지 않습니다.")
        }
    }

    override suspend fun decrease(quantities: List<Quantity>) {
        val execute = reactiveRedisTemplate.execute(decreaseScript, getProductQuantityKeys(quantities), getProductQuantityValues(quantities))
            .awaitSingle()

        when(execute) {
            SUCCESSED -> log.debug("RedisQuantityRepository decrease Successed")
            NOTEXIST -> throw IllegalArgumentException("상품수량정보가 존재하지 않습니다.")
            SOLDOUT -> throw IllegalStateException("주문한 상품량이 재고량보다 큽니다.")
        }
    }

    override suspend fun delete(quantity: Quantity): Mono<Boolean> {
        return reactiveValueOperations.delete(quantity.id.toString())
    }

    private fun getProductQuantityKeys(quantities: List<Quantity>) = quantities.map { it.id.toString() }
    private fun getProductQuantityValues(quantities: List<Quantity>) = quantities.map { it.quantity.toString() }

}