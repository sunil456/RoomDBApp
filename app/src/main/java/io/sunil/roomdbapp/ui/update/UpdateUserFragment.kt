package io.sunil.roomdbapp.ui.update


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.sunil.roomdbapp.R
import io.sunil.roomdbapp.databinding.FragmentUpdateUserBinding
import io.sunil.roomdbapp.model.User
import io.sunil.roomdbapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_update_user.*


class UpdateUserFragment : Fragment() {

    private var _updateUserFragmentBinding: FragmentUpdateUserBinding? = null

    private val updateUserBinding get() = _updateUserFragmentBinding

    private val args by navArgs<UpdateUserFragmentArgs>()
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _updateUserFragmentBinding = FragmentUpdateUserBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Add menu
        setHasOptionsMenu(true)

        return updateUserBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUserBinding?.apply {
            updateFirstNameEditText.setText(args.currentUser.firstName)
            updateLastNameEditText.setText(args.currentUser.lastName)
            updateAgeEditText.setText(args.currentUser.age.toString())

            updateButton.setOnClickListener {
                updateData()
            }
        }
    }

    private fun updateData() {
        val firstName = updateFirstNameEditText.text.toString()
        val lastName = updateLastNameEditText.text.toString()
        val age = Integer.parseInt(updateAgeEditText.text.toString())

        if(inputCheck(firstName, lastName, updateAgeEditText.text)){

            // Create Current User Object
            val updatedUser = User(args.currentUser.id, firstName,lastName,age)

            // Update Current user
            userViewModel.updateUser(updatedUser)

            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()

            // Navigate Back
            findNavController().navigate(R.id.action_updateUserFragment_to_userListFragment)
        }
        else Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()

    }

    private fun inputCheck(firstName:String, lastName:String, age: Editable):Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES"){_,_ ->
            userViewModel.deleteUser(args.currentUser)

            Toast.makeText(requireContext(), "Successfully removed : ${args.currentUser.firstName}", Toast.LENGTH_SHORT).show()

            // Navigate Back
            findNavController().navigate(R.id.action_updateUserFragment_to_userListFragment)

        }
        builder.setNegativeButton("NO"){_,_ ->}
        builder.setTitle("Delete ${args.currentUser.firstName}")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _updateUserFragmentBinding = null
    }
}