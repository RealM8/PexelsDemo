package com.realmatesoft.pexelsdemo.ui

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


fun AppCompatActivity.showSnackbarWithMessage(rootView : View, @StringRes messageResId : Int) {
        Snackbar.make(rootView, messageResId, Snackbar.LENGTH_LONG)
                .show()
    }