package com.example.chatapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chatapp.databinding.FragmentProfileBinding
import com.example.chatapp.model.UserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileFragment : BaseFragmentWithBinding<FragmentProfileBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg: Uri
    private lateinit var dialog: AlertDialog.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = AlertDialog.Builder(context)
            .setMessage("Updating Profile...")
            .setCancelable(false)

        db = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.userImg.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image"
            startActivityForResult(intent, 1)
        }

        binding.btnContinue.setOnClickListener {
            if (binding.nameTxv.text.isEmpty()) {
                Toast.makeText(context, "Please enter your name !!", Toast.LENGTH_LONG).show()
            } else if (selectedImg == null) {
                Toast.makeText(context, "Please select your image !!", Toast.LENGTH_LONG).show()
            } else {
                uploadData()
            }
        }
    }

    fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener {
                    uploadInfo(task.toString())
                }
            }
        }
    }

    fun uploadInfo(imgUrl: String) {

        val user = UserDto(auth.uid.toString(), binding.nameTxv.toString(), auth.currentUser?.phoneNumber.toString(), imgUrl)
        db.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Data inserted successfully !!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ))
                //MainActivity Handling
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && data.data != null) {
            selectedImg = data.data!!

            binding.userImg.setImageURI(selectedImg)
        }
    }
}