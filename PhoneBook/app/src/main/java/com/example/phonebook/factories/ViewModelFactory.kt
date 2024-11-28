package com.example.phonebook.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.phonebook.models.ContactRepository
import com.example.phonebook.models.IContactRepository
import com.example.phonebook.viewmodels.ContactEditViewModel
import com.example.phonebook.viewmodels.ContactsListViewModel

object ViewModelFactory : ViewModelProvider.Factory {
    private val contactRepo: IContactRepository = ContactRepository()

    override fun <TViewModel : ViewModel> create(modelClass: Class<TViewModel>): TViewModel {
        if (modelClass.isAssignableFrom(ContactsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsListViewModel(contactRepo) as TViewModel
        }
        else if (modelClass.isAssignableFrom(ContactEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactEditViewModel(contactRepo) as TViewModel
        }
        throw IllegalArgumentException("Unknown ViewModel class (not even possible)")
    }
}
