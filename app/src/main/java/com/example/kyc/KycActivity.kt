package com.example.test1

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.kyc.R
import com.example.kyc.databinding.ActivityKycBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.collectLatest


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
        setKycInfoText(binding)
        with(binding) {
            etPanNumber.doOnTextChanged { text, start, before, count ->
                kycViewModel.panNumber.value = text.toString()
            }
            dayOfBirth.doOnTextChanged { text, start, before, count ->
                kycViewModel.dayOfBirth.value = text.toString()
            }

            monthOfBirth.doOnTextChanged { text, start, before, count ->
                kycViewModel.monthOfBirth.value = text.toString()
            }
            yearOfBirth.doOnTextChanged { text, start, before, count ->
                kycViewModel.yearOfBirth.value = text.toString()
            }
        }

        lifecycleScope.launchWhenCreated {
            kycViewModel.panErrorMessage.observe(this@KycActivity) { _errorMessage ->
                _errorMessage?.let {
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.etPanNumber.error = _errorMessage
                }
            }
            kycViewModel.birthDateErrorMessage.observe(this@KycActivity) { _errorMessage ->
                _errorMessage?.let {
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = _errorMessage
                }
            }
            kycViewModel.isFormValid.collectLatest { isValid ->
                binding.btnNext.apply {
                    isEnabled = isValid
                    backgroundTintList = ColorStateList.valueOf(
                        if (isValid) {
                            ContextCompat.getColor(this@KycActivity, R.color.purple_700)
                        } else {
                            ContextCompat.getColor(this@KycActivity, R.color.gray)
                        }
                    )
                }
            }
        }

        binding.btnNext.setOnClickListener {
            Toast.makeText(this, "Details Submitted Succesfully", Toast.LENGTH_SHORT).show()

        }
        binding.btnNoPan.setOnClickListener {
            finish()
        }


    }

    private fun setKycInfoText(binding: ActivityKycBinding) {
        val spannable = SpannableString(resources.getString(R.string.kyc_info_label))
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.purple_700)),
            106, // start
            116, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.labelKycDisclaimer.text = spannable
    }


}