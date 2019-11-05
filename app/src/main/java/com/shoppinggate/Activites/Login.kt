package com.shoppinggate.Activites

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.shoppinggate.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var dataSaver: SharedPreferences
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(this);


    }

    fun Rela_SignIn(view: View) {
        constrain_Register.visibility= View.GONE
        constrain_Login.visibility= View.VISIBLE
        view_Register.visibility=View.GONE
        view_login.visibility=View.VISIBLE

    }

    fun Rela_SignUp(view: View) {
        constrain_Register.visibility= View.VISIBLE
        constrain_Login.visibility= View.GONE
        view_Register.visibility=View.VISIBLE
        view_login.visibility=View.GONE



    }


}
