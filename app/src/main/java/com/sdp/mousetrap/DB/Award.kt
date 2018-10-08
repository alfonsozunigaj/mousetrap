package com.sdp.mousetrap.DB

class Award {
    var name: String
    var price: Int
    var image_url: String

    constructor(name: String, price: Int, image_url: String) {
        this.name = name
        this.price = price
        this.image_url = image_url
    }
}