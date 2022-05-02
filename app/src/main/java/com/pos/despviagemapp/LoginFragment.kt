package com.pos.despviagemapp

import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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

           val usuario: String = binding.txtColaborador.text.toString()
           val senha: String = binding.txtSenha.text.toString()

            if(usuario.toString().equals("") or senha.toString().equals("") )
           {
               Toast.makeText(context , "Campo colaborador ou senha não podem ser vazios", Toast.LENGTH_SHORT).show()
           }
           else
           {
               val login = Login(login = usuario, senha = senha)
               viewModel.post(login)
               viewModel.response.observe(viewLifecycleOwner, {
                   if(it.token != null) {

                    binding.dadosUsuario = viewModel

                       binding.btnAutenticar
                           .findNavController()
                           .navigate(LoginFragmentDirections
                                        .actionLoginFragmentToListaViagensFragment())
                    }
                   else
                   {
                       Toast.makeText(context , "Colaborador ou senha inválidos", Toast.LENGTH_SHORT).show()
                   }
               })
           }

        })
        return binding.root
    }

}