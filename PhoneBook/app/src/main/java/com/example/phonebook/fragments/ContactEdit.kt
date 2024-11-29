package com.example.phonebook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentContactEditBinding
import com.example.phonebook.factories.ViewModelFactory
import com.example.phonebook.viewmodels.ContactEditViewModel

private const val ARG_CONTACT_INDEX = "param1"

class ContactEdit : Fragment() {
    private var contactIndexParam: String? = null
    private var contactIndex: Int? = null

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var contactEditViewModel: ContactEditViewModel
    private lateinit var binding: FragmentContactEditBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactIndexParam = it.getString(ARG_CONTACT_INDEX)
            contactIndex = contactIndexParam?.toIntOrNull()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        contactEditViewModel = ViewModelProvider(this, ViewModelFactory)[ContactEditViewModel::class.java]

        binding = FragmentContactEditBinding.inflate(inflater, container, false)
        binding.contactEditViewModel = contactEditViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (contactIndex != null) {
            contactEditViewModel.fillEditTexts(contactIndex!!)
        }

        saveButton = view.findViewById(R.id.saveButton)
        saveButton.isEnabled = false

        saveButton.setOnClickListener {
            contactEditViewModel.saveContact(contactIndex)
            goToContactsList()
        }

        cancelButton = view.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            goToContactsList()
        }

        contactEditViewModel.isSaveButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            saveButton.isEnabled = isEnabled
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(contactIndexParam: String) =
            ContactEdit().apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTACT_INDEX, contactIndexParam)
                }
            }
    }

    private fun goToContactsList(){
        requireActivity()
            .supportFragmentManager.commit {
                replace<ContactsList>(R.id.fragmentContainer)
            }
    }
}