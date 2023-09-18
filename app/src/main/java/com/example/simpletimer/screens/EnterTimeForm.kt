package com.example.simpletimer.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simpletimer.R

@Composable
fun EnterTimeForm(
    navHostController: NavHostController,
    enterTimeFormViewModel: EnterTimeFormViewModel = viewModel()
) {
    val hmsString = enterTimeFormViewModel.hmsString

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                "New Timer",
                modifier = Modifier.padding(30.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    hmsString.value.substring(0, 2), style = MaterialTheme.typography.displayLarge
                )
                Text(
                    ":", style = MaterialTheme.typography.displayLarge
                )
                Text(
                    hmsString.value.substring(2, 4), style = MaterialTheme.typography.displayLarge
                )
                Text(
                    ":", style = MaterialTheme.typography.displayLarge
                )
                Text(
                    hmsString.value.substring(4, 6), style = MaterialTheme.typography.displayLarge
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Numpad(enterTimeFormViewModel)
            }
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(25.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { navHostController.navigate("timerList") }
                ) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete Timer")
                }
                Button(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(2.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp),
                    enabled = enterTimeFormViewModel.hmsString.value.toInt() > 0,
                    onClick = {
                        enterTimeFormViewModel.submitTimer()
                        navHostController.navigate("timerList")
                    }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Submit Timer")
                }
                Spacer(Modifier.width(100.dp))
            }
        }
    }
}

@Composable
fun Numpad(viewModel: EnterTimeFormViewModel) {
    Row(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        NumpadButton(text = "1", viewModel = viewModel)
        NumpadButton(text = "2", viewModel = viewModel)
        NumpadButton(text = "3", viewModel = viewModel)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        NumpadButton(text = "4", viewModel = viewModel)
        NumpadButton(text = "5", viewModel = viewModel)
        NumpadButton(text = "6", viewModel = viewModel)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        NumpadButton(text = "7", viewModel = viewModel)
        NumpadButton(text = "8", viewModel = viewModel)
        NumpadButton(text = "9", viewModel = viewModel)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        NumpadButton(text = "00", viewModel = viewModel)
        NumpadButton(text = "0", viewModel = viewModel)
        Button(
            modifier = Modifier
                .size(100.dp)
                .padding(2.dp),
            onClick = { viewModel.deleteNumber() },
            shape = CircleShape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(ImageVector.vectorResource(R.drawable.backspace), "Delete")
        }
    }
}

@Composable
fun NumpadButton(text: String, viewModel: EnterTimeFormViewModel) {
    OutlinedButton(
        modifier = Modifier
            .size(100.dp)
            .padding(2.dp),
        onClick = { viewModel.enterNumber(text) },
        shape = CircleShape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text, style = MaterialTheme.typography.displayLarge)
    }
}