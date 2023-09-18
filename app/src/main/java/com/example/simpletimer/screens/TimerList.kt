package com.example.simpletimer.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simpletimer.data.TimerRoom
import com.example.simpletimer.toDisplayString
import kotlin.time.Duration

@Composable
fun TimerList(
    navHostController: NavHostController,
    timerListViewModel: TimerListViewModel = viewModel()
) {
    val timers by timerListViewModel.timers.collectAsState(emptyList())

    // When a timer's name is being edited, save the ID for the timer here, else null
    var editingNameId by remember { mutableStateOf<Long?>(null) }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    editingNameId = null
                }
            }, color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            items(items = timers) { timer ->
                TimerCard(timerListViewModel, timer, editingNameId) {
                    editingNameId = it
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(modifier = Modifier
            .padding(16.dp)
            .align(alignment = Alignment.BottomEnd),
            onClick = { navHostController.navigate("newTimerForm") }) {
            Icon(Icons.Filled.Add, contentDescription = "Add timer")
        }
    }
}

@Composable
fun TimerCard(
    viewModel: TimerListViewModel,
    timer: TimerRoom,
    editingNameId: Long?,
    changeEditingNameId: (Long?) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                timer.title ?: "${viewModel.getTimerDisplayString(timer)} timer"
            )
        )
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {

        val endRange = if (isFocused) textFieldValue.text.length else 0

        textFieldValue = textFieldValue.copy(
            selection = TextRange(
                start = 0,
                end = endRange
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (editingNameId == timer.id) {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        changeEditingNameId(null)
                        viewModel.updateTimer(timer.copy(title = textFieldValue.text))
                    }),
                    modifier = Modifier.focusRequester(focusRequester),
                    interactionSource = interactionSource
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                ClickableText(AnnotatedString(
                    timer.title ?: "${viewModel.getTimerDisplayString(timer)} timer"
                ),
                    style = MaterialTheme.typography.titleMedium,
                    onClick = {
                        changeEditingNameId(timer.id)
                    })
                Button(
                    onClick = { viewModel.deleteTimer(timer) },
                    modifier = Modifier.size(25.dp),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        modifier = Modifier.size(20.dp),
                        contentDescription = "Remove timer"
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                Duration.parseIsoString(timer.duration).toDisplayString(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
