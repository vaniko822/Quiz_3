package com.example.quiz3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private val db = Firebase.database.getReference("Student")
    private val auth = FirebaseAuth.getInstance()

    private lateinit var nameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var personalIdEditText: EditText
    private lateinit var imageEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var schoolEditText: EditText

    private lateinit var userNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var personalIdTextView: TextView
    private lateinit var emailTextView: TextView

    private lateinit var imageView: ImageView

    private lateinit var editProfileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        listeners()
    }
    private fun listeners() {
        editProfileButton.setOnClickListener{
            val email = emailEditText.text.toString()
            val firstname = nameEditText.text.toString()
            val lastname = lastNameEditText.text.toString()
            val personalId = personalIdEditText.text.toString()
            val profilePicture = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg"

            if(personalId.length!=13){
                Toast.makeText(this, "ID must be 13 characters length", Toast.LENGTH_LONG).show()
            } else if(!email.contains("@")){
                Toast.makeText(this, "Email not valid", Toast.LENGTH_LONG).show()
            } else{
                val studentInfo = Student(email, firstname, lastname, personalId, profilePicture)
                db.child("students").setValue(studentInfo)
            }

        }
    }

    private fun init() {
        imageView = findViewById(R.id.imageView)

        lastNameEditText = findViewById(R.id.lastNameEditText)
        nameEditText = findViewById(R.id.nameEditText)
        personalIdEditText = findViewById(R.id.personalIdEditText)
        emailEditText = findViewById(R.id.emailEditText)
        imageEditText = findViewById(R.id.linkEditText)
        schoolEditText = findViewById(R.id.schoolEditText)

        userNameTextView = findViewById(R.id.userNameTextView)
        lastNameTextView = findViewById(R.id.lastNameTextView)
        personalIdTextView = findViewById(R.id.personalIdTextView)
        emailTextView = findViewById(R.id.emailTextView)

        editProfileButton = findViewById(R.id.editProfileButton)

        db.child(auth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val studentInfo = snapshot.getValue(Student::class.java) ?: return
                emailTextView.text = studentInfo.email
                userNameTextView.text = studentInfo.firstname
                lastNameTextView.text = studentInfo.lastname
                personalIdTextView.text = studentInfo.personalId
                Glide.with(this@ProfileActivity).load(studentInfo.profilePicture).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
}}