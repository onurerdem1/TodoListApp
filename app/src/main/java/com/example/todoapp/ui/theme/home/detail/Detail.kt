package com.example.todoapp.ui.theme.home.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.Todo

@Composable
fun DetailScreen(
    selectedId: Long,
    onNavigate: () -> Unit,
) {
    val viewModel = viewModel(
        DetailViewModel::class.java,
        factory = DetailViewModelFactory(selectedId)
    )
    val state by viewModel.state.collectAsState()
    DetailScreenComponent(
        context = LocalContext.current,
        todoText = state.todo,
        onTodoTextChange = { viewModel.onTextChange(it) },
        timeText = state.time,
        onTimeTextChange = { viewModel.onTimeChange(it) },
        onNavigate = { onNavigate() },
        onSaveTodo = { viewModel.insert(it) },
        selectedId = state.selectId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenComponent(
    context: Context,
    todoText: String,
    onTodoTextChange: (String) -> Unit,
    timeText: String,
    onTimeTextChange: (String) -> Unit,
    onNavigate: () -> Unit,
    onSaveTodo: (Todo) -> Unit,
    selectedId: Long,
) {
    val isTodoEdit = selectedId == -1L
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = todoText,
            onValueChange = { onTodoTextChange(it) },
            label = { Text(text = "Enter Todo") }
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = timeText,
            onValueChange = { onTimeTextChange(it) },
            label = { Text(text = "Enter Time") }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {

            if(todoText.isNotEmpty() && timeText.isNotEmpty()){
                val todo = if (isTodoEdit) Todo(todoText, timeText)
                else Todo(todoText, timeText,id = selectedId)
                onSaveTodo(todo)
                onNavigate()
            }else{
                Toast.makeText(context,"Can not be empty",Toast.LENGTH_SHORT).show()
            }

        }) {
            val text = if (isTodoEdit) "Save Todo" else "Update todo"
            Text(text = text)
        }


    }


}