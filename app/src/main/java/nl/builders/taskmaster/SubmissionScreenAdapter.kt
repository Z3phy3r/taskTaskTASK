package nl.builders.taskmaster

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taksmasterapp.Tasks
import com.example.taksmasterapp.user
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.task_screen_adapter.view.*
import kotlinx.android.synthetic.main.user_task_submission.view.*
import kotlinx.android.synthetic.main.user_task_submission.view.titleTextView
import java.nio.file.Files.size
import kotlin.math.log

class SubmissionScreenAdapter(
    var mContext:Context,
    var submissions: List<Tasks>
) : RecyclerView.Adapter<SubmissionScreenAdapter.SubmissionViewHolder>() {

    inner class SubmissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_task_submission, parent, false)
        Log.e("submissions log", submissions.toString())
        return SubmissionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return submissions.size
    }

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        var bmp : Bitmap? = null
        var fileRef: StorageReference? = null
        var imageName : String? = null
        var imageReff : DatabaseReference? = null

            imageReff = FirebaseDatabase.getInstance().reference.child("users").child(submissions[position].userUID!!)
            Log.e("image reference" , imageReff.toString())

            imageReff?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    throw p0.toException()
                }

                override fun onDataChange(p0: DataSnapshot) {

                    imageName = p0.child("profilePic").getValue(String::class.java)
                    Log.e("image reference" , imageName)
                    fileRef = FirebaseStorage.getInstance().reference.child("profileImages").child(imageName!!)
                    Log.e("file reference" , fileRef.toString())

                    val ONE_MEGABYTE = (1920 * 1080).toLong()
                    Log.e("hier werkt die nog",  fileRef.toString())
                    fileRef!!.getBytes(ONE_MEGABYTE)
                        .addOnSuccessListener { bytes ->
                            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            println(bmp)
                            Log.e("file reference" , bmp.toString())
                            holder.itemView.userImage.setImageBitmap(bmp)

                        }
                        .addOnFailureListener { exception ->
                            Log.e("Failure" , "it has failed")
                        }
                }})





       // var fileRef = FirebaseStorage.getInstance().reference.child("taskImages").child(taskList[position].imagereff!!)

        var ranking = submissions[position].ranking?.toFloat()!!
        var userId = submissions[position].userID

        holder.itemView.ratingBar.rating = ranking
        holder.itemView.titleTextView.text = userId


    }
}