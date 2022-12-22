package com.example.test1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kyc.databinding.ActivityKycBinding
import com.google.android.material.textfield.TextInputEditText



class KycActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKycBinding
    lateinit var panNumberText: TextInputEditText
    lateinit var dayOfBirth: TextInputEditText
    lateinit var monthOfBirth: TextInputEditText
    lateinit var yearOfBirth: TextInputEditText
    val kycViewModel: KycViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKycBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNext.setOnClickListener {

        }


    }


}