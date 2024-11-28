package com.example.phonebook.models

class ContactRepository: IContactRepository {

    private val _dbContext = InMemoryDatabase

    override fun getAllContacts(): List<Contact> {
        return _dbContext.contacts
    }

    override fun addContact(contact: Contact) {
        _dbContext.contacts.add(contact)
    }

    override fun editContact(index: Int, contact: Contact){
        _dbContext.contacts[index] = contact
    }

    override fun deleteContact(index: Int) {
        _dbContext.contacts.removeAt(index)
    }
}