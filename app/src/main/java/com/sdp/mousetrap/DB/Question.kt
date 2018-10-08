package com.sdp.mousetrap.DB

class Question {
    var poll: Poll
    var question: String
    var type: Int

    constructor(poll: Poll, question: String, type: Int) {
        this.poll = poll
        this.question = question
        this.type= type
    }
}