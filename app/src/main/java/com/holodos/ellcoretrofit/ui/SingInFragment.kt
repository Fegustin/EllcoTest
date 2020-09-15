package com.holodos.ellcoretrofit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.holodos.ellcoretrofit.R
import kotlinx.android.synthetic.main.fragment_sing_in.*


class SingInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sing_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSingIn.setOnClickListener {
            val action = SingInFragmentDirections.actionSingInFragmentToTicketListFragment()
            it.findNavController().navigate(action)
        }
    }
}