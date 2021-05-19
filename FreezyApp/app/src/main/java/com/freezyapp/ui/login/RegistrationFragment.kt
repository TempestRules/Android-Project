package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.freezyapp.R
import com.freezyapp.databinding.RegistrationFragmentBinding
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: RegistrationFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate<RegistrationFragmentBinding>(inflater,
            R.layout.registration_fragment,container,false)


        binding.backToLogin.setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_registration_fragment_to_login_fragment)
        }
        binding.registerAccount.setOnClickListener{
            register()

        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    fun register(){
        viewModel.name = binding.name.text.toString()
        viewModel.username = binding.editTextTextPersonName.text.toString()
        viewModel.password = binding.editPassword.text.toString()
        viewModel.password2 = binding.editPassword2.text.toString()

        viewModel.createAccount()
        requireView().findNavController().navigate(R.id.action_registration_fragment_to_login_fragment)
        val sb = Snackbar.make(requireView(), R.string.registered, Snackbar.LENGTH_SHORT)
        sb.show()
    }



}