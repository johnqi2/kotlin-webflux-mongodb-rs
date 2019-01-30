package com.example.ktboot.web

import com.example.ktboot.test.TestData
import com.example.ktboot.config.RoutingConfig
import com.example.ktboot.model.Product
import com.example.ktboot.repo.ProductRepo
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.ArgumentMatchers.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest
@Import(RoutingConfig::class)
class ProductHandlerTest {
    @MockBean lateinit var productRepo: ProductRepo
    @Autowired lateinit var webClient: WebTestClient

    @Test
    fun getProductTest() {
        val sku = "sku1"
        given(productRepo.findById(sku)).willReturn(Mono.just(TestData.product(sku)))

        webClient.get().uri("/products/$sku")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.sku").isEqualTo(sku)
    }

    @Test
    fun getProductsTest() {
        val sku = "sku1"
        var type = "TV"
        val params = LinkedMultiValueMap<String, String>()
        params.add("type", type)
        given(productRepo.searchProducts(params)).willReturn(Flux.just(TestData.product(sku)))

        webClient.get().uri("/products?type=$type")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$[0].sku").isEqualTo(sku)
    }

    @Test
    fun addProductTest() {
        val sku = "sku1"
        val prod = TestData.product(sku)
        given(productRepo.existsById(sku)).willReturn(Mono.just(false))
        given(productRepo.save(any(Product::class.java)))
            .willReturn(Mono.just(TestData.product(sku)))

        webClient.post()
            .uri("/products")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .syncBody(prod)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals("Location", "/products/sku1")
    }

    @Test
    fun addProductTest_conflict() {
        val sku = "sku1"
        val prod = TestData.product(sku)
        given(productRepo.existsById(sku)).willReturn(Mono.just(true))

        webClient.post()
            .uri("/products")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .syncBody(prod)
            .exchange()
            .expectStatus().is4xxClientError
    }
}
