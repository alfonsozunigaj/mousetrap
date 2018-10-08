package com.sdp.mousetrap.DB

class Alternative {
    var question: Question
    var value: String

    constructor(question: Question, value: String) {
        this.question = question
        this.value = value
    }
}