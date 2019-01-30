package com.example.ktboot.repo

import com.example.ktboot.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux

class ProductCustomRepoImpl : ProductCustomRepo {
    @Autowired lateinit var mongoOps: ReactiveMongoTemplate

    override fun searchProducts(params: MultiValueMap<String, String>): Flux<Product> {
        if (params.isEmpty()) {
            mongoOps.findAll(Product::class.java)
        }
        var criteria: Criteria? = null
        var count = 0
        for (entry in params) {
            if (count == 0) {
                criteria = where(entry.key).`is`(entry.value.first())
                count++
            } else {
                criteria = criteria!!.and(entry.key).`is`(entry.value.first())
            }
        }
        return mongoOps.find(Query(criteria!!), Product::class.java)
    }
}
