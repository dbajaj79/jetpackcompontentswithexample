package com.example.myapplication.common

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(msg:String)
{
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}