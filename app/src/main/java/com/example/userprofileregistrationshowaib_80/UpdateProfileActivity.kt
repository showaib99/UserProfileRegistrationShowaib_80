package com.example.userprofileregistrationshowaib_80


import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var userProfile: UserProfile

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var districtEditText: EditText
    private lateinit var mobileEditText: EditText

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_profile)


        profileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)


        userProfile = intent.getSerializableExtra("USER_PROFILE") as UserProfile


        nameEditText = findViewById(R.id.profileNameEt)
        emailEditText = findViewById(R.id.profileEmailEt)
        dobEditText = findViewById(R.id.profileDOBEt)
        districtEditText = findViewById(R.id.profileDistrictEt)
        mobileEditText = findViewById(R.id.profilemobileEt)


        populateFields()


        progressDialog = ProgressDialog(this).apply {
            setMessage("Updating Profile...")
            setCancelable(false) // Prevent closing while loading
        }


        findViewById<Button>(R.id.updateBtn).setOnClickListener {
            if (validateInputs()) {
                showLoading()
                updateUserProfile()
            }
        }
    }

    private fun populateFields() {
        nameEditText.setText(userProfile.name)
        emailEditText.setText(userProfile.email)
        dobEditText.setText(userProfile.dob)
        districtEditText.setText(userProfile.district)
        mobileEditText.setText(userProfile.mobile)
    }

    private fun validateInputs(): Boolean {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val dob = dobEditText.text.toString().trim()
        val district = districtEditText.text.toString().trim()
        val mobile = mobileEditText.text.toString().trim()

        return when {
            name.isEmpty() -> {
                nameEditText.error = "Please enter your name"
                nameEditText.requestFocus()
                false
            }
            email.isEmpty() -> {
                emailEditText.error = "Please enter your email"
                emailEditText.requestFocus()
                false
            }
            dob.isEmpty() -> {
                dobEditText.error = "Please enter your date of birth"
                dobEditText.requestFocus()
                false
            }
            district.isEmpty() -> {
                districtEditText.error = "Please enter your district"
                districtEditText.requestFocus()
                false
            }
            mobile.isEmpty() -> {
                mobileEditText.error = "Please enter your mobile number"
                mobileEditText.requestFocus()
                false
            }
            else -> true
        }
    }

    private fun updateUserProfile() {
        // Retrieve input values
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val dob = dobEditText.text.toString()
        val district = districtEditText.text.toString()
        val mobile = mobileEditText.text.toString()


        val updatedUserProfile = UserProfile(
            id = userProfile.id,
            name = name,
            email = email,
            dob = dob,
            district = district,
            mobile = mobile
        )


        profileViewModel.updateUserProfile(updatedUserProfile)


        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }, 2000)
    }

    private fun showLoading() {

        progressDialog.show()
    }

    private fun hideLoading() {

        progressDialog.dismiss()
    }
}
