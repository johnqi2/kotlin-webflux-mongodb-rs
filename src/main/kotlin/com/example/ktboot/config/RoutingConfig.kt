package com.example.ktboot.config

import com.example.ktboot.web.ProductHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RoutingConfig {
    @Autowired lateinit var productHander: ProductHandler

    @Bean
    fun productRouter() = router {
        "/products".nest {
            GET("", productHander::getProducts)
            GET("/{id}", productHander::getProduct)
            contentType(MediaType.APPLICATION_JSON_UTF8)
                .nest {
                    POST("", productHander::addProduct)
                }
        }
    }
}
