package com.shoppinggate.Activites

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.shoppinggate.Model.Register_Model
import com.shoppinggate.R
import com.shoppinggate.ViewModel.Register_ViewModel
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var dataSaver: SharedPreferences
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    var isChecked:Boolean=false

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
    fun Btn_Register(view: View) {
        var Name = E_NameRegister.text.toString()
        var Street=e_street.text.toString()
        var City=e_city.text.toString()
        var Country=e_country.text.toString()
        var Phone = e_PhoneRegister.text.toString()
        var email = e_EmailRegister.text.toString()
        var Password = e_PasswordRegister.text.toString().trim()

        if(Name.isEmpty()){
            E_NameRegister.error = "Name required"
            E_NameRegister.requestFocus()
        }
        if(Phone.isEmpty()){
            e_PhoneRegister.error = "Phone required"
            e_PhoneRegister.requestFocus()
        }
        if(email.isEmpty()){
            e_EmailRegister.error = "Email required"
            e_EmailRegister.requestFocus()
        }
        if(Password.isEmpty()){
            e_PasswordRegister.error = "Password required"
            e_PasswordRegister.requestFocus()
        }
        if(Password.length<6||Password.length>16){
            e_PasswordRegister.error = "Min password must be at least 6 Chrachter and  max 16 Chrachter "
            e_PasswordRegister.requestFocus()
        }
        if(!isEmailValid(email)){
            e_EmailRegister.error = "Valid Email required"
            e_EmailRegister.requestFocus()
        } else if(!Name.isEmpty()&&!Phone.isEmpty() &&!email.isEmpty() &&!Password.isEmpty()  &&Password.length>=6||Password.length<=16 ) {
            if(isConnected){
                var RegisterViewModel: Register_ViewModel = ViewModelProviders.of(this)[Register_ViewModel::class.java]
                progressBarLogin.visibility= View.VISIBLE
                view.isEnabled=false
                view.hideKeyboard()
                RegisterViewModel.getData(email, Password,Phone,Name, applicationContext).observe(this,
                    Observer<Register_Model> { loginmodel ->
                        view.isEnabled=true
                        progressBarLogin.visibility = View.GONE
                        if (loginmodel != null) {
                            val customer_id = loginmodel.data.userToken
                            dataSaver.edit().putString("token", customer_id).apply()
                            val intent = Intent(this, Navigation::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val status: Boolean = RegisterViewModel.getStatus()
                            if (status == true) {
                                Toast.makeText(
                                    applicationContext,
                                    applicationContext.getString(R.string.emailisused),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                )
            }else {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.nointernet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun Btn_Login(view: View) {
        var email = E_Email.text.toString()
        var Password = E_Password.text.toString().trim()
        if(email.isEmpty()){
            E_Email.error = "Email required"
            E_Email.requestFocus()
        }
        if(Password.isEmpty()){
            E_Password.error = "Password required"
            E_Password.requestFocus()
        }
        if(Password.length<6||Password.length>16){
            E_Password.error = "Min password must be at least 6 Chrachter and  max 16 Chrachter "
            E_Password.requestFocus()
        }
        if(!isEmailValid(email)){
            E_Email.error = "Valid Email required"
            E_Email.requestFocus()
        } else if( !email.isEmpty() &&!Password.isEmpty()  &&Password.length>=6||Password.length<=16 ) {
            if(isConnected) {
                var RegisterViewModel: Register_ViewModel =
                    ViewModelProviders.of(this)[Register_ViewModel::class.java]
                progressBarLogin.visibility = View.VISIBLE
                view.isEnabled=false
                view.hideKeyboard()
                RegisterViewModel.getLogin(email, Password, applicationContext).observe(this,
                    Observer<Register_Model> { loginmodel ->
                        progressBarLogin.visibility = View.GONE
                        view.isEnabled=true
                        if (loginmodel != null) {
                            val customer_id = loginmodel.data.userToken
                            dataSaver.edit().putString("token", customer_id).apply()
                            val intent = Intent(this, Navigation::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            val status: Boolean = RegisterViewModel.getStatus()
                            if (status == true) {
                                Toast.makeText(
                                    applicationContext,
                                    applicationContext.getString(R.string.wrongemailorpass),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })
            }else{
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.nointernet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    }


    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    fun Rela_SignUp(view: View) {
        constrain_Register.visibility= View.VISIBLE
        constrain_Login.visibility= View.GONE
        view_Register.visibility=View.VISIBLE
        view_login.visibility=View.GONE



    }




    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    val Context.isConnected: Boolean
        get() {
            return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun checkEye(view: View) {

        if(isChecked){
            E_Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            icon_Eye?.setImageDrawable(this.getDrawable(R.drawable.icon_darkeye))
            isChecked=false
        }   else{
            E_Password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            icon_Eye?.setImageDrawable(this.getDrawable(R.drawable.iconeye))
            isChecked=true
        }
        E_Password.setSelection(E_Password.length());

    }
}
