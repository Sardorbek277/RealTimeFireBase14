package com.example.realtimefirebase14

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import com.example.realtimefirebase14.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var database:FirebaseDatabase
    lateinit var reference: DatabaseReference
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("messages")

        binding.switchFlash.setOnCheckedChangeListener { buttonView, isChecked ->
            reference.setValue(isChecked)
        }
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val isChecked: Boolean = snapshot.value as Boolean
                val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
                val cameraId = cameraManager.cameraIdList[0]
                if (isChecked){
                    cameraManager.setTorchMode(cameraId, true)

                }else{
                    try {
                        cameraManager.setTorchMode(cameraId, false)
                    }catch (e:CameraAccessException){

                    }

                }
            }


            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}