package com.example.kotlinimagecapture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    lateinit var button:Button
    lateinit var imageView: ImageView
    var REQUEST_CAPTURE_IMAGE:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button=findViewById(R.id.button)
        imageView=findViewById(R.id.imageView)
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
            button.isEnabled=false

        button.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)
                imageCapture()
            else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),100)
        }
    }
    fun imageCapture(){
        var intent: Intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CAPTURE_IMAGE && resultCode== Activity.RESULT_OK){
                var bitMap: Bitmap = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(bitMap)

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100 && grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
           imageCapture()

    }

}