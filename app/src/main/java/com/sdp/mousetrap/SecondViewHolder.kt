package com.sdp.mousetrap

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class SecondViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById(R.id.tittle) as TextView
    val avatar = view.findViewById(R.id.logo) as ImageView

    @SuppressLint("SetTextI18n")
    fun bind(award:Award, context: Context){
        title.text = award.name
        title.text = award.price.toString() + ' ' + getEmojiByUnicode(0x1F9C0)
        avatar.loadUrl(award.image_url)
        itemView.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation Message")
            builder.setMessage("Are you sure you want to cash in this reward?")
            builder.setPositiveButton("YES") {dialog, which ->
                Toast.makeText(context, "Cashed in " + award.name, Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("NO") {dialog, which ->
                Toast.makeText(context, "Operation cancelled", Toast.LENGTH_SHORT).show()
            }
            var dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }
}