package com.zasko.boxtool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zasko.boxtool.base.act.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}