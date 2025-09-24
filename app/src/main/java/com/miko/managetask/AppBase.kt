package com.miko.managetask

import android.app.Application
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.HiltAndroidApp

// Application class is used to initialize the application with Hilt.
@HiltAndroidApp
class AppBase : Application()