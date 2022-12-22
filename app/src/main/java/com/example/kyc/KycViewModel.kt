package com.example.test1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.util.regex.Matcher
import java.util.regex.Pattern

class KycViewModel : ViewModel() {

    val panNumber = MutableStateFlow("")
    val dayOfBirth = MutableStateFlow("")
    val monthOfBirth = MutableStateFlow("")
    val yearOfBirth = MutableStateFlow("")
    val panPattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
    var MAX_VALID_YR = 9999
    var MIN_VALID_YR = 1800
    val panErrorMessage: LiveData<String?>
        get() = _panErrorMessage
    private var _panErrorMessage = MutableLiveData<String?>()

    val birthDateErrorMessage: LiveData<String?>
        get() = _birthDateErrorMessage
    private var _birthDateErrorMessage = MutableLiveData<String?>()

    val isFormValid =
        combine(panNumber, dayOfBirth, monthOfBirth, yearOfBirth) { _pan, _day, _month, _year -> val panMatcher: Matcher = panPattern.matcher(_pan)
            val isPanvalid = panMatcher.matches()
            val isValidBirthDate = try {
                isValidBirthDate(_day.toInt(), month = _month.toInt(), year = _year.toInt())
            } catch (e: NumberFormatException) {
                false
            }

            if(isPanvalid.not()){
                if(_pan.isEmpty()){
                    _panErrorMessage.postValue("Please enter a pan number")
                }else
                {
                    _panErrorMessage.postValue("Pan number is not valid")
                }

            }
            if(isValidBirthDate.not()){
                if(_day.isEmpty() || _month.isEmpty()||_year.isEmpty()){
                    _birthDateErrorMessage.postValue("Please enter the Birthdate")
                }else
                {
                    _birthDateErrorMessage.postValue("Please enter a correct Birthdate")
                }
            }
            else{
                _birthDateErrorMessage.postValue("")
            }
            isValidBirthDate and isPanvalid

        }


    fun isLeap(year: Int): Boolean {

        return year % 4 == 0 &&
                year % 100 != 0 ||
                year % 400 == 0
    }


    fun isValidBirthDate(
        day: Int,
        month: Int,
        year: Int
    ): Boolean {
        if (year > MAX_VALID_YR ||
            year < MIN_VALID_YR
        ) return false
        if (month < 1 || month > 12) return false
        if (day < 1 || day > 31) return false
        if (month == 2) {
            return if (isLeap(year)) day <= 29 else day <= 28
        }
        return if (month == 4 || month == 6 || month == 9 || month == 11
        ) day <= 30 else true
    }


}