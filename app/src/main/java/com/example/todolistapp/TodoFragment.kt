package com.example.todolistapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class TodoFragment : Fragment() {
    private var mTodo: Todo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         Fragment accessing the intent from the hosting Activity as in the following code snippet
         allows for simple code that works.

        UUID todoId = (UUID) getActivity()
                .getIntent().getSerializableExtra(TodoActivity.EXTRA_TODO_ID);

         The disadvantage: TodoFragment is no longer reusable as it is coupled to Activities whoes
         intent has to contain the todoId.

         Solution: store the todoId in the fragment's arguments bundle.
            See the TodoFragment newInstance(UUID todoId) method.

         Then to create a new fragment, the TodoActivity should call TodoFragment.newInstance(UUID)
         and pass in the UUID it retrieves from its extra argument.

        */
        val todoId = requireArguments().getSerializable(ARG_TODO_ID) as UUID?
        mTodo = TodoModel.Companion.get(activity)!!.getTodo(todoId)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_todo, container, false)
        val mEditTextTitle = view.findViewById<View>(R.id.todo_title) as EditText
        mEditTextTitle.setText(mTodo!!.title)
        mEditTextTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // This line is intentionally left blank
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mTodo!!.title = s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                // This line is intentionally left blank
            }
        })
        val mButtonDate = view.findViewById<View>(R.id.todo_date) as Button
        mButtonDate.text = mTodo!!.date.toString()
        mButtonDate.isEnabled = false
        val mCheckBoxIsComplete = view.findViewById<View>(R.id.todo_complete) as CheckBox
        mCheckBoxIsComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("DEBUG **** TodoFragment", "called onCheckedChanged")
            mTodo!!.isComplete = isChecked
        }
        return view
    }

    companion object {
        private const val ARG_TODO_ID = "todo_id"

        /*
    Rather than the calling the constructor directly, Activity(s) should call newInstance
    and pass required parameters that the fragment needs to create its arguments.
     */
        fun newInstance(todoId: UUID?): TodoFragment {
            val args = Bundle()
            args.putSerializable(ARG_TODO_ID, todoId)
            val fragment = TodoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}