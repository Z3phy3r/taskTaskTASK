//package com.example.taksmasterapp
//
//import android.annotation.SuppressLint
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.*
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//import kotlinx.android.synthetic.main.view_task_rating_screen.*
//import nl.builders.taskmaster.R
//import nl.builders.taskmaster.setTasks
//
//val ratingFirebase=taskList[position].rating           //add from last page task.indexList.ranking
//var user: String= taskList[position].userID        //add from last page taskList.index.user
//var userUID=taskList[position].userUID  //add from last page taskList.index.userUID
//var task:String= setTasks[position].name //add from last page taskList[index].name
//var currentRating= arrayListOf<RatingData>()
//
//data class RatingData(
//        val nrVotes: Double?=-1.0,
//        val sumOfVotes: Double?=-1.0
//)
//class Rating : AppCompatActivity() {
//    var ratingTextView: TextView? = null
//    var videoView: ImageView?=null
//    lateinit var ratingBar: RatingBar
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.view_task_rating_screen)
//        ratingTextView = findViewById(R.id.textView)
//        ratingBar.rating = ratingFirebase.toFloat()
//
//    }
//
//
//    fun rate(view: View) {
//        FirebaseDatabase.getInstance().getReference("submissions").child(task).child(userUID)
//                .child("rating").child("voted")
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onCancelled(p0: DatabaseError) {
//                        throw p0.toException()
//                    }
//
//                    override fun onDataChange(p0: DataSnapshot) {
//                        Log.e("snapshot", p0.value.toString())
//                        if (p0.value.toString().contains(user)) {
//                            Toast.makeText(applicationContext , "already voted!!", Toast.LENGTH_LONG).show()
//                        } else {
//                            FirebaseDatabase.getInstance().getReference("submissions").child(task).child(userUID)
//                                    .child("rating")
//                                    .addListenerForSingleValueEvent(object : ValueEventListener {
//                                        override fun onCancelled(p0: DatabaseError) {
//                                            throw p0.toException()
//                                        }
//
//                                        @SuppressLint("SetTextI18n")
//                                        override fun onDataChange(p0: DataSnapshot) {
//                                            val rating = p0.getValue(RatingData::class.java)
//                                            currentRating.add(rating!!)
//                                            println(currentRating)
//                                            val ratingValue: Float = ratingBar.rating
//                                            ratingTextView?.text = "The Rating is: $ratingValue"
//                                            val oldSumOfVotes = currentRating[0].sumOfVotes
//                                            val oldNrVotes: Double? = currentRating[0].nrVotes
//                                            val nrVotes: Double? = 1.0 + oldNrVotes!!
//                                            val sumOfVotes: Double? = oldSumOfVotes?.plus(ratingValue)
//                                            val ranking: Double? = sumOfVotes?.div(nrVotes!!)
//                                            FirebaseDatabase.getInstance().getReference("submissions").child(task)
//                                                    .child(userUID)
//                                                    .child("rating").child("nrVotes").setValue(nrVotes)
//                                            FirebaseDatabase.getInstance().getReference("submissions").child(task)
//                                                    .child(userUID)
//                                                    .child("rating").child("sumOfVotes").setValue(sumOfVotes)
//                                            FirebaseDatabase.getInstance().getReference("submissions").child(task)
//                                                    .child(userUID)
//                                                    .child("ranking").setValue(ranking)
//                                            FirebaseDatabase.getInstance().getReference("submissions").child(task)
//                                                    .child(userUID)
//                                                    .child("rating").child("voted").child(user).setValue(1)
//                                        }
//                                    })
//                        }
//                    }
//
//
//                })
//
//
//    }
//    fun getFile(){
//        var bmp : Bitmap? = null
//        var fileRef: StorageReference? = null
//        fileRef = FirebaseStorage.getInstance().reference.child(task).child(taskList[position].storageID!!)
//
//        if (fileRef != null) {
//
//            val ONE_MEGABYTE = (1920 * 1080).toLong()
//            fileRef.getBytes(ONE_MEGABYTE)
//                    .addOnSuccessListener { bytes ->
//                        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                        println(bmp)
//                        videoView?.setImageBitmap(bmp)
//
//                    }
//                    .addOnFailureListener { exception ->
//                        println("fail")
//                    }
//        } else {
//
//        }
//    }
//}