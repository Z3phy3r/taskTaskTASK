package com.example.taksmasterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import nl.builders.taskmaster.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.task_screen.*
import nl.builders.taskmaster.taskScreenAdapter

var setTasks= arrayListOf<TaskQuestion>()

data class TaskQuestion(
    val discription: String?="",
    val imagereff: String?="",
    val name: String?=""
)

class TmTask : AppCompatActivity() {

    private var mDatabase: DatabaseReference? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_screen)
        mDatabase = FirebaseDatabase.getInstance().getReference("tasks")
        getTasks()
    }
    private fun getTasks() {
        FirebaseDatabase.getInstance().getReference("tasks")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    throw p0.toException()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (productSnapshot in p0.children) {
                        val tasks =productSnapshot.getValue((TaskQuestion()::class.java)
                        )
                        setTasks.add(tasks!!)
                        Log.e("setTasks", setTasks.toString())
                        Log.e("p0",productSnapshot.toString())
                    }
                    val adapter = taskScreenAdapter(this@TmTask,setTasks)
                    viewPager.adapter = adapter

                }
            })

    }

}