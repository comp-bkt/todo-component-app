package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.todolistapp.data.TodoModel
import com.example.todolistapp.model.Todo
import com.example.todolistapp.ui.TodoSwiper
import com.example.todolistapp.ui.theme.TodoTheme
import java.util.UUID

/**
 * Updated by Patrick for Android 14 Compose 29/2/2024
 */

class TodoPagerActivity : ComponentActivity() {
    private var mTodos: List<Todo>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoId = intent.getSerializableExtra(EXTRA_TODO_ID, UUID::class.java)
        mTodos = TodoModel.Companion.get()?.todos
        val currentPage:Int = mTodos!!.indexOfFirst { it.id == todoId }

        setContent {
            TodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoSwiper(mTodos!!, currentPage, Modifier)
                }
            }
        }
    }

    companion object {
        private const val EXTRA_TODO_ID = "todo_id"
        fun newIntent(packageContext: Context?, todoId: UUID?): Intent {
            val intent = Intent(packageContext, TodoPagerActivity::class.java)
            intent.putExtra(EXTRA_TODO_ID, todoId)
            return intent
        }
    }
}