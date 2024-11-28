package com.example.phonebook.viewmodels

import androidx.lifecycle.ViewModel


class ContactsListViewModel: ViewModel() {

    fun deleteContact(index: Int) {
        println("Deleting contact with index: $index")
    }
}