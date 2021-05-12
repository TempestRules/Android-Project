package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.freezyapp.R
import com.freezyapp.backend.AccessToken
import com.freezyapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: LoginFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<LoginFragmentBinding>(inflater,
            R.layout.login_fragment,container,false)


        binding.register.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_login_fragment_to_registration_fragment) }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.login.setOnClickListener { onLogin() }

    }


    private fun onLogin(){
        viewModel.username = binding.username.text.toString()
        viewModel.password = binding.password.text.toString()

        viewModel.login()

        AccessToken.setContext(requireActivity().applicationContext)
        AccessToken.set(viewModel.mld.value!!)
    }

}