package com.jrodriguezva.mifilmo.ui.login

import android.os.Bundle
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}