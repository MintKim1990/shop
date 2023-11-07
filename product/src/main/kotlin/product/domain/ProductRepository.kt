package product.domain

import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface ProductRepository: CoroutineSortingRepository<Product, Long> {
}