package com.example.ktboot.config

import com.example.ktboot.repo.ProductRepo
import com.example.ktboot.test.TestData
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

/**
 * Loader for populating some testing data to MongoDB server.
 */
@Component
@Profile("dev")
class DataLoader(private val productRepo: ProductRepo) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val product = TestData.product()
        productRepo.findById(product.sku!!)
            .switchIfEmpty(
                productRepo.save(product)
            )
            .subscribe()
    }
}
