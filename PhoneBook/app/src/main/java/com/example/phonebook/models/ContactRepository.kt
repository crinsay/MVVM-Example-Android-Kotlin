package com.example.phonebook.models

class ContactRepository: IContactRepository {

    private val contacts = mutableListOf<Contact>()

    override fun addContact(contact: Contact) {
        contacts.add(contact)
    }

    override fun editContact(index: Int, contact: Contact){
        contacts[index] = contact
    }
}