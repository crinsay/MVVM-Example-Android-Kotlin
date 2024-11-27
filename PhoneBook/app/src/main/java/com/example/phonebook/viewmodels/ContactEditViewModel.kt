package com.example.phonebook.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactEditViewModel : ViewModel() {
    val firstName = MutableLiveData<String>()
}