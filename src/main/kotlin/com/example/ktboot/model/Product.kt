package com.example.ktboot.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
class Product {
    @Id
    @NotBlank
    var sku: String? = null

    @NotBlank
    var type: String? = null

    @NotBlank
    var title: String? = null

    var description: String? = null
    var shipping: List<Shipping>? = null
    var details: Details? = null
}

class Shipping {
    var weight: Int? = null
    var dimensions: Dimension? = null

    class Dimension {
        var width: Int? = null
        var height: Int? = null
        var depth: Int? = null
    }
}

class Details {
    var title: String? = null
    var type: String? = null
    var tracks: List<String>? = null
}


