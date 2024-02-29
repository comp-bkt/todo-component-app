package com.example.todolistapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.model.Todo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoSwiper(todos:List<Todo>, currentPage:Int, modifier: Modifier) {
    val pagerState = rememberPagerState(
                initialPage = currentPage,
                pageCount = {todos.size}
            )

    HorizontalPager(state = pagerState) { page ->
        TodoUIScreen(
            todo = todos[page],
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TodoUIScreen (todo: Todo, modifier: Modifier){
    val input: MutableState<String> = rememberSaveable { mutableStateOf("")}
    val isChecked = rememberSaveable { mutableStateOf(false) }

    /*currently saved instance state used to save a Bundle and then passed to
    composable on Orientation change - consider chaging to watching the address
    space of the Todo Object to force the recomposition*/

    isChecked.value = todo.isComplete
    input.value = todo.title?:""

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ){
        Text(text = todo.title?:" ",
            fontWeight = FontWeight.Bold
        )
        TextField(value = input.value,
            modifier = modifier.fillMaxWidth(),
            onValueChange = { it ->
                input.value = it
                todo.title = it
            }
        )
        Text(text = todo.detail?:" ",
            fontWeight = FontWeight.Bold
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            onClick = {}
        ){
            Text(todo.date.toString())
        }
        Spacer(Modifier.size(6.dp))
        Checkbox(
            checked =   isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                todo.isComplete = it
            }
        )
    }
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun TodoUIPreview(){
    TodoUIScreen(
        Todo(),
        Modifier
    )
}
