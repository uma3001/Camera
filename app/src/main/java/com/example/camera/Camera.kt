package com.example.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

private lateinit var imageview: ImageView
private const val REQUEST_CODE = 100
class Camera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val btncamera = findViewById<Button>(R.id.btncamera)

        btncamera.setOnClickListener {
            val Camintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Camintent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(Camintent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "unable to open camera", Toast.LENGTH_SHORT).show()
            }
            }
        }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode== REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takeimage = data?.extras?.get("data") as Bitmap
            imageview.setImageBitmap(takeimage)
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}