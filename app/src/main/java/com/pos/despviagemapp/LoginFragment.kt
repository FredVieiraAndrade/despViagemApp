package com.pos.despviagemapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.pos.despviagemapp.databinding.FragmentLoginBinding
import com.pos.despviagemapp.models.Login
import com.pos.despviagemapp.models.Usuario

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)


        binding.btnAutenticar.setOnClickListener(View.OnClickListener {
            val usuario = binding.txtColaborador.text
            val senha = binding.txtSenha.text

            val login = Login(login = usuario.toString(), senha = senha.toString())

            viewModel.post(login)
        })

        return binding.root
    }

}