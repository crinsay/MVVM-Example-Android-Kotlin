package com.example.phonebook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentContactEditBinding
import com.example.phonebook.models.ContactRepository
import com.example.phonebook.models.IContactRepository
import com.example.phonebook.viewmodels.ContactEditViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactEdit.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactEdit : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var contactEditViewModel: ContactEditViewModel
    private lateinit var binding: FragmentContactEditBinding

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        val contactRepo: IContactRepository = ContactRepository()
        contactEditViewModel = ContactEditViewModel(contactRepo)

        binding = FragmentContactEditBinding.inflate(inflater, container, false)
        binding.contactEditViewModel = contactEditViewModel
        binding.lifecycleOwner = viewLifecycleOwner // Important for LiveData

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton = view.findViewById(R.id.saveButton)
        saveButton.isEnabled=false

        //do zmiany
        saveButton.setOnClickListener{
            contactEditViewModel.saveContact(null)
            goToList()
        }

        cancelButton= view.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener{
            goToList()
        }

        contactEditViewModel.isSaveButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            saveButton.isEnabled = isEnabled
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactEdit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactEdit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun goToList(){
        requireActivity()
            .supportFragmentManager.commit {
                replace<ContactsList>(R.id.fragmentContainer)
            }
    }
}