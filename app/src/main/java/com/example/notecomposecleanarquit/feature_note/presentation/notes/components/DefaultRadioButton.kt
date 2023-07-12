package com.example.notecomposecleanarquit.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheck: () -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = checked,
            onClick = onCheck,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun DefaultRadioButtonPreview() {
    DefaultRadioButton(Modifier, "teste", true, {})
}