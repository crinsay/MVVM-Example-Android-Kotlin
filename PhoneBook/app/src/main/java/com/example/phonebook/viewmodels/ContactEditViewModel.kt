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
            val contact = Contact(firstName.value!!.trim(),
                                  lastName.value!!.trim(),
                                  phoneNumber.value!!.trim())

            if (index == null)
                contactRepository.addContact(contact)
            else
                contactRepository.editContact(index, contact)
        }
    }

    fun fillEditTexts(index: Int) {
        val contactToEdit = contactRepository.getContact(index)

        firstName.value = contactToEdit.firstName
        lastName.value = contactToEdit.lastName
        phoneNumber.value = contactToEdit.phoneNumber
    }

    private fun isContactCreatable(): Boolean =
        ((firstName.value != null && lastName.value != null && phoneNumber.value != null)
                && (firstName.value!!.trim().isNotEmpty()
                && lastName.value!!.trim().isNotEmpty()
                && phoneNumber.value!!.trim().isNotEmpty()))

    //LiveData for save button:
    val isSaveButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(firstName) { checkFields() }
        addSource(lastName) { checkFields() }
        addSource(phoneNumber) { checkFields() }
    }

    private fun checkFields() {
        isSaveButtonEnabled.value = isContactCreatable()
    }
}

