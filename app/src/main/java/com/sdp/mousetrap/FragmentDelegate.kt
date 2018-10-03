package com.sdp.mousetrap

import android.app.Fragment
import android.support.v4.app.FragmentManager

interface FragmentDelegate {
    fun createFragmentManager() : FragmentManager
}