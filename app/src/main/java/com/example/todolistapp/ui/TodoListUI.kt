package com.example.todolistapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.R
import com.example.todolistapp.TodoPagerActivity
import com.example.todolistapp.data.TodoModel
import com.example.todolistapp.model.Todo
import com.example.todolistapp.ui.theme.TodoTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(todoList: List<Todo>, modifier: Modifier = Modifier) {
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
           /* if (result.resultCode == Activity.RESULT_OK) {
                //TODO if needed
            }
            else {
               //TODO if needed
            }*/
        }
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                },
                actions = {
                    IconButton(onClick = {
                        val todo = Todo()
                        TodoModel.get()!!.addTodo(todo)
                        startForResult.launch(TodoPagerActivity.newIntent(context, todo.id))
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            LazyColumn(contentPadding = innerPadding
                , modifier = modifier) {
            items(todoList) {
                TodoCard(
                    todo = it,
                    modifier = Modifier.padding(8.dp),
                    startForResult = startForResult
                    )
                }
            }
        }
    )
}

@Composable
fun TodoCard(
    todo: Todo,
    modifier: Modifier = Modifier,
    startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>?
) {
    val clicked = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                clicked.value = true
                startForResult!!.launch(TodoPagerActivity.newIntent(context, todo.id))
            }
    )
    {
        Column {
            Text(
                text = todo.title?:"",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = todo.date.toString()?:"",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true,
    showSystemUi = true,
    name = "My Preview")
@Composable
fun TodoListPreview() {
    TodoTheme {
        TodoList(TodoModel.get()!!.todos)
    }
}

@Preview(showBackground = true,
    name = "My Card")
@Composable
fun TodoCardPreview() {
    TodoTheme {
        TodoCard(Todo(title = "Todo Title 0"), startForResult = null)
    }
}
