package com.example.phonebook.models

interface IContactRepository {
    fun getAllContacts(): List<Contact>
    fun addContact(contact: Contact)
    fun editContact(index: Int, contact: Contact)
    fun deleteContact(index: Int)
}