package com.example.finalthing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcel
import android.text.TextUtils
import android.util.Base64


class ActivityBridge(context: Context) {
    private val context: Context
    private val sharedPreferences: SharedPreferences

    @SuppressLint("ApplySharedPref")
    fun putData(bundle: Bundle?, intent: Intent) {
        sharedPreferences.edit()
                .putString(
                        intent.toString(),
                        Base64.encodeToString(bundleToBytes(bundle), 0)
                )
                .commit()
    }

    @SuppressLint("ApplySharedPref")
    fun getData(intent: Intent): Bundle? {
        val bundle: Bundle?
        val bundleString = sharedPreferences.getString(intent.toString(), "")
        bundle = if (TextUtils.isEmpty(bundleString)) {
            return null
        } else {
            bytesToBundle(Base64.decode(bundleString, 0))
        }
        sharedPreferences.edit()
                .clear()
                .commit()
        return bundle
    }

    fun bundleToBytes(bundle: Bundle?): ByteArray {
        val parcel = Parcel.obtain()
        parcel.writeParcelable(((bundle?.getParcelable("<KEY>"))),0)
        val bytes = parcel.marshall()
        parcel.recycle()
        return bytes
    }

    fun bytesToBundle(bytes: ByteArray): Bundle? {
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        val bundle = parcel.readBundle(context.getClassLoader())
        parcel.recycle()
        return bundle
    }

    companion object {
        private const val KEY_ACTIVITY_BRIDGE = "ACTIVITY_BRIDGE"
    }

    init {
        this.context = context
        sharedPreferences = context.getSharedPreferences(KEY_ACTIVITY_BRIDGE, Context.MODE_PRIVATE)
    }
}