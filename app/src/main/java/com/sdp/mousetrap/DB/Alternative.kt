package com.sdp.mousetrap.DB

class Alternative {
    var id: Int
    var question: Question
    var value: String

    constructor(id: Int,question: Question, value: String) {
        this.id = id
        this.question = question
        this.value = value
    }
}