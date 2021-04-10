package com.example.finalthing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private var ourtextsize = 14f

    override fun onCreate(savedInstanceState: Bundle?) {

        try
        {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {}

        var count = 0
        var dX : Float = 0.0f
        var dY : Float = 0.0f
        var st1 = 0
        var st2 = 0
        var st3 = 0
        var kek = 0

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        second_upload_btn.setOnClickListener {
            Toast.makeText(this, "upload button worked", Toast.LENGTH_SHORT).show()
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                requestPermissions(permissions, PERMISSION_CODE)
            }

            else {
                pickImageFromGallery();
            }
        }


                //val Scroll:HorizontalScrollView = findViewById(R.id.scroll)






        second_text_btn.setOnClickListener{
            Toast.makeText(this, "add button worked", Toast.LENGTH_SHORT).show()
            second_edit_text.visibility = View.VISIBLE
            apply_btn.visibility = View.VISIBLE
            ChangeFontButton.visibility = View.VISIBLE
        }


        ChangeFontButton.setOnClickListener {
            var intent = Intent(this,FontsLibActivity::class.java)

            var test :ArrayList<String> = ArrayList<String>()

            this.getDir("fonts", Context.MODE_PRIVATE).walk().forEach{test.add(it.absolutePath)}
            //Log.d("way", this.getDir("fonts", Context.MODE_PRIVATE).absolutePath)
            intent.putStringArrayListExtra(FontsLibActivity.STORED_FONTS,test)

            //var imgLib= ImgLib(this.filesDir);
            startActivityForResult(intent,1)
        }

        apply_btn.setOnClickListener {
            Toast.makeText(this, "apply button worked", Toast.LENGTH_SHORT).show()
            val edittext = findViewById<EditText>(R.id.second_edit_text)
            val message = edittext.text.toString()

            val textview = findViewById<TextView>(R.id.second_text).apply {
                text=message
            }
            second_edit_text.visibility = View.INVISIBLE
            apply_btn.visibility = View.INVISIBLE
        }

        second_text.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                    {
                        Log.d("TAG","Down x: " + event.getX() + ", Down y: " + event.getY() + ", Raw down x: " + event.getRawX() + ", Raw down y: " + event.getRawY())
                        dX = event.getX() - event.getRawX()
                        dY = event.getY() - event.getRawY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_text.animate()
                                .x(event.rawX + dX + 100)
                                .y(event.rawY + dY + 300)
                                .setDuration(0)
                                .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        count += 1;
                        Log.d("TAG", "Up x: " + event.getX() + ", Up y: " + event.getY() + ", Raw up x: " + event.getRawX() + ", Raw up y: " + event.getRawY())
                        dX = 0.0f
                        dY = 0.0f
                    }
                    MotionEvent.ACTION_CANCEL ->
                    {
                        Log.d("TAG","Cancel x: " + event.getX() + ", Cancel y: " + event.getY() + ", Raw cancel x: " + event.getRawX() + ", Raw cancel y: " + event.getRawY())
                        return false
                    }
                    else ->
                        return false
                }

                return true
            }
        })

        second_sticker_vie1.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                    {
                        Log.d("TAG","Down x: " + event.getX() + ", Down y: " + event.getY() + ", Raw down x: " + event.getRawX() + ", Raw down y: " + event.getRawY())
                        dX = event.getX() - event.getRawX()
                        dY = event.getY() - event.getRawY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_sticker_vie1.animate()
                                .x(event.rawX + dX - 500)
                                .y(event.rawY + dY)
                                .setDuration(0)
                                .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        count += 1;
                        Log.d("TAG", "Up x: " + event.getX() + ", Up y: " + event.getY() + ", Raw up x: " + event.getRawX() + ", Raw up y: " + event.getRawY())
                        dX = 0.0f
                        dY = 0.0f
                    }
                    MotionEvent.ACTION_CANCEL ->
                    {
                        Log.d("TAG","Cancel x: " + event.getX() + ", Cancel y: " + event.getY() + ", Raw cancel x: " + event.getRawX() + ", Raw cancel y: " + event.getRawY())
                        return false
                    }
                    else ->
                        return false
                }

                return true
            }
        })

        second_sticker_vie2.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                    {
                        Log.d("TAG","Down x: " + event.getX() + ", Down y: " + event.getY() + ", Raw down x: " + event.getRawX() + ", Raw down y: " + event.getRawY())
                        dX = event.getX() - event.getRawX()
                        dY = event.getY() - event.getRawY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_sticker_vie2.animate()
                                .x(event.rawX + dX - 500)
                                .y(event.rawY + dY)
                                .setDuration(0)
                                .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        count += 1;
                        Log.d("TAG", "Up x: " + event.getX() + ", Up y: " + event.getY() + ", Raw up x: " + event.getRawX() + ", Raw up y: " + event.getRawY())
                        dX = 0.0f
                        dY = 0.0f
                    }
                    MotionEvent.ACTION_CANCEL ->
                    {
                        Log.d("TAG","Cancel x: " + event.getX() + ", Cancel y: " + event.getY() + ", Raw cancel x: " + event.getRawX() + ", Raw cancel y: " + event.getRawY())
                        return false
                    }
                    else ->
                        return false
                }

                return true
            }
        })

        second_sticker_vie3.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                    {
                        Log.d("TAG","Down x: " + event.getX() + ", Down y: " + event.getY() + ", Raw down x: " + event.getRawX() + ", Raw down y: " + event.getRawY())
                        dX = event.getX() - event.getRawX()
                        dY = event.getY() - event.getRawY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_sticker_vie3.animate()
                                .x(event.rawX + dX - 500)
                                .y(event.rawY + dY)
                                .setDuration(0)
                                .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        count += 1;
                        Log.d("TAG", "Up x: " + event.getX() + ", Up y: " + event.getY() + ", Raw up x: " + event.getRawX() + ", Raw up y: " + event.getRawY())
                        dX = 0.0f
                        dY = 0.0f
                    }
                    MotionEvent.ACTION_CANCEL ->
                    {
                        Log.d("TAG","Cancel x: " + event.getX() + ", Cancel y: " + event.getY() + ", Raw cancel x: " + event.getRawX() + ", Raw cancel y: " + event.getRawY())
                        return false
                    }
                    else ->
                        return false
                }

                return true
            }
        })


        sticker1.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img1)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img1)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker2.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img2)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img2)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker3.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img3)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img3)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker4.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img4)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img4)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker5.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img5)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img5)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker6.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img6)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img6)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker7.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img7)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img7)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker8.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img8)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img8)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker9.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img9)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img9)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker10.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img10)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img10)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker11.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img11)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img11)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker12.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img12)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img12)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker13.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img13)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img13)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker14.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img14)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img14)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker15.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img15)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img15)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker16.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img16)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img16)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker17.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img17)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img17)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker18.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img18)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img18)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker19.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img19)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img19)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker20.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img20)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img20)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker21.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img21)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img21)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker22.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img22)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img22)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker23.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img23)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img23)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker24.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img24)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img24)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker25.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img25)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img25)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker26.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img26)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img26)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker27.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img27)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img27)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker28.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img28)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img28)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker29.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img29)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img29)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker30.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img30)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img30)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker31.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img31)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img31)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker32.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img32)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img32)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker33.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img33)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img33)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker34.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img34)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img34)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker35.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img35)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img35)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker36.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img36)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img36)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker37.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img37)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img37)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker38.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img38)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img38)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker39.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img39)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img39)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker40.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img40)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img40)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker41.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img41)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img41)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker42.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img42)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img42)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker43.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img43)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img43)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker44.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img44)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img44)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker45.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img45)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img45)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker46.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img46)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img46)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker47.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img47)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img47)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker48.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img48)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img48)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker49.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img49)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img49)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker50.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img50)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img50)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker51.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img51)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img51)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker52.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img52)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img52)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker53.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img53)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img53)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker54.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img54)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img54)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker55.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img55)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img55)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker56.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img56)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img56)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker57.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img57)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img57)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker58.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img58)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img58)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker59.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img59)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img59)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker60.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img60)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img60)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker61.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img61)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img61)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker62.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img62)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img62)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker63.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img63)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img63)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker64.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img64)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img64)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker65.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img65)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img65)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker66.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img66)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img66)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker67.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img67)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img67)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker68.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img68)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img68)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker69.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img69)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img69)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker70.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img70)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img70)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker71.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img71)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img71)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker72.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img72)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img72)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker73.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img73)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img73)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker74.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img74)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img74)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker75.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img75)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img75)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker76.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img76)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img76)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker77.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img77)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img77)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker78.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img78)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img78)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker79.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img79)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img79)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker80.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img80)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img80)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker81.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img81)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img81)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker82.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img82)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img82)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker83.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img83)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img83)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker84.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img84)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img84)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker85.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img85)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img85)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker86.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img86)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img86)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker87.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img87)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img87)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker88.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img88)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img88)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker89.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img89)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img89)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker90.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img90)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img90)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker91.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img91)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img91)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker92.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img92)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img92)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker93.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img93)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img93)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker94.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img94)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img94)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker95.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img95)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img95)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker96.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img96)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img96)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker97.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img97)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img97)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker98.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img98)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img98)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker99.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img99)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img99)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker100.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img100)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img100)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker101.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img101)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img101)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker102.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img102)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img102)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker103.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img103)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img103)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker104.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img104)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img104)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker105.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img105)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img105)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker106.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img106)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img106)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker107.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img107)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img107)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker108.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img108)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img108)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker109.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img109)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img109)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }
        sticker110.setOnClickListener {
            when(kek)
            {
                st1 ->
                {
                    second_sticker_vie1.visibility = View.VISIBLE
                    second_sticker_vie1.setImageResource(R.drawable.img110)
                    st1 = 1
                }
                st2 ->
                {
                    second_sticker_vie2.visibility = View.VISIBLE
                    second_sticker_vie2.setImageResource(R.drawable.img110)
                    st2 = 1
                }
                st3 ->
                {
                    second_sticker_vie3.visibility = View.VISIBLE
                    second_sticker_vie3.setImageResource(R.drawable.merry_christmas)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }

        button_plus.setOnClickListener {
            ourtextsize += 1f
            second_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourtextsize)
        }

        button_minus.setOnClickListener {
            ourtextsize -= 1f
            second_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourtextsize)
        }

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

    private fun pickImageFromGallery() {
        // На 7:00 продолжение https://youtu.be/gd300jxLEe0
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_THING)
    }

    companion object
    {
        val ACCESS_MESSAGE = "ACCESS_MESSAGE"
        private val IMAGE_PICK_THING = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permissoin denied", Toast.LENGTH_SHORT). show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_THING){
            second_pic.setImageURI(data?.data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            val textView = findViewById<TextView>(R.id.second_text)
            var accessMessage = data?.getStringExtra(ACCESS_MESSAGE);
            textView.typeface = Typeface.createFromFile(accessMessage);
            Log.d("typeface converted","converted")
        }
    }
}