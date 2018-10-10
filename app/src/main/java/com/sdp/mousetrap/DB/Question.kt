package com.sdp.mousetrap.DB

import java.io.Serializable

class Question : Serializable {
    var id : Int
    var poll: Poll
    var question: String
    var type: Int

    constructor(id: Int, poll: Poll, question: String, type: Int) {
        this.id = id
        this.poll = poll
        this.question = question
        this.type= type
    }
}