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
    val realName = view.findViewById(R.id.tvRealName) as TextView
    val publisher = view.findViewById(R.id.tvPublisher) as TextView
    val avatar = view.findViewById(R.id.logo) as ImageView

    @SuppressLint("SetTextI18n")
    fun bind(poll:Poll, context: Context){
        title.text = poll.client
        realName.text = poll.cost.toString()
        publisher.text = poll.score.toString() + ' ' + getEmojiByUnicode(0x1F9C0)
        itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, poll.client, Toast.LENGTH_SHORT).show() })
        avatar.loadUrl("https://www.festisite.com/static/partylogo/img/logos/burger-king.png")
    }
    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }
}
