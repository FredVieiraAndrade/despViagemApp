package com.pos.despviagemapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pos.despviagemapp.adapter.ViagemAdapter
import com.pos.despviagemapp.databinding.FragmentListaViagensBinding

class ListaViagensFragment : Fragment() {

    private lateinit var binding: FragmentListaViagensBinding
    private val viewModel: ListaViagemViewModel by activityViewModels()
    private val vmUsuario: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lista_viagens, container, false)

        try {
            vmUsuario.response.observe(viewLifecycleOwner, {
                if(it != null) {
                    AtualizarLista(it.matricula!!.toInt())
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context , "Não foi possivel carregar a lista de viagem, tente mais tarde.", android.widget.Toast.LENGTH_SHORT).show()
        }
        binding.btnAdicionar.setOnClickListener(View.OnClickListener {
            it.findNavController()
                .navigate(ListaViagensFragmentDirections.actionListaViagensFragmentToDeclaracaoFragment(0))
        })
        return binding.root
    }

    private fun AtualizarLista(matricula: Int) {
        viewModel.get(matricula)
        viewModel.response.observe(viewLifecycleOwner, {
            if (it.size == 0) {
                Toast.makeText(context , "O colaborador não possui viagens.", Toast.LENGTH_SHORT).show()
            }
            val context = requireContext()
            val myDataSet = it
            val recyclerView =  binding.recyclerView.findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView.adapter = ViagemAdapter(context, myDataSet)
            recyclerView.setHasFixedSize(true)
        })
    }
}