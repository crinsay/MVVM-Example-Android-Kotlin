package com.example.phonebook.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.models.Contact
import com.example.phonebook.models.IContactRepository


class ContactsListViewModel(private val contactRepo: IContactRepository): ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    var contacts: LiveData<List<Contact>> = _contacts

    init {
        _contacts.value = contactRepo.getAllContacts()
    }

    fun deleteContact(index: Int) {
        contactRepo.deleteContact(index)
        _contacts.value = contactRepo.getAllContacts()
    }
}