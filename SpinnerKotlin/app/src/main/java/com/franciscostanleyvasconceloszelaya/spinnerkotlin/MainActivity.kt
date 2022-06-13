package com.franciscostanleyvasconceloszelaya.spinnerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.franciscostanleyvasconceloszelaya.spinnerkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var mBinding: ActivityMainBinding

    private lateinit var aaCountries: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        aaCountries = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item)
        aaCountries.addAll(listOf("Mexico", "El Salvador", "España", "Chile"))
        aaCountries.add("Argentina")

        mBinding.spinnerCountries.onItemSelectedListener = this
        mBinding.spinnerCountries.adapter = aaCountries
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val countrySelected = aaCountries.getItem(position)
        mBinding.tvSelected.text = countrySelected
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}