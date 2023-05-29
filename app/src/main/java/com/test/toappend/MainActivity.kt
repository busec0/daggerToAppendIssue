package com.test.toappend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.toappend.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
    }
}