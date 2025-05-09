package com.skyblue.mail.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Composable that represents a single email message card.
 *
 * @param emailMessage The email message to display.
 */

@Composable
fun EmailMessageCard(emailMessage: EmailMessage) {
    ListItem(
      //  modifier = Modifier.clip(MaterialTheme.shapes.small)
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
        ,
        headlineContent = {
            Text(
                emailMessage.sender,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                emailMessage.message,
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingContent = {
            Icon(
                Icons.Filled.Person,
                contentDescription = "person icon",
                Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            )
        }
    )
}