package io.sunil.roomdbapp.ui.adduser

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.sunil.roomdbapp.R
import io.sunil.roomdbapp.model.User
import io.sunil.roomdbapp.databinding.FragmentAddUserBinding
import io.sunil.roomdbapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*


class AddUserFragment : Fragment() {

    private var _fragmentAddUserBinding: FragmentAddUserBinding? = null

    private val addUserBinding get() = _fragmentAddUserBinding

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentAddUserBinding = FragmentAddUserBinding.inflate(inflater, container, false)
         userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return addUserBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addUserBinding!!.apply {
            addButton.setOnClickListener {
                addUserToDatabase()
            }
        }
    }

    private fun addUserToDatabase() {
        val firstName = addFirstNameEditText.text.toString()
        val lastName = addLastNameEditText.text.toString()
        val age = addAgeEditText.text

        if (inputCheck(firstName, lastName, age)){
            // Create User
            val user = User(id = 0, firstName = firstName, lastName = lastName, age = Integer.parseInt(age.toString()))

            // Add Data to Database
            userViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()

            // Navigate Back
            findNavController().navigate(R.id.action_addUserFragment_to_userListFragment)
        }
        else Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(firstName:String, lastName:String, age:Editable):Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentAddUserBinding = null
    }
}