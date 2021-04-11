package com.example.finalthing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalthing.SecondActivity.Companion.ACCESS_MESSAGE

class FontsLibActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try
        {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {}

        setContentView(R.layout.activity_lib)
        var intent: Intent = getIntent()
        var images = intent.getStringArrayListExtra(STORED_FONTS)
        var Lib: RecyclerView = findViewById(R.id.LibView)
        val listener:onFontListener  = onFontListener()
        val adapter = FontsLibAdapter(onFontListener(),this,images!!)
        Lib.adapter = adapter
        Lib.layoutManager = LinearLayoutManager(this)
    }
    inner class onFontListener{
        fun onFontClicked(id: View){
            var intent = Intent()
            intent.putExtra(SecondActivity.ACCESS_MESSAGE,id.getTag() as String)
            setResult(Activity.RESULT_OK,intent)
            Log.d("data send","send ${id.getTag() as String}")
            finish()
        }
    }
    companion object {
        val STORED_FONTS = "stored_fonts"
    }
}