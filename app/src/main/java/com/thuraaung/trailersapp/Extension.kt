package com.thuraaung.trailersapp

import android.content.Context
import com.google.android.material.chip.Chip

fun Context.createChip(t : String) : Chip {
    return Chip(this).apply {
        text = t
        isCheckable = false
    }
}