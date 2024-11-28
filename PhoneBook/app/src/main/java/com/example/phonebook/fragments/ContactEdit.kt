package com.example.phonebook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentContactEditBinding
import com.example.phonebook.viewmodels.ContactEditViewModel

private const val ARG_CONTACT_INDEX = "param1"

class ContactEdit : Fragment() {
    // TODO: Rename and change types of parameters
    private var contactIndexParam: String? = null
    private var contactIndex: Int? = null

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
    ): View? {
        // Inflate the layout for this fragment
        contactEditViewModel = ViewModelProvider(this)[ContactEditViewModel::class.java]

        binding = FragmentContactEditBinding.inflate(inflater, container, false)
        binding.contactEditViewModel = contactEditViewModel
        binding.lifecycleOwner = viewLifecycleOwner // Important for LiveData

        return binding.root
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
}