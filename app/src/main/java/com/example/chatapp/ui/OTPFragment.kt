package com.example.chatapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chatapp.databinding.FragmentOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPFragment : BaseFragmentWithBinding<FragmentOtpBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var dialog: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val builder = AlertDialog.Builder(context)
            .setMessage("Please wait")
            .setTitle("Loading")
            .setCancelable(false)

        dialog = builder.create()
        dialog.show()

        val phoneNumber = "kdfndf"
        //fetch phone number from user via navargs

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(activity, "Please try again !!", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId = p0
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.btnContinue.setOnClickListener {
            if (binding.otpEdt.text.isEmpty()) {
                Toast.makeText(context, "Please enter otp", Toast.LENGTH_LONG).show()
            } else {
                dialog.show()
                val credential = PhoneAuthProvider.getCredential(verificationId, binding.otpEdt.text.toString())

                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        dialog.dismiss()
                        if (it.isSuccessful) {
                            //send to profile activity or whatever the fuck it is

                        } else {
                            Toast.makeText(context, "Error: ${it.exception}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }


    }
}