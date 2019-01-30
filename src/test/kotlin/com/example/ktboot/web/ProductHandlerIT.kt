package com.example.ktboot.web

import com.example.ktboot.test.TestData
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductHandlerIT {
    @Autowired lateinit var webClient: WebTestClient

    @Test
    @DisplayName("Integration test for Product")
    fun getProductIT() {
        val sku = "sku1"
        val prod = TestData.product(sku)

        webClient.post().uri("/products")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .syncBody(prod)
            .exchange()
            .expectStatus().isCreated

        webClient.get()
            .uri("/products/$sku")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.sku").isEqualTo(sku)

        webClient.get()
            .uri("/products?type=TV&details.type=HDTV")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$[0].sku").isEqualTo(sku)
    }
}
