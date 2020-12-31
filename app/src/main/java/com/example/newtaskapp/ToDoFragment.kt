package com.example.newtaskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ToDoFragment : Fragment(),InputDialogFragment.Callbacks,DatePickerFragment.Callbacks {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var task:Task
    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProviders.of(this).get(TaskViewModel::class.java)
    }
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())
    private lateinit var taskRecyclerView: RecyclerView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_to_do, container, false)
        taskRecyclerView = view.findViewById(R.id.task1_recycler_view) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter
        return view
    }
    private fun updateUI(tasks: List<Task>) {
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter
        adapter= taskRecyclerView.adapter as TaskAdapter
    }

    private inner class TaskHolder(view: View)
        : RecyclerView.ViewHolder(view) , View.OnClickListener{
        private lateinit var task: Task

        val nameTextView: TextView = itemView.findViewById(R.id.task_name)
        val detailsTextView: TextView = itemView.findViewById(R.id.task_details)
        val dateEndTextView: TextView = itemView.findViewById(R.id.end_date)
        val moveButton: ImageButton = itemView.findViewById(R.id.move_task)
        val deletButton: ImageButton = itemView.findViewById(R.id.delete)
        val rebackButton: ImageButton = itemView.findViewById(R.id.reback_task)

        init {
            itemView.setOnClickListener(this)

        }
        fun bind(task: Task) {
            this.task = task

            val expiry_date = this.task.time_end .time
            var current_date=System.currentTimeMillis()
            val day_counter = expiry_date - current_date
            val priod = (day_counter / (1000 * 60 * 60 * 24)).toInt()
            if( priod>3){
                Toast.makeText(context, " their is more than three day to your task", Toast.LENGTH_SHORT) .show()
            }
            else if(priod==3){
                Toast.makeText(context, "  3 days to your task", Toast.LENGTH_SHORT) .show()
            }
            else{

                Toast.makeText(context, " less than 3 days ", Toast.LENGTH_SHORT) .show()
            }
            deletButton.setOnClickListener {
                taskViewModel.deleteTask(task)
            }
            nameTextView.text = this.task.title_task
            detailsTextView.text=this.task.details
            dateEndTextView.text=  java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(
                this.task.time_end
            ).toString()
            moveButton.setOnClickListener {
                this.task.status_task +=1
                taskViewModel.updateTask(task)
            }
            rebackButton.visibility=View.GONE
        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "${task.title_task}!", Toast.LENGTH_SHORT)
                .show()
        }
    }
    private inner class TaskAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item_task, parent, false)
            return TaskHolder(view)
        }
        override fun onBindViewHolder(holder: TaskHolder, position: Int) {

            val student=tasks[position]
            holder.apply {
                holder.bind(student)


            }
        }
        override fun getItemCount(): Int {
            return tasks.size
        }}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        taskViewModel.taskToDoListLiveData.observe(
//            viewLifecycleOwner,
//            {
//                    tasks ->
//                tasks?.let {
//
//                    updateUI(tasks)
//
//                }
//
//            }
//
//        )
}
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ToDoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun addTask(task: Task) {
        taskViewModel.addTask(task)
    }

    override fun onDateSelected(date: Date) {
        task.time_end=date
        //updateUI()
    }
}
