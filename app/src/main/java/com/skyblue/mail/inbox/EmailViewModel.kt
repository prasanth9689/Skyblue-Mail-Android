package com.skyblue.mail.inbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EmailViewModel : ViewModel() {

    private val _messageState = MutableStateFlow(emptyList<EmailMessage>())
    val messageState : StateFlow<List<EmailMessage>> = _messageState.asStateFlow()

    init {
        _messageState.update { sampleMessages() }
    }

    fun refresh(){
        _messageState.update { sampleMessages() }
    }

    fun removeItem(currentItem: EmailMessage) {
        _messageState.update {
            val mutableList = it.toMutableList()
            mutableList.remove(currentItem)
            mutableList
        }
    }

    private fun sampleMessages()= listOf(
        EmailMessage("John Doe", "Hello"),
        EmailMessage("Alice", "Hey there! How's it going?"),
        EmailMessage("Bob", "I just discovered a cool new programming language!"),
        EmailMessage("Geek", "Have you seen the latest tech news? It's fascinating!"),
        EmailMessage("Mark", "Let's grab a coffee and talk about coding!"),
        EmailMessage("Cyan", "I need help with a coding problem. Can you assist me?"),
    )
}