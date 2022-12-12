package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.todolistapp.TodoPagerActivity
import java.util.*

/**
 * Created by Ebbi on 26/11/2017.
 * Updated by Patrick for Android 12 18/1/2022
 */
class TodoPagerActivity : AppCompatActivity() {
    private var mTodos: List<Todo>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_pager)
        val todoId = intent.getSerializableExtra(EXTRA_TODO_ID) as UUID?
        val mViewPager = findViewById<ViewPager2>(R.id.todo_view_pager)
        mTodos = TodoModel.Companion.get(this)?.todos
        mViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mTodos!!.size
            }

            override fun createFragment(position: Int): Fragment {
                val todo = mTodos!![position]
                return TodoFragment.Companion.newInstance(todo.id)
            }
        }
        for (i in mTodos!!.indices) {
            if (mTodos!![i].id == todoId) {
                mViewPager.currentItem = i
                break
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