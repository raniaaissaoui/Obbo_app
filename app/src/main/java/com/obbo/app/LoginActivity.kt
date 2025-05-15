package com.obbo.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var tvCreateAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialisation des composants
        btnLogin = findViewById(R.id.btnLogin)
        tvCreateAccount = findViewById(R.id.tvCreateAccount)

        // Vérification des IDs
        if (btnLogin == null) {
            Toast.makeText(this, "Erreur : ID btnLogin non trouvé", Toast.LENGTH_SHORT).show()
        }
        if (tvCreateAccount == null) {
            Toast.makeText(this, "Erreur : ID tvCreateAccount non trouvé", Toast.LENGTH_SHORT).show()
        }

        // Action Créer un compte
        tvCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Action Se connecter (à implémenter plus tard)
        btnLogin.setOnClickListener {
            Toast.makeText(this, "Connexion à implémenter", Toast.LENGTH_SHORT).show()
        }
    }
}
