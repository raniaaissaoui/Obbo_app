package com.obbo.app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var etPrenom: EditText
    private lateinit var etNom: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvAlreadyAccount: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialisation des composants
        etPrenom = findViewById(R.id.etPrenom)
        etNom = findViewById(R.id.etNom)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvAlreadyAccount = findViewById(R.id.tvAlreadyAccount)

        // Initialisation de Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Redirection vers la page de connexion
        tvAlreadyAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Action Inscription
        btnRegister.setOnClickListener {
            val prenom = etPrenom.text.toString().trim()
            val nom = etNom.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Adresse email invalide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Envoyer email de vérification
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { emailTask ->
                            if (emailTask.isSuccessful) {
                                Toast.makeText(this, "Email de vérification envoyé. Vérifiez votre boîte mail.", Toast.LENGTH_LONG).show()

                                // Rediriger vers LoginActivity
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Erreur d'envoi de l'email : ${emailTask.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Erreur d'inscription : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
