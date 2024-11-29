package com.example.phonebook.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.phonebook.R
import com.example.phonebook.factories.ViewModelFactory
import com.example.phonebook.models.Contact
import com.example.phonebook.viewmodels.ContactsListViewModel


class ContactsList : Fragment() {
    private lateinit var contactsListView: ListView
    private lateinit var addContactButton: Button
    private lateinit var contactsListAdapter: ContactsListAdapter
    private lateinit var contactsListViewModel: ContactsListViewModel


    //Custom adapter for ListView:
    inner class ContactsListAdapter(private var data: List<Contact>): BaseAdapter() {
        override fun getCount(): Int = data.size

        override fun getItem(position: Int): Any = data[position]

        override fun getItemId(position: Int): Long = position.toLong()

        @SuppressLint("SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val newConvertView = convertView ?: LayoutInflater.from(parent?.context).inflate(
                R.layout.fragment_contacts_list_element, parent, false
            )

            val contact = getItem(position) as Contact

            val fullNameTextView = newConvertView.findViewById<TextView>(R.id.fullNameTextView)
            val phoneNumberTextView = newConvertView.findViewById<TextView>(R.id.phoneNumberTextView)

            fullNameTextView.text = "${contact.firstName} ${contact.lastName}"
            phoneNumberTextView.text = contact.phoneNumber

            return newConvertView
        }

        fun updateData(newData: List<Contact>) {
            data = newData
            notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Adapter and ListView config:
        contactsListAdapter = ContactsListAdapter(emptyList())

        contactsListView = view.findViewById(R.id.contactsListView)
        contactsListView.adapter = contactsListAdapter

        contactsListViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            contactsListAdapter.updateData(contacts)
        }

        contactsListView.setOnItemLongClickListener { parent, view, position, _ ->
            val popupMenu = PopupMenu(parent.context, view)

            popupMenu.menuInflater
                .inflate(R.menu.menu_list_view_options, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.editOption -> {
                        val contactIndex = parent.adapter
                            .getItemId(position).toInt()
                        goToContactEdit(contactIndex)

                        true
                    }
                    R.id.deleteOption -> {
                        val contactIndex = parent.adapter
                            .getItemId(position).toInt()
                        contactsListViewModel.deleteContact(contactIndex)

                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
            true
        }

        //Add new contact button config:
        addContactButton = view.findViewById(R.id.addButton)
        addContactButton.setOnClickListener{
            goToContactEdit(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsListViewModel = ViewModelProvider(this, ViewModelFactory)[ContactsListViewModel::class.java]

        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsList()
    }


    //Go to ContactEdit fragment:
    private fun goToContactEdit(index: Int?) {
        val contactEditFragment = ContactEdit.newInstance(index.toString())
        requireActivity().supportFragmentManager
            .commit {
                replace(R.id.fragmentContainer, contactEditFragment)
            }
    }
}