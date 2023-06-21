package com.example.notecompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notecompose.model.Note
import com.example.notecompose.utils.formatDate

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    isExpanded: Boolean = false,
    onClicked: (Note) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(isExpanded)
    }
    Surface(
        Modifier
            .padding(4.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 34.dp,
                    bottomStart = 34.dp
                )
            )
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB)
    ) {
        Column(modifier
            .clickable {
                isExpanded = !isExpanded
//                onClicked(note)
            }
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp,
            ),
            horizontalAlignment = Alignment.Start) {

            Text(text = note.title, style = MaterialTheme.typography.subtitle2, color = Color.Black)
            AnimatedVisibility(visible = isExpanded) {
                Row(
                    Modifier.padding(vertical = 8.dp),
                ) {
                    Text(
                        color = Color.Black,
                        text = note.description,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.clickable { onClicked(note) },
                        tint = Color.Black
                    )
                }
            }

            Text(
                text = formatDate(note.entryData.time),
                style = MaterialTheme.typography.caption,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun NoteItemPreview() {
    NoteItem(
        note = Note(
            title = "title",
            description = "description",
        ), onClicked = {})
}

@Preview
@Composable
fun NoteItemExpandedPreview() {
    NoteItem(
        isExpanded = true,
        note = Note(
            title = "title",
            description = "description",
        ), onClicked = {})
}