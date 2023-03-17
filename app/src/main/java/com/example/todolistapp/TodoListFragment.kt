package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodoListFragment : Fragment() {
    private var mTodoRecyclerView: RecyclerView? = null
    var mTodoAdapter: TodoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)
        mTodoRecyclerView = view.findViewById(R.id.todo_recycler_view)
        mTodoRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_todo_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_todo -> {
                val todo = Todo()
                TodoModel.Companion.get(activity)!!.addTodo(todo)
                val intent: Intent = TodoPagerActivity.Companion.newIntent(activity, todo.id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val todoModel: TodoModel? = TodoModel.Companion.get(context)
        val todos = todoModel!!.todos
        if (mTodoAdapter == null) {
            mTodoAdapter = TodoAdapter(todos)
            mTodoRecyclerView!!.adapter = mTodoAdapter
        } else {
            mTodoAdapter!!.notifyDataSetChanged()
        }
    }

    inner class TodoHolder(inflater: LayoutInflater, parent: ViewGroup?) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_todo, parent, false)), View.OnClickListener {
        private var mTodo: Todo? = null
        private val mTextViewTitle: TextView
        private val mTextViewDate: TextView

        init {
            itemView.setOnClickListener(this)
            mTextViewTitle = itemView.findViewById(R.id.todo_title)
            mTextViewDate = itemView.findViewById(R.id.todo_date)
        }

        override fun onClick(view: View) {
            // have a Toast for now
            Toast.makeText(
                    activity,
                    mTodo!!.title + " clicked",
                    Toast.LENGTH_SHORT)
                    .show()

            val intent: Intent = TodoPagerActivity.Companion.newIntent(activity, mTodo!!.id)
            startActivity(intent)
        }

        fun bind(todo: Todo?) {
            mTodo = todo
            mTextViewTitle.text = mTodo!!.title
            mTextViewDate.text = mTodo!!.date.toString()
        }
    }

    inner class TodoAdapter(private val mTodos: List<Todo?>?) : RecyclerView.Adapter<TodoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return TodoHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: TodoHolder, position: Int) {
            val todo = mTodos!![position]
            holder.bind(todo)
        }

        override fun getItemCount(): Int {
            return mTodos!!.size
        }
    }
}