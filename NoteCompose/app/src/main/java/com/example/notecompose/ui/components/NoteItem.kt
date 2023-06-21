package com.example.notecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notecompose.model.Note
import java.time.format.DateTimeFormatter

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClicked: (Note) -> Unit
) {
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
                onClicked(note)
            }
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp,
            ),
            horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.subtitle2)
            Text(text = note.description, style = MaterialTheme.typography.subtitle1)
            Text(
                text = note.entryData.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
                style = MaterialTheme.typography.caption
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