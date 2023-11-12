package product.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    private val DECREASE_QUANTITY_SCRIPT_PATH = "scripts/decreaseQuantity.lua"
    private val INCREASE_QUANTITY_SCRIPT_PATH = "scripts/increaseQuantity.lua"

    @Bean
    fun reactiveRedisTemplate(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, String> {

        val redisSerializationContext = RedisSerializationContext.newSerializationContext<String, String>()
            .key(StringRedisSerializer())
            .value(StringRedisSerializer())
            .hashKey(StringRedisSerializer())
            .hashValue(StringRedisSerializer())
            .build()

        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, redisSerializationContext)
    }

    @Bean
    fun decreaseScript(): RedisScript<String> {
        return RedisScript.of(ClassPathResource(DECREASE_QUANTITY_SCRIPT_PATH), String::class.java)
    }

    @Bean
    fun increaseScript(): RedisScript<String> {
        return RedisScript.of(ClassPathResource(INCREASE_QUANTITY_SCRIPT_PATH), String::class.java)
    }

}