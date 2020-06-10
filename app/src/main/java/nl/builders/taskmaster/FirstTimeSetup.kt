package com.example.taksmasterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nl.builders.taskmaster.R

class FirstTimeSetup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_time_setup_screen)
    }
}