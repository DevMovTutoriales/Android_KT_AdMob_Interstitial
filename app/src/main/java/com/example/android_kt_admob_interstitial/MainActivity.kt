package com.example.android_kt_admob_interstitial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_kt_admob_interstitial.databinding.ActivityMainBinding
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var interstitial: InterstitialAd? = null
    private var count_level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        binding.tvLevel.text = "NIVEL " + count_level

        initInterstitialAds()

        binding.btnNextLevel.setOnClickListener {
            showInterstitial()
        }
    }

    private fun showInterstitial() {
        if(interstitial != null){
            interstitial!!.show(this)
            interstitial= null
        } else{
            binding.tvLevel.text = "NIVEL " + (++count_level)
            initInterstitialAds()
            binding.btnNextLevel.isEnabled = false
        }
    }

    private fun initInterstitialAds() {
        var adRequest = com.google.android.gms.ads.AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback(){
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial = interstitialAd
                    Toast.makeText(this@MainActivity, "Anuncio encontrado", Toast.LENGTH_SHORT).show()
                    binding.btnNextLevel.isEnabled = true
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interstitial = null
                    Toast.makeText(this@MainActivity, "Verifique su conexión", Toast.LENGTH_SHORT).show()
                    binding.btnNextLevel.isEnabled = true
                }
            }
        )
    }
}