package com.example.notecomposecleanarquit.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notecomposecleanarquit.feature_note.domain.util.NoteOrder
import com.example.notecomposecleanarquit.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Titulo", checked = noteOrder is NoteOrder.Title,
                onCheck = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Data", checked = noteOrder is NoteOrder.Date,
                onCheck = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Cor", checked = noteOrder is NoteOrder.Color,
                onCheck = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Crescente", checked = noteOrder.orderType is OrderType.Ascending,
                onCheck = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Decrescente", checked = noteOrder.orderType is OrderType.Descending,
                onCheck = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}

@Preview
@Composable
fun OrderSeciontPreview() {
    OrderSection(onOrderChange = {})
}