package com.sdp.mousetrap.DB

class Award {
    var id: Int
    var name: String
    var price: Int
    var image_url: String

    constructor(id: Int, name: String, price: Int, image_url: String) {
        this.id = id
        this.name = name
        this.price = price
        this.image_url = image_url
    }
}