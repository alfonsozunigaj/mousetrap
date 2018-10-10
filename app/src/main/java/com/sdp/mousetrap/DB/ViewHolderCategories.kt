package com.sdp.mousetrap.DB

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sdp.mousetrap.FragmentDelegate
import com.sdp.mousetrap.R


class ViewHolderCategories(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.findViewById(R.id.name) as TextView

    @SuppressLint("SetTextI18n")
    fun bind(category: Category, fragmentDelegate: FragmentDelegate){
        name.text = category.name
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }
}