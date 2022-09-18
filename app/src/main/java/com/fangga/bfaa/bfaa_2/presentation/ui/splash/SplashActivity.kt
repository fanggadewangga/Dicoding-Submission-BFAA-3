package com.fangga.bfaa.bfaa_2.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.fangga.bfaa.bfaa_2.base.BaseActivity
import com.fangga.bfaa.bfaa_2.databinding.ActivitySplashBinding
import com.fangga.bfaa.bfaa_2.presentation.ui.main.MainActivity
import com.fangga.bfaa.bfaa_2.utils.Constant.TIME_SPLASH
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var viewModel: SplashViewModel

    override fun inflateViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivitySplashBinding.binder() {
        viewModel = ViewModelProvider(this@SplashActivity)[SplashViewModel::class.java]

        val handler = Handler(mainLooper)

        handler.postDelayed({
            viewModel.getTheme().observe(this@SplashActivity) { isLightModeActive ->
                if (isLightModeActive) {
                    intentToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    intentToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }, TIME_SPLASH)

        supportActionBar?.hide()
    }

    private fun intentToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}