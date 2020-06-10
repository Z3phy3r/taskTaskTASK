
package nl.builders.taskmaster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taksmasterapp.TmTask
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var mailText: EditText
    lateinit var passwordText: EditText
    private lateinit var mAuth: FirebaseAuth
    lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        mailText= findViewById(R.id.mailText)
        passwordText= findViewById(R.id.passwordText)
        mAuth =  Firebase.auth
        signInButton = findViewById(R.id.signupButton)
        Log.d("oncreate", "oncreate done")
    }




    fun goClicked(view: View) {
        // Check if we can log in the user
        mAuth.signInWithEmailAndPassword(mailText?.text.toString(), passwordText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    // Sign up the user
                    mAuth.createUserWithEmailAndPassword(mailText?.text.toString(), passwordText?.text.toString()).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //FirebaseDatabase.getInstance().getReference().child("users").child(task.result.user.uid).child("email").setValue(usernameText?.text.toString())
                            val user = Firebase.auth.currentUser
                            user?.let {
                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                // authenticate with your backend server, if you have one. Use
                                // FirebaseUser.getToken() instead.
                                val uid = user.uid
                            }
                            //FirebaseDatabase.getInstance().getReference().child("users").setValue(user!!.uid)
                            FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid).child("userID").setValue(
                                mailText.text.toString())
                            FirebaseDatabase.getInstance().reference.child("users").child(user.uid).child("profilePic").setValue("alvin.jpg")
                            FirebaseDatabase.getInstance().reference.child("users").child(user.uid).child("friendIDs").child("f2IX4FQb4NRlmxPfwkmJlYo7FNC2").setValue("f2IX4FQb4NRlmxPfwkmJlYo7FNC2")
                            FirebaseDatabase.getInstance().reference.child("users").child("f2IX4FQb4NRlmxPfwkmJlYo7FNC2").child("friendIDs").child(user!!.uid).setValue(user!!.uid)
                            logIn()
                            Log.d("account created", "account has been created")
                        } else {
                            Toast.makeText(this,"Login Failed. Try Again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }
    private fun logIn() {
        // Move to next Activity
        val intent = Intent(this, TmTask::class.java)
        startActivity(intent)
    }

}




