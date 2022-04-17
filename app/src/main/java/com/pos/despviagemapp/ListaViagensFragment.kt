package com.pos.despviagemapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pos.despviagemapp.databinding.FragmentListaViagensBinding

class ListaViagensFragment : Fragment() {

    private lateinit var binding: FragmentListaViagensBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lista_viagens, container, false)

        return binding.root
    }
}