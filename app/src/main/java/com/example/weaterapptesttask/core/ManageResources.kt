package com.example.weaterapptesttask.core

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface ManageResources {

    fun getString(@StringRes id: Int): String

    class Base @Inject constructor(
        private val context: Context
    ): ManageResources{
        override fun getString(id: Int) = context.getString(id)

    }

}
