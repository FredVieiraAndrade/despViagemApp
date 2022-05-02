package com.pos.despviagemapp.ui.declaracoes

import android.icu.text.MessagePattern
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pos.despviagemapp.R
import com.pos.despviagemapp.adapter.LancamentoAdapter
import com.pos.despviagemapp.databinding.FragmentLancamentoBinding
import com.pos.despviagemapp.models.Lancamento
import kotlin.math.sign
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class LancamentoFragment : Fragment() {
    private lateinit var binding: FragmentLancamentoBinding
    private val viewModel: DeclaracoesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lancamento, container, false)
        val arg = LancamentoFragmentArgs.fromBundle(requireArguments())

        /*Carregar campos view*/
        binding.lbProtocoloLanId.setText("${arg.id}")

        val spinner: Spinner = binding.cboTipoDespesa

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.tipo_despesa,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        AtualizarLista(arg.id)

        binding.btnAdcionarDesp.setOnClickListener(View.OnClickListener {

            try {
                var lancamento = Lancamento (
                    LancamentoID = 0,
                    ViagemID = arg.id,
                    ValorDespesa = binding.txtValorDespesa.text.toString().toDouble(),
                    TipoDespesaID = binding.cboTipoDespesa.selectedItemId.toInt(),
                    Observacao = binding.txtObservacaoDesp.text.toString()
                )
                viewModel.postLancamento(lancamento)
                viewModel.lancamentoPost.observe(viewLifecycleOwner,{
                    if(it != null) {
                        AtualizarLista(arg.id)

                        binding.txtValorDespesa.setText("")
                        binding.cboTipoDespesa.setSelection(0)
                        binding.txtObservacaoDesp.setText("")
                    }
                })
            } catch (e: Exception) {
                Toast.makeText(context , "Erro ao incluir despesa.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnApurarLancamentos.setOnClickListener(View.OnClickListener {

            if(binding.txtValorTotal.text.toString().toDouble() === 0.0)
            {
                Toast.makeText(context , "Deve ser incluido despesa antes apurar.", Toast.LENGTH_SHORT).show()
            }else {
                it.findNavController()
                    .navigate(LancamentoFragmentDirections.actionLancamentoFragmentToApuracaoFragment(arg.id, binding.txtValorTotal.text.toString()))
            }

        })

        return binding.root
    }

    private fun AtualizarLista(id: Int) {
        /*Carregar lista de despesas*/
        viewModel.getLancamentos(id)
        viewModel.responseLst.observe(viewLifecycleOwner, {

            if(it.size > 0) {
                val context = requireContext()
                val myDataSet = it
                val recyclerView = binding.listaDespesaView.findViewById<RecyclerView>(R.id.lista_despesa_view)
                recyclerView.adapter = LancamentoAdapter(context, myDataSet)
                recyclerView.setHasFixedSize(true)


                var totalDespesas: Double = 0.0
                for(item in it) {
                    totalDespesas += item.ValorDespesa!!
                }
                binding.txtValorTotal.setText("${totalDespesas}")
            }
        })
    }

}