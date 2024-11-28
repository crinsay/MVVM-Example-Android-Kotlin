package com.example.phonebook.models

interface IContactRepository {
    fun addContact(contact: Contact)
    fun editContact(index: Int, contact: Contact)
}