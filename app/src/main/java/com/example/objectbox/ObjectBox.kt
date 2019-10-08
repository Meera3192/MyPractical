package com.example.objectbox

import android.content.Context
import io.objectbox.BoxStore

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.objectbox
 * Class_Name : ObjectBox
 * Description : This class use for create ObjectBox instance.
 */
class ObjectBox {
    companion object {

        var boxStore: BoxStore? = null

        // Use this method for create instance of objectbox
        fun initialize(context: Context) {
            boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build()
        }

        fun get(): BoxStore? {
            return boxStore
        }
    }
}