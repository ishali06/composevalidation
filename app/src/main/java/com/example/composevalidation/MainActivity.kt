package com.example.composevalidation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.composevalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding.loginButton.setOnClickListener { login() }
        emailInputTextOnFocusListener()
        passwordInputTextOnFocusListener()
        phoneInputTextOnFocusListener()
        setContentView(activityMainBinding.root)
    }
    private fun validPassword(): String? {
        val passwordText = activityMainBinding.loginPassword.text.toString()
        if(passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }
    private fun checkIfEmailIsValid() {

        val emailInputText = activityMainBinding.loginEmail.text.toString()
        activityMainBinding.loginEmail.doOnTextChanged { text, start, before, count ->
            if(!Patterns.EMAIL_ADDRESS.matcher(emailInputText).matches()){
                activityMainBinding.loginEmail.error = "Invalid Email Address"
            }
            else{
                activityMainBinding.loginEmail.error = null
            }
        }
        return TODO("Provide the return value")
    }
    private fun checkValidPhoneNumber(): String? {
        val phoneText = activityMainBinding.loginPhone.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex())){
            return "Must be all Digits"
        }
        if(phoneText.length != 10){
            return "Must be 10 Digits"
        }
        return null
    }
    private fun login() {
        activityMainBinding.loginEmailContainer.helperText = checkIfEmailIsValid().toString()
        activityMainBinding.loginPasswordContainer.helperText = validPassword()
        activityMainBinding.loginPhoneContainer.helperText = checkValidPhoneNumber()

        val checkIfEmailIsValid = activityMainBinding.loginEmailContainer.helperText == null
        val validPassword = activityMainBinding.loginPasswordContainer.helperText == null
        val checkValidPhoneNumber = activityMainBinding.loginPhoneContainer.helperText == null

        if (checkIfEmailIsValid && validPassword && checkValidPhoneNumber) {
            Toast.makeText(this,"Valid Form", Toast.LENGTH_SHORT).show()
            resetForm()

        }

        else
            Toast.makeText(this, "Invalid Form", Toast.LENGTH_SHORT).show()
    }
    private fun resetForm() {

        activityMainBinding.loginEmail.text = null
        activityMainBinding.loginPassword.text = null
        activityMainBinding.loginPhone.text = null

        activityMainBinding.loginPasswordContainer.helperText = "Required"
        activityMainBinding.loginEmailContainer.helperText = "Required"
        activityMainBinding.loginPhoneContainer.helperText = "Required"
    }
    private fun passwordInputTextOnFocusListener() {
        activityMainBinding.loginPassword.setOnFocusChangeListener { _, focused ->
            if(!focused){
                activityMainBinding.loginPasswordContainer.helperText = validPassword()
            }
        }
    }

    private fun emailInputTextOnFocusListener() {
        activityMainBinding.loginEmail.setOnFocusChangeListener { _, focused ->
            if(!focused){
                activityMainBinding.loginEmailContainer.helperText = checkIfEmailIsValid().toString()
            }
        }
    }

    private fun phoneInputTextOnFocusListener() {
        activityMainBinding.loginPhone.setOnFocusChangeListener { _, focused ->
            if(!focused){
                activityMainBinding.loginPhoneContainer.helperText = checkValidPhoneNumber()
            }
        }
    }

}