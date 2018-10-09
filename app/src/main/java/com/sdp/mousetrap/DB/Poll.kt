package com.sdp.mousetrap.DB

import java.io.Serializable
import java.util.*

class Poll : Serializable {
    var id: Int
    var client: String
    var cost: Int
    var creation_date: Date
    var anonymous: Boolean
    var score: Int
    var deadline: Date
    var minimum_answers: Int
    var maximum_answers: Int
    var description: String
    var image_url: String

    constructor(id: Int, client: String, cost: Int, creation_date: Date, anonymous: Boolean, score: Int,
                deadline: Date, minimum_answers: Int, maximum_answers: Int, description: String,
                image_url: String) {
        this.id = id
        this.client = client
        this.cost = cost
        this.creation_date = creation_date
        this.anonymous= anonymous
        this.score = score
        this.deadline = deadline
        this.minimum_answers = minimum_answers
        this.maximum_answers = maximum_answers
        this.description = description
        this.image_url = image_url
    }

}