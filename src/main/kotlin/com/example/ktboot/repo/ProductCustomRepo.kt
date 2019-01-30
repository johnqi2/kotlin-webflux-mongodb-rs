package com.example.ktboot.repo

import com.example.ktboot.model.Product
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux

interface ProductCustomRepo {
    fun searchProducts(params: MultiValueMap<String, String>): Flux<Product>
}
