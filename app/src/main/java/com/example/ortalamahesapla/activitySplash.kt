package com.example.ortalamahesapla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activty_splash.*

class activitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_splash)


        var asagidanGelenButon=AnimationUtils.loadAnimation(this,R.anim.asagidangelenbuton) //animasyon icin tanımladık butonu ve görseli :)
        btnOrtalamaHesaplaAnim.animation=asagidanGelenButon

        var yukaridanGelenBalon=AnimationUtils.loadAnimation(this,R.anim.yukaridangelenbalon)
        imgBalon.animation=yukaridanGelenBalon

        var asagiyaGeriDonenButon=AnimationUtils.loadAnimation(this,R.anim.asagigidenbuton)
        var yukariyaGeriDonenBalon=AnimationUtils.loadAnimation(this,R.anim.yukariyagidenbalon)

        btnOrtalamaHesaplaAnim.setOnClickListener {

            imgBalon.startAnimation(yukariyaGeriDonenBalon)
            btnOrtalamaHesaplaAnim.startAnimation(asagiyaGeriDonenButon)

            object :CountDownTimer(1000,1000){
                override fun onFinish() {

                    var intent=Intent(applicationContext,MainActivity::class.java) //erkanlar arası geciş icin intent kavramı
                    startActivity(intent)
                }

                override fun onTick(millisUntilFinished: Long) {
                }

            }.start()



        }
    }
}
