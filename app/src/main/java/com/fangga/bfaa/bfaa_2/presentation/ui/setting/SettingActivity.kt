package com.fangga.bfaa.bfaa_2.presentation.ui.setting

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.fangga.bfaa.bfaa_2.base.BaseActivity
import com.fangga.bfaa.bfaa_2.databinding.ActivitySettingBinding
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    private lateinit var viewModel: SettingViewModel

    override fun inflateViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivitySettingBinding.binder() {
        viewModel = ViewModelProvider(this@SettingActivity)[SettingViewModel::class.java]


        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.saveTheme(isChecked)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getTheme().observe(this@SettingActivity) { isLightModeActive ->
                if (isLightModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = false
                }
            }
        }

        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}