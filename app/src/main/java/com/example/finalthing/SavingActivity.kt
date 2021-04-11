package com.example.finalthing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.finalthing.SecondActivity.Companion.SAVED_MESSAGE
import kotlinx.android.synthetic.main.activity_saving.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class SavingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try
        {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {}

        setContentView(R.layout.activity_saving)


        var bitmapstring = intent.getStringExtra(SAVED_MESSAGE)
        var bitmap = BitmapFactory.decodeStream(openFileInput("myImage"));
        //val croppedBitmap = Bitmap.createBitmap(bitmap, 160, 500, 650, 850)//vertical
        val croppedBitmap = Bitmap.createBitmap(bitmap, 45, 605, 880, 660)//horizontal 5.3
        imageView2.setImageBitmap(croppedBitmap)
        //bitmap.compress(Bitmap.CompressFormat.WEBP, 0, out)
        //val decoded = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100,)
        Log.d("savt","read ${bitmap.byteCount} bytes")
        //imageView2.setImageBitmap(bitmap)
    }
    fun onlick_save_image(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("savt","permitions reqested")


            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            Log.d("savt","permitions reqested")
        }
        else
        {
            SaveImageToStorage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 100)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                SaveImageToStorage()
            }
            else
            {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public fun SaveImageToStorage(){
        val externalStorageState = Environment.getExternalStorageState()
        Log.d("savt","saving started")
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED))
        {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, "test.jpg")
            Log.d("savt","File ${file.absolutePath}")
            try
            {
                val stream: OutputStream = FileOutputStream(file)
                //val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.img100)
                var bitmapstring = intent.getStringExtra(SAVED_MESSAGE)
                var bitmap = BitmapFactory.decodeStream(openFileInput("myImage"));
                //var out = ByteArrayOutputStream()
                val croppedBitmap = Bitmap.createBitmap(bitmap, 45, 605, 880, 660)

                //bitmap.compress(Bitmap.CompressFormat.WEBP, 0, out)
                //val decoded = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))
                //bitmap.compress(Bitmap.CompressFormat.PNG, 100,)
                Log.d("savt","read ${bitmap.byteCount} bytes")
                croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                //val decoded = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))

                stream.flush()
                stream.close()
                Log.d("savt","Image saved")
                Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
            }
            catch(e: Exception)
            {
                e.printStackTrace()
            }
        }
        else
        {
            Toast.makeText(this, "Unable to access the storage", Toast.LENGTH_SHORT).show()
        }
    }

    fun SharePicture(view:View){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.type = "image/jpg"


        val storageDirectory = Environment.getExternalStorageDirectory().toString()
        val file = File(storageDirectory, "temp.jpg")
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try
        {
            val stream: OutputStream = FileOutputStream(file)
            var bitmap = BitmapFactory.decodeStream(openFileInput("myImage"));
            val croppedBitmap = Bitmap.createBitmap(bitmap, 45, 605, 880, 660)
            croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()


        }
        catch(e: Exception)
        {
            e.printStackTrace()
        }
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(sendIntent, "Поделиться"))
    }
}
