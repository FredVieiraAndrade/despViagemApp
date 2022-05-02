package com.pos.despviagemapp.ui.declaracoes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.pos.despviagemapp.R
import com.pos.despviagemapp.UsuarioViewModel
import com.pos.despviagemapp.databinding.FragmentDeclaracaoBinding
import com.pos.despviagemapp.models.Viagem
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class DeclaracaoFragment : Fragment() {

    private lateinit var binding: FragmentDeclaracaoBinding
    private val viewModel: DeclaracoesViewModel by activityViewModels()
    private val vmUsuario: UsuarioViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_declaracao, container, false)
        val arg = DeclaracaoFragmentArgs.fromBundle(requireArguments())
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
        val spinner: Spinner = binding.txtMotivoViagem

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.motivo_viagem,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        if (arg.id > 0) {
            viewModel.getDeclaracao(arg.id)
            viewModel.declaracao.observe(viewLifecycleOwner, {

                if(it != null) {
                    for (item in it)
                    {
                        val dataInicio = LocalDate.parse(item.DataInicio.toString(), inputFormatter)
                        val dataFinal = LocalDate.parse(item.DataFim.toString(), inputFormatter)
                        binding.txtDataInicio.setText("${outputFormatter.format(dataInicio)}")
                        binding.txtDataFinal.setText("${outputFormatter.format(dataFinal)}")
                        binding.txtMotivoViagem.setSelection(item.Motivo!!.toInt())
                        binding.txtNumeroChamado.setText(item.Chamado)
                        binding.txtDeclAdiantamento.setText(item.Adiantamento.toString())
                        binding.txtJustificativa.setText(item.Justificativa)
                    }
                }

                binding.btnIncluir.setText("alterar")
                binding.btnIncluir.setOnClickListener(View.OnClickListener {
                    try {

                        val dataInicio = binding.txtDataInicio.text.toString().split("/").toTypedArray()
                        val dataFinal = binding.txtDataFinal.text.toString().split("/").toTypedArray()

                        val viagem = Viagem (
                            DataInicio =  "${dataInicio[2]}-${dataInicio[1]}-${dataInicio[0]}",
                            DataFim = "${dataFinal[2]}-${dataFinal[1]}-${dataFinal[0]}",
                            Motivo = binding.txtMotivoViagem.selectedItemId.toInt(),
                            Chamado =  binding.txtNumeroChamado.text.toString(),
                            Adiantamento = binding.txtDeclAdiantamento.text.toString().toDouble(),
                            Justificativa = binding.txtJustificativa.text.toString(),
                            CentroCusto = 0,
                            DebitoCredito = 0.0,
                            Matricula = 0,
                            Situacao = 0,
                            ViagemID = arg.id
                        )

                        viewModel.putDeclaracao(arg.id, viagem)
                        it.findNavController()
                            .navigate(DeclaracaoFragmentDirections.actionDeclaracaoFragmentToLancamentoFragment(arg.id))

                    } catch (e: Exception) {
                        Toast.makeText(context , "Erro ao alterar a declaração.", Toast.LENGTH_SHORT).show()
                    }
                 })
            })
        }

        binding.btnIncluir.setOnClickListener(View.OnClickListener {

            try {
                val dataInicio = binding.txtDataInicio.text.toString().split("/").toTypedArray()
                val dataFinal = binding.txtDataFinal.text.toString().split("/").toTypedArray()

                vmUsuario.response.observe(viewLifecycleOwner, {
                    if (it != null){
                        val viagem = Viagem (
                            DataInicio =  "${dataInicio[2]}-${dataInicio[1]}-${dataInicio[0]}",
                            DataFim = "${dataFinal[2]}-${dataFinal[1]}-${dataFinal[0]}",
                            Motivo = binding.txtMotivoViagem.selectedItemId.toInt(),
                            Chamado =  binding.txtNumeroChamado.text.toString(),
                            Adiantamento = binding.txtDeclAdiantamento.text.toString().toDouble(),
                            Justificativa = binding.txtJustificativa.text.toString(),
                            CentroCusto = 0,
                            DebitoCredito = 0.0,
                            Matricula = it.matricula,
                            Situacao = 0,
                            ViagemID = arg.id
                        )

                        viewModel.postDeclaracao(viagem)
                        viewModel.declaracaoPost.observe(viewLifecycleOwner, {
                            if(it != null){
                                binding
                                    .btnIncluir
                                    .findNavController()
                                    .navigate(DeclaracaoFragmentDirections.actionDeclaracaoFragmentToLancamentoFragment(it.id!!))
                            }
                        })
                    }
                })

            } catch (e: Exception) {
                Toast.makeText(context , "Erro ao incluir a declaração.", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

}