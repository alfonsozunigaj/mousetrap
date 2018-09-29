package com.sdp.mousetrap

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById(R.id.title) as TextView
    val description = view.findViewById(R.id.description) as TextView
    val cheeses = view.findViewById(R.id.cheeses) as TextView
    val avatar = view.findViewById(R.id.logo) as ImageView

    @SuppressLint("SetTextI18n")
    fun bind(poll:Poll, context: Context){
        title.text = poll.client
        description.text = poll.description
        cheeses.text = poll.score.toString() + ' ' + getEmojiByUnicode(0x1F9C0)
        avatar.loadUrl(poll.image_url)
        itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, poll.client, Toast.LENGTH_SHORT).show() })
    }
    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }
}
