package com.example.finalthing

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tarek360.instacapture.Instacapture
import com.tarek360.instacapture.listener.SimpleScreenCapturingListener
import kotlinx.android.synthetic.main.activity_saving.*
import kotlinx.android.synthetic.main.activity_second.*
import java.io.ByteArrayOutputStream

class SecondActivity : AppCompatActivity() {

    private var ourtextsize = 40f
    private lateinit var memoryCache: LruCache<String, Bitmap>

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

Log.d("savt",this.window.decorView.rootView.javaClass.toString())
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

        fun getBitmapFromView(view :View):Bitmap {
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            var bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),Bitmap.Config.ARGB_8888)
            view.layout(0, 0, view.getWidth(), view.getHeight());
            Log.d("", "combineImages: width: " + view.getWidth());
            Log.d("", "combineImages: height: " + view.getHeight());
            return bitmap;
        }
                //val Scroll:HorizontalScrollView = findViewById(R.id.scroll)

        fun createImageFromBitmap(bitmap :Bitmap):String {
            var fileName:String? = "myImage";//no .png or .jpg needed
            try {
                var bytes =  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                var fo = openFileOutput(fileName, Context.MODE_PRIVATE);
                fo.write(bytes.toByteArray());
                // remember close file output
                fo.close();
            } catch (e:Exception) {
                e.printStackTrace();
                fileName = null;
            }
            return fileName!!;
        }

        fun loadBitmapFromView(v:View):Bitmap {
            var b = Bitmap.createBitmap( v.width, v.height, Bitmap.Config.ARGB_8888);
            var c = Canvas(b);
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(c);
            return b;
        }
        fun getScreenViewBitmap(v: View): Bitmap? {
            v.isDrawingCacheEnabled = true
            Log.d("savt","view is ${v.javaClass.toString()}")
            // this is the important code :)
            // Without it the view will have a dimension of 0,0 and the bitmap will be null
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            v.layout(0, 0, v.measuredWidth, v.measuredHeight)
            v.buildDrawingCache(true)
            val b = Bitmap.createBitmap(v.drawingCache,0,0,v.width,v.height)
            v.isDrawingCacheEnabled = false // clear drawing cache
            return b
        }

        NextButton.setOnClickListener {
            var intent = Intent(this,SavingActivity::class.java)

            var layout = layoutInflater.inflate(R.layout.activity_second,null) as View
            Log.d("savt","view is ${layout.javaClass.toString()}")
            //var bitmap = getScreenViewBitmap((layout.findViewById<View>(R.id.second_pic).rootView as View))
            Instacapture.capture(this, object : SimpleScreenCapturingListener() {
                override fun onCaptureComplete(bitmap: Bitmap) {
                    Log.d("savt","size ${bitmap?.byteCount}")
                    var s = createImageFromBitmap(bitmap!!)
                    intent.putExtra(ACCESS_MESSAGE,s)
                    Log.d("savt","saving started to ${s}")
                    startActivity(intent)

                }
            },button_minus,button_plus,ChangeFontButton,NextButton,second_text_btn,second_upload_btn,layout)

        }




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
                        dX = event.getX() - second_text.getX()
                        dY = event.getY() - second_text.getY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_text.setX((event.getX() - dX))
                        second_text.setY((event.getY() - dY))
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
                        dX = event.getX() - second_sticker_vie1.getX()
                        dY = event.getY() - second_sticker_vie1.getY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_sticker_vie1.setX((event.getX() - dX))
                        second_sticker_vie1.setY((event.getY() - dY))
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
                        dX = event.getX() - second_sticker_vie2.getX()
                        dY = event.getY() - second_sticker_vie2.getY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_sticker_vie2.setX((event.getX() - dX))
                        second_sticker_vie2.setY((event.getY() - dY))
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
                        dX = event.getX() - second_sticker_vie3.getX()
                        dY = event.getY() - second_sticker_vie3.getY()
                    }
                    MotionEvent.ACTION_MOVE ->
                    {
                        Log.d("TAG","Move x: " + event.getX() + ", Move y: " + event.getY() + ", Raw move x: " + event.getRawX() + ", Raw move y: " + event.getRawY())
                        second_sticker_vie3.setX((event.getX() - dX))
                        second_sticker_vie3.setY((event.getY() - dY))
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
                    second_sticker_vie3.setImageResource(R.drawable.img1)
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
                    second_sticker_vie3.setImageResource(R.drawable.img2)
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
                    second_sticker_vie3.setImageResource(R.drawable.img3)
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
                    second_sticker_vie3.setImageResource(R.drawable.img4)
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
                    second_sticker_vie3.setImageResource(R.drawable.img5)
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
                    second_sticker_vie3.setImageResource(R.drawable.img6)
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
                    second_sticker_vie3.setImageResource(R.drawable.img7)
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
                    second_sticker_vie3.setImageResource(R.drawable.img8)
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
                    second_sticker_vie3.setImageResource(R.drawable.img9)
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
                    second_sticker_vie3.setImageResource(R.drawable.img10)
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
                    second_sticker_vie3.setImageResource(R.drawable.img11)
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
                    second_sticker_vie3.setImageResource(R.drawable.img12)
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
                    second_sticker_vie3.setImageResource(R.drawable.img13)
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
                    second_sticker_vie3.setImageResource(R.drawable.img14)
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
                    second_sticker_vie3.setImageResource(R.drawable.img15)
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
                    second_sticker_vie3.setImageResource(R.drawable.img16)
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
                    second_sticker_vie3.setImageResource(R.drawable.img17)
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
                    second_sticker_vie3.setImageResource(R.drawable.img18)
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
                    second_sticker_vie3.setImageResource(R.drawable.img19)
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
                    second_sticker_vie3.setImageResource(R.drawable.img20)
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
                    second_sticker_vie3.setImageResource(R.drawable.img21)
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
                    second_sticker_vie3.setImageResource(R.drawable.img22)
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
                    second_sticker_vie3.setImageResource(R.drawable.img23)
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
                    second_sticker_vie3.setImageResource(R.drawable.img24)
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
                    second_sticker_vie3.setImageResource(R.drawable.img25)
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
                    second_sticker_vie3.setImageResource(R.drawable.img26)
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
                    second_sticker_vie3.setImageResource(R.drawable.img27)
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
                    second_sticker_vie3.setImageResource(R.drawable.img28)
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
                    second_sticker_vie3.setImageResource(R.drawable.img29)
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
                    second_sticker_vie3.setImageResource(R.drawable.img30)
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
                    second_sticker_vie3.setImageResource(R.drawable.img31)
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
                    second_sticker_vie3.setImageResource(R.drawable.img32)
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
                    second_sticker_vie3.setImageResource(R.drawable.img33)
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
                    second_sticker_vie3.setImageResource(R.drawable.img34)
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
                    second_sticker_vie3.setImageResource(R.drawable.img35)
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
                    second_sticker_vie3.setImageResource(R.drawable.img36)
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
                    second_sticker_vie3.setImageResource(R.drawable.img37)
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
                    second_sticker_vie3.setImageResource(R.drawable.img38)
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
                    second_sticker_vie3.setImageResource(R.drawable.img39)
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
                    second_sticker_vie3.setImageResource(R.drawable.img40)
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
                    second_sticker_vie3.setImageResource(R.drawable.img41)
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
                    second_sticker_vie3.setImageResource(R.drawable.img42)
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
                    second_sticker_vie3.setImageResource(R.drawable.img43)
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
                    second_sticker_vie3.setImageResource(R.drawable.img44)
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
                    second_sticker_vie3.setImageResource(R.drawable.img45)
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
                    second_sticker_vie3.setImageResource(R.drawable.img46)
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
                    second_sticker_vie3.setImageResource(R.drawable.img47)
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
                    second_sticker_vie3.setImageResource(R.drawable.img48)
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
                    second_sticker_vie3.setImageResource(R.drawable.img49)
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
                    second_sticker_vie3.setImageResource(R.drawable.img50)
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
                    second_sticker_vie3.setImageResource(R.drawable.img51)
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
                    second_sticker_vie3.setImageResource(R.drawable.img52)
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
                    second_sticker_vie3.setImageResource(R.drawable.img53)
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
                    second_sticker_vie3.setImageResource(R.drawable.img54)
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
                    second_sticker_vie3.setImageResource(R.drawable.img55)
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
                    second_sticker_vie3.setImageResource(R.drawable.img56)
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
                    second_sticker_vie3.setImageResource(R.drawable.img57)
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
                    second_sticker_vie3.setImageResource(R.drawable.img58)
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
                    second_sticker_vie3.setImageResource(R.drawable.img59)
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
                    second_sticker_vie3.setImageResource(R.drawable.img60)
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
                    second_sticker_vie3.setImageResource(R.drawable.img61)
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
                    second_sticker_vie3.setImageResource(R.drawable.img62)
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
                    second_sticker_vie3.setImageResource(R.drawable.img63)
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
                    second_sticker_vie3.setImageResource(R.drawable.img64)
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
                    second_sticker_vie3.setImageResource(R.drawable.img65)
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
                    second_sticker_vie3.setImageResource(R.drawable.img66)
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
                    second_sticker_vie3.setImageResource(R.drawable.img67)
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
                    second_sticker_vie3.setImageResource(R.drawable.img68)
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
                    second_sticker_vie3.setImageResource(R.drawable.img69)
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
                    second_sticker_vie3.setImageResource(R.drawable.img70)
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
                    second_sticker_vie3.setImageResource(R.drawable.img71)
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
                    second_sticker_vie3.setImageResource(R.drawable.img72)
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
                    second_sticker_vie3.setImageResource(R.drawable.img73)
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
                    second_sticker_vie3.setImageResource(R.drawable.img74)
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
                    second_sticker_vie3.setImageResource(R.drawable.img75)
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
                    second_sticker_vie3.setImageResource(R.drawable.img76)
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
                    second_sticker_vie3.setImageResource(R.drawable.img77)
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
                    second_sticker_vie3.setImageResource(R.drawable.img78)
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
                    second_sticker_vie3.setImageResource(R.drawable.img79)
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
                    second_sticker_vie3.setImageResource(R.drawable.img80)
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
                    second_sticker_vie3.setImageResource(R.drawable.img81)
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
                    second_sticker_vie3.setImageResource(R.drawable.img82)
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
                    second_sticker_vie3.setImageResource(R.drawable.img83)
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
                    second_sticker_vie3.setImageResource(R.drawable.img84)
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
                    second_sticker_vie3.setImageResource(R.drawable.img85)
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
                    second_sticker_vie3.setImageResource(R.drawable.img86)
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
                    second_sticker_vie3.setImageResource(R.drawable.img87)
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
                    second_sticker_vie3.setImageResource(R.drawable.img88)
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
                    second_sticker_vie3.setImageResource(R.drawable.img89)
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
                    second_sticker_vie3.setImageResource(R.drawable.img90)
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
                    second_sticker_vie3.setImageResource(R.drawable.img91)
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
                    second_sticker_vie3.setImageResource(R.drawable.img92)
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
                    second_sticker_vie3.setImageResource(R.drawable.img93)
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
                    second_sticker_vie3.setImageResource(R.drawable.img94)
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
                    second_sticker_vie3.setImageResource(R.drawable.img95)
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
                    second_sticker_vie3.setImageResource(R.drawable.img96)
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
                    second_sticker_vie3.setImageResource(R.drawable.img97)
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
                    second_sticker_vie3.setImageResource(R.drawable.img98)
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
                    second_sticker_vie3.setImageResource(R.drawable.img99)
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
                    second_sticker_vie3.setImageResource(R.drawable.img100)
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
                    second_sticker_vie3.setImageResource(R.drawable.img101)
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
                    second_sticker_vie3.setImageResource(R.drawable.img102)
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
                    second_sticker_vie3.setImageResource(R.drawable.img103)
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
                    second_sticker_vie3.setImageResource(R.drawable.img104)
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
                    second_sticker_vie3.setImageResource(R.drawable.img105)
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
                    second_sticker_vie3.setImageResource(R.drawable.img106)
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
                    second_sticker_vie3.setImageResource(R.drawable.img107)
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
                    second_sticker_vie3.setImageResource(R.drawable.img108)
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
                    second_sticker_vie3.setImageResource(R.drawable.img109)
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
                    second_sticker_vie3.setImageResource(R.drawable.img110)
                    st3 = 1
                }
                else ->
                    Toast.makeText(this, "You can't add more stickers", Toast.LENGTH_SHORT).show()
            }
        }

        button_plus.setOnClickListener {
            ourtextsize += 4f
            second_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourtextsize)
        }

        button_minus.setOnClickListener {
            ourtextsize -= 4f
            second_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourtextsize)
        }

    }

    fun getScreenViewBitmap(v: View): Bitmap? {
        v.isDrawingCacheEnabled = true
        Log.d("savt","view is ${v.javaClass.toString()}")
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.buildDrawingCache(true)
        val b = Bitmap.createBitmap(v.drawingCache)
        v.isDrawingCacheEnabled = false // clear drawing cache
        return b
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
        val SAVED_MESSAGE = "SAVED_MESSAGE"
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