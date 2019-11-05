package com.shoppinggate

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var DataSaver: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataSaver= PreferenceManager.getDefaultSharedPreferences(this)

        Handler().postDelayed({
            val UserToken: String? =DataSaver.getString("token",null);
            if(UserToken!=null) {
//                val intent = Intent(this, Navigation::class.java)
//                startActivity(intent)
//                finish()
            }else {
//                val intent = Intent(this, Login::class.java)
//                startActivity(intent)
//                finish()
            }
        }, 2000)

    }
}
