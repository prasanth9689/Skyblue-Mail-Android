package com.skyblue.mail.inbox

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailItem(
    emailMessage: EmailMessage,
    modifier: Modifier = Modifier,
    onRemove: (EmailMessage) -> Unit
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(emailMessage)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                StartToEnd -> {
                    onRemove(currentItem)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                EndToStart -> {
                    onRemove(currentItem)
                    Toast.makeText(context, "Item archived", Toast.LENGTH_SHORT).show()
                }
                Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = { DismissBackground(dismissState)},
        content = {
            EmailMessageCard(emailMessage)
        })
}