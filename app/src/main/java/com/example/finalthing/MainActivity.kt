package com.example.finalthing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try
        {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {}

        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        var count = 0
        var dX : Float = 0.0f
        var dY : Float = 0.0f
        var st1 = 0
        var st2 = 0
        var st3 = 0
        var kek = 0
    }

    fun onclick_main_btn(view: View)
    {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }
}