package com.codepatissier.keki.src.main.consumer.search.map

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapView;

class MapActivity: BaseActivity<ActivityMapBinding>(ActivityMapBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapView = MapView(this)
        binding.mapView.addView(mapView)
        backClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

}