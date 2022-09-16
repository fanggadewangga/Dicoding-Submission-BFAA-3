package com.fangga.bfaa.bfaa_2.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import com.fangga.bfaa.bfaa_2.base.BaseActivity
import com.fangga.bfaa.bfaa_2.databinding.ActivitySplashBinding
import com.fangga.bfaa.bfaa_2.presentation.ui.main.MainActivity
import com.fangga.bfaa.bfaa_2.utils.Constant.TIME_SPLASH
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun inflateViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivitySplashBinding.binder() {

        val handler = Handler(mainLooper)
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_SPLASH)

        supportActionBar?.hide()
    }
}