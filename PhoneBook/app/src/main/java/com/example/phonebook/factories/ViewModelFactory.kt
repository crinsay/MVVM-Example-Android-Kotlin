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
        return when {
            modelClass.isAssignableFrom(ContactsListViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                ContactsListViewModel(contactRepo) as TViewModel
            }
            modelClass.isAssignableFrom(ContactEditViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                ContactEditViewModel(contactRepo) as TViewModel
            }
            else -> throw IllegalArgumentException("Unknown ViewModel (not even possible")
        }
    }
}
