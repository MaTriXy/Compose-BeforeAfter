package com.smarttoolfactory.composebeforeafter.demo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val contentScaleOptions =
    listOf("None", "Fit", "Crop", "FillBounds", "FillWidth", "FillHeight", "Inside")

@Composable
internal fun ContentScaleSelectionMenu(
    contentScale: ContentScale,
    onContentScaleChanged: (ContentScale) -> Unit
) {
    var index = when (contentScale) {
        ContentScale.None -> 0
        ContentScale.Fit -> 1
        ContentScale.Crop -> 2
        ContentScale.FillBounds -> 3
        ContentScale.FillWidth -> 4
        ContentScale.FillHeight -> 5
        else -> 6
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        ExposedSelectionMenu(
            modifier = Modifier.fillMaxWidth(),
            index = index,
            title = "ContentScale",
            options = contentScaleOptions,
            onSelected = {
                index = it
                val scale = when (index) {
                    0 -> ContentScale.None
                    1 -> ContentScale.Fit
                    2 -> ContentScale.Crop
                    3 -> ContentScale.FillBounds
                    4 -> ContentScale.FillWidth
                    5 -> ContentScale.FillHeight
                    else -> ContentScale.Inside
                }

                onContentScaleChanged(scale)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedSelectionMenu(
    modifier: Modifier = Modifier,
    index: Int,
    title: String? = null,
    textStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 14.sp
    ),
    colors: TextFieldColors = ExposedDropdownMenuDefaults.textFieldColors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    ),
    options: List<String>,
    onSelected: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[index]) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = modifier,
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = {
                title?.let {
                    Text(it)
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = colors,
            textStyle = textStyle
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false

            }
        ) {
            options.forEachIndexed { index: Int, selectionOption: String ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onSelected(index)
                    },
                    text = {
                        Text(text = selectionOption)
                    }
                )
            }
        }
    }
}
