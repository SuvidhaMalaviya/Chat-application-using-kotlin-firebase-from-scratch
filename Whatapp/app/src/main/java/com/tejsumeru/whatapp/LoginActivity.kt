package com.tejsumeru.whatapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() , View.OnClickListener
{

    var firebaseAuth : FirebaseAuth ?= null
    var progressDialog : ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)

        create_account.setOnClickListener(this)
        login_button.setOnClickListener(this)
    }


    private fun sendToMainActivity() {
        var intent = Intent(this@LoginActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun sendToRegisterActivity() {
        var intent = Intent(this@LoginActivity,RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(p0: View?) {
        if (p0 == create_account)
        {
            sendToRegisterActivity()
        }
        if (p0 == login_button)
        {
            AllowUserLogin()
        }
    }

    private fun AllowUserLogin() {
        var email = login_email.text.toString()
        var password = login_password.text.toString()

        progressDialog!!.setTitle("Loading")
        progressDialog!!.setMessage("Logging In...")
        progressDialog!!.setCanceledOnTouchOutside(true)
        progressDialog!!.show()

        if (email.isEmpty())
        {
            Toast.makeText(this@LoginActivity,"Email cannot be empty",Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty())
        {
            login_password.error="cannot be empty"
        }
        else
        {
            firebaseAuth!!.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (p0.isSuccessful)
                        {
                            sendToMainActivity()
                            Toast.makeText(this@LoginActivity,"Logged In Successfully",Toast.LENGTH_SHORT).show()
                            progressDialog!!.dismiss()
                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity,"Opps!! Try Again",Toast.LENGTH_SHORT).show()
                            progressDialog!!.dismiss()
                        }
                    }

                })
        }
    }
}