package com.sdp.mousetrap

import android.app.Fragment
import android.support.v4.app.FragmentManager
import java.io.Serializable

interface FragmentDelegate : Serializable {
    fun createFragmentManager() : FragmentManager
}