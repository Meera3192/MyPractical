package com.example.app

import android.app.Application
import com.example.objectbox.ObjectBox

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.app
 * Class_Name : MyApplication
 * Description : Application class used for initialize ObjectBox.
 */
class MyApplication :Application()
{
    override fun onCreate() {
        super.onCreate()
        ObjectBox.initialize(this)
    }
}