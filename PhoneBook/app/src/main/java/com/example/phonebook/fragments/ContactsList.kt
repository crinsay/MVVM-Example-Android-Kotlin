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
import androidx.fragment.app.replace
import com.example.phonebook.R
import com.example.phonebook.models.Contact
import com.example.phonebook.viewmodels.ContactsListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactsList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var contactsListView: ListView
    private lateinit var addContactButton: Button

    private val contactsListViewModel = ContactsListViewModel()

    private val testData = listOf(Contact("Leo", "Messi", "93893139183"),
        Contact("Leo11", "Messi11", "9389313918233"),
        Contact("Ronaldo", "xdddd", "938933"),
        Contact("Pessi", "Penaldo", "+48 663 224 506"),
        Contact("Pessi", "Penaldo", "+48 663 224 506"))

    inner class ContactsListAdapter(private val data: List<Contact>): BaseAdapter() {
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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addContactButton = view.findViewById(R.id.addButton)
        addContactButton.setOnClickListener{
            goToContactEdit(null)
        }

        contactsListView = view.findViewById(R.id.contactsListView)
        contactsListView.adapter = ContactsListAdapter(testData)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactsList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun goToContactEdit(index: Int?) {
        val contactEditFragment = ContactEdit.newInstance(index.toString())
        requireActivity().supportFragmentManager
            .commit {
                replace(R.id.fragmentContainer, contactEditFragment)
            }
    }
}