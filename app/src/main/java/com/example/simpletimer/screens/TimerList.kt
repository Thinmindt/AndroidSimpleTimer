package com.example.simpletimer.screens

import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletimer.toDisplayString

@Composable
fun TimerList(timerListViewModel: TimerListViewModel = viewModel()) {

    val timersList = timerListViewModel.timersList
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            items(items = timersList) { timer ->
                TimerCard(timerListViewModel, timer)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { timerListViewModel.addTimer() }) {
            Icon(Icons.Filled.Add, contentDescription = "Add timer")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimerCard(viewModel: TimerListViewModel, timer: Timer) {
    val focusRequester = remember { FocusRequester() }
    var editingName by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                editingName = false
            })
        }) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (editingName) {
                BasicTextField(
                    value = timer.name.value,
                    onValueChange = { timer.name.value = it },
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        editingName = false
                    }),
                    modifier = Modifier.focusRequester(focusRequester)
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                ClickableText(
                    AnnotatedString(timer.name.value),
                    style = MaterialTheme.typography.titleMedium,
                    onClick = {
                        editingName = true
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
            Text(timer.time.toDisplayString(), style = MaterialTheme.typography.titleLarge)
        }
    }
}