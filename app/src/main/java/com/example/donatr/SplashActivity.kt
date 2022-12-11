package com.example.donatr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.donatr.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)

        splashAnim.setAnimationListener(
            object: Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    //
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // start a new activity here..
                    val intentSplash = Intent()
                    intentSplash.setClass(
                        this@SplashActivity, LoginActivity ::class.java
                    )
                    startActivity(intentSplash)
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    //
                }
            }
        )
        binding.ivLogo.startAnimation(splashAnim)
    }
}