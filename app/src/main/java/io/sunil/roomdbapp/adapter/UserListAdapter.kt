package io.sunil.roomdbapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.sunil.roomdbapp.R
import io.sunil.roomdbapp.model.User
import io.sunil.roomdbapp.databinding.UserRowLayoutBinding
import io.sunil.roomdbapp.ui.userlist.UserListFragmentDirections

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent.context.getSystemService(LayoutInflater::class.java).inflate(
            R.layout.user_row_layout,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        UserRowLayoutBinding.bind(holder.itemView).apply {
            idTextView.text = currentItem.id.toString()
            firstNameTextView.text = currentItem.firstName
            lastNameTextView.text = currentItem.lastName
            ageTextView.text = currentItem.age.toString()

            rowLayout.setOnClickListener {
                val action = UserListFragmentDirections.actionUserListFragmentToUpdateUserFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}