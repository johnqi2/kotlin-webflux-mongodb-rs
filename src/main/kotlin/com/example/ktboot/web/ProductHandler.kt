package com.example.ktboot.web

import com.example.ktboot.model.Product
import com.example.ktboot.repo.ProductRepo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.net.URI
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Controller
class ProductHandler(private val productRepo: ProductRepo, private val validator: Validator) {

    fun addProduct(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono<Product>()
            .map {
                val rs = validator.validate(it)
                if (!validator.validate(it).isEmpty()) {
                    throw ConstraintViolationException(rs)
                }
                it
            }
            .zipWhen { productRepo.existsById(it.sku!!) }
            .flatMap {
                if (it.t2) throw ResponseStatusException(HttpStatus.CONFLICT)
                else productRepo.save(it.t1)
            }
            .flatMap {
                ServerResponse.created(URI.create("/products/${it.sku}")).build()
            }

    fun getProduct(req: ServerRequest): Mono<ServerResponse> =
        productRepo.findById(req.pathVariable("id"))
            .flatMap { ServerResponse.ok().body(Mono.just(it)) }
            .switchIfEmpty(ServerResponse.notFound().build())

    fun getProducts(req: ServerRequest): Mono<ServerResponse> {
        val rs = productRepo.searchProducts(req.queryParams())
        return ServerResponse.ok().body(rs, Product::class.java)
    }
}
