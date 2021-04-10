package com.example.finalthing

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class FontsLibAdapter(val callback:FontsLibActivity.onFontListener,var context:Context,private val values:ArrayList<String>) : RecyclerView.Adapter<FontsLibAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_view,parent,false)
        itemView.findViewById<Button>(R.id.pick_button).setOnClickListener{
            if(callback != null){
                var id: View = it.getTag() as View
                callback.onFontClicked(id)
                var s:String = it.getTag() as String
                Log.d("data send","send to ativity: $s")
            }
        }
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.textView?.typeface = Typeface.createFromFile(values[position])
        holder?.textView?.text ="Ave Satan!"
        var id:Int = position
        holder?.itemView.setTag(id)
        holder?.PickButton.setOnClickListener{

        holder?.PickButton.setTag(values[id])
Log.d("selected","selected $id")
            callback.onFontClicked(it)
        }

    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textView = itemView.findViewById<TextView>(R.id.text_list_item)
        var PickButton = itemView.findViewById<Button>(R.id.pick_button)

    }
}









