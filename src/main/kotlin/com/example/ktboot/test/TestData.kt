package com.example.ktboot.test

import com.example.ktboot.model.Details
import com.example.ktboot.model.Product

object TestData {
    fun product(
        sku: String = "sku1",
        title: String = "foo_title",
        description: String = "foo_desc"
    ): Product {
        val prod = Product()
        prod.sku = sku
        prod.type = "TV"
        prod.title = title
        prod.description = description
        val details = Details()
        details.type = "HDTV"
        prod.details = details
        return prod
    }
}
