package com.example.phonebook.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.models.Contact
import com.example.phonebook.models.IContactRepository

class ContactEditViewModel(private val contactRepository: IContactRepository) : ViewModel() {
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

    fun saveContact(index: Int?) {
        if (isContactCreatable()) {
            val contact = Contact(firstName.value!!, lastName.value!!, phoneNumber.value!!)

            if (index == null)
                contactRepository.addContact(contact)
            else
                contactRepository.editContact(index, contact)
        }
    }

    private fun isContactCreatable(): Boolean =
        ((firstName.value!= null &&  lastName.value!= null &&  phoneNumber.value!=null)
                && (firstName.value.toString().trim()!=""
                && lastName.value.toString().trim()!=""
                && phoneNumber.value.toString().trim()!=""))

     //LiveData na potrzeby przycisku
    val isSaveButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(firstName) { checkFields() }
        addSource(lastName) { checkFields() }
        addSource(phoneNumber) { checkFields() }
    }

    private fun checkFields() {
        isSaveButtonEnabled.value = isContactCreatable()
    }
}

