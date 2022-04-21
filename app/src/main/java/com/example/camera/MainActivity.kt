package com.example.camera

import android.app.Activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File


private const val REQUEST_CODE = 100
private const val CODE = 200
class MainActivity : AppCompatActivity() {
    private lateinit var filePhoto: File
    private val FILE_NAME = "photo.jpg"
    private val IMAGE_CHOOSE = 1000
    lateinit var imageview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnchoose = findViewById<Button>(R.id.btnchoose)

        btnchoose.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.dialogTitle)
            builder.setMessage(R.string.dialogMessage)
            builder.setPositiveButton("Open Camera") { dialogInterface, which ->
                checkPermission(Manifest.permission.CAMERA, CODE )
                // Toast.makeText(this, "Opening Camera", Toast.LENGTH_SHORT).show()
                //cam()
            }
            builder.setNegativeButton("Open Gallery") { dialogInterface, which ->
                //val intent = Intent(this, Gallery::class.java)
                //startActivity(intent)
                Toast.makeText(this, "Opening Gallery", Toast.LENGTH_SHORT).show()
                gallery()
            }
            builder.setNeutralButton("Close") { dialogInterface, which ->
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
    private fun cam() {
        val Camintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(FILE_NAME)
        val providerFile = FileProvider.getUriForFile(this,"com.example.camera.fileprovider", filePhoto)
        Camintent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        startActivityForResult(Camintent, REQUEST_CODE)

    }
    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }
    private fun gallery(){
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i,IMAGE_CHOOSE)
    }
        private fun checkPermission(permission: String, requestCode: Int) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            } else {
                Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
               cam()
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photo = BitmapFactory.decodeFile(filePhoto.absolutePath)
                       imageview.setImageBitmap(photo)
                    }else{
                        super.onActivityResult(requestCode, resultCode, data)
                    }
                    if(requestCode == REQUEST_CODE&& resultCode== Activity.RESULT_OK) {
                           imageview.setImageURI(data?.data)
                    }
        }
    }