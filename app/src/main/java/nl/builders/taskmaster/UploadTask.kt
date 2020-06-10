package com.example.taksmasterapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.upload_task_screen.*
import nl.builders.taskmaster.R
import nl.builders.taskmaster.taskScreenAdapter
import java.io.ByteArrayOutputStream
import java.util.*

///upload info
var selectedTask="testTask2"
var userUID="0ITvbQnnC4McEC4OkBnvbUgnrDp2"
var descriptionText="null"
//var storageID=null
var userID="testGuido1"

class UploadTask : AppCompatActivity() {
    var createPictureView: ImageView? = null
    private val imageName = UUID.randomUUID().toString() + ".jpg"
    var storageID =imageName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_task_screen)
        createPictureView = findViewById(R.id.imageButton)
    }

    private fun getPicture() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        startActivityForResult(intent, 1)
        Log.e("log", "$intent")
    }

    fun choosePictureClicked(view: View) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPicture()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createPictureView?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPicture()
            }
        }
    }

    fun onNextClick(view: View) {
        descriptionText=descriptionTextView?.text.toString()
        Log.e("still works","1")
        if (descriptionText == "null") {
            Toast.makeText(this, "please fill in the description!", Toast.LENGTH_SHORT).show()
        }
        if (storageID == null) {
            Toast.makeText(this, "please choose an image!", Toast.LENGTH_SHORT).show()
        } else {
            Log.e("still works","2")
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("description").setValue(descriptionText)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("ranking").setValue(0.5)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("rating").child("nrVotes").setValue(0)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("rating").child("sumOfVotes").setValue(0)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("rating").child("voted").child(userUID).setValue(1)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("storageID").setValue(storageID)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("userID").setValue(userID)
            FirebaseDatabase.getInstance().reference.child("submissions").child(selectedTask).child(userUID).child("userUID").setValue(userUID)
            Log.e("still works","3")
            val bitmap = (createPictureView?.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            var uploadTask = FirebaseStorage.getInstance().reference.child(selectedTask).child(imageName).putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "upload failed", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc
                var downloadURL = "it.storage.getR"
                Log.i("downloadURL", downloadURL.toString())
            }


        }
    }
   override fun onBackPressed(){
        super.onBackPressed()
        var intent= Intent(this, TmTask::class.java)
        startActivity(intent)
    }
}