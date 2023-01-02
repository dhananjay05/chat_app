@file:Suppress("OverrideDeprecatedMigration")

package com.example.chatapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.base.BaseFragmentWithBinding
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

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()
        ) {
            binding.userImg.setImageURI(it)
            if (it != null) {
                selectedImg = it
            }
        }

        binding.userImg.setOnClickListener {
            loadImage.launch("image/*")
        }

        binding.btnContinue.setOnClickListener {
            val name = binding.nameTxv.text.toString()
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(context, "Please enter your name !!", Toast.LENGTH_LONG).show()
            } else {
                uploadData()
            }
        }

    }

    fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        if (this::selectedImg.isInitialized) {
            reference.putFile(selectedImg).addOnCompleteListener {
                if (it.isSuccessful) {
                    reference.downloadUrl.addOnSuccessListener { task ->
                        uploadInfo(task.toString())
                    }
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
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
            }

    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_profile
    }
}