package io.sunil.roomdbapp.ui.userlist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.sunil.roomdbapp.R
import io.sunil.roomdbapp.adapter.UserListAdapter
import io.sunil.roomdbapp.databinding.FragmentUserListBinding
import io.sunil.roomdbapp.viewmodel.UserViewModel

class UserListFragment : Fragment() {

    private var _fragmentUserListBinding: FragmentUserListBinding? = null

    private val userListBing get() = _fragmentUserListBinding

    private lateinit var userViewModel: UserViewModel
    private val userListAdapter = UserListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentUserListBinding = FragmentUserListBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Add menu
        setHasOptionsMenu(true)
        return userListBing?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.readAllData.observe(viewLifecycleOwner){user ->
            userListAdapter.setData(user)
        }

        userListBing?.apply {
            userListRecyclerView.layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            userListRecyclerView.adapter = userListAdapter

        }

        userListBing?.floatingActionButton?.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }
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
            userViewModel.deleteAllUSer()
            Toast.makeText(requireContext(), "Successfully removed :", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("NO"){_,_ ->}
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete")
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentUserListBinding = null
    }
}