package com.example.camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnchoose = findViewById<Button>(R.id.btnchoose)

        btnchoose.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.dialogTitle)
            builder.setMessage(R.string.dialogMessage)
            builder.setPositiveButton("Open Camera") { dialogInterface, which ->
                val intent = Intent(this, Camera::class.java)
                startActivity(intent)
                Toast.makeText(this, "Opening Camera", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Open Gallery") { dialogInterface, which ->
                val intent = Intent(this, Gallery::class.java)
                startActivity(intent)
                Toast.makeText(this, "Opening Gallery", Toast.LENGTH_SHORT).show()
            }
            builder.setNeutralButton("Close") { dialogInterface, which ->
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}