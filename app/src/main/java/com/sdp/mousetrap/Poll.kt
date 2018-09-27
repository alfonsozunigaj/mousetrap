package com.sdp.mousetrap

import java.util.*

class Poll {
    var client: String
    var cost: Int
    var creation_date: Date
    var anonymous: Boolean
    var score: Int
    var deadline: Date
    var minimum_answers: Int
    var maximum_answers: Int

    constructor(client: String, cost: Int, creation_date: Date, anonymous: Boolean, score: Int, deadline: Date, minimum_answers: Int, maximum_answers: Int) {
        this.client = client
        this.cost = cost
        this.creation_date = creation_date
        this.anonymous= anonymous
        this.score = score
        this.deadline = deadline
        this.minimum_answers = minimum_answers
        this.maximum_answers = maximum_answers
    }

}