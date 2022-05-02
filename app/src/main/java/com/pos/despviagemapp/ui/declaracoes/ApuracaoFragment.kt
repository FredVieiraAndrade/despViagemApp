package com.pos.despviagemapp.ui.declaracoes

import android.os.Bundle
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
import com.pos.despviagemapp.R
import com.pos.despviagemapp.UsuarioViewModel
import com.pos.despviagemapp.databinding.FragmentApuracaoBinding
import com.pos.despviagemapp.models.Viagem

class ApuracaoFragment : Fragment() {

    private lateinit var binding: FragmentApuracaoBinding
    private val viewModel: DeclaracoesViewModel by activityViewModels()
    private val vmUsuario: UsuarioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_apuracao, container, false)
        val arg = ApuracaoFragmentArgs.fromBundle(requireArguments())
        val spinner: Spinner = binding.cboApurCentroCusto

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.centro_custo,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.txtApurProtocoloID.setText("${arg.id}")
        viewModel.getDeclaracao(arg.id)
        viewModel.declaracao.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.txtApurTotalDespesas.setText("${arg.totalDespesas}")
                var saldo: Double
                for(item in it) {
                    binding.txtApurTotalAdiantamento.setText("${item.Adiantamento}")
                    saldo = arg.totalDespesas.toDouble() - item.Adiantamento.toString().toDouble()
                    binding.txtApurTotalDevolverReceber.setText("${saldo}")
                }
            }
        })

        binding.btnApurEnviar.setOnClickListener(View.OnClickListener {

            try {
                val viagem = Viagem (
                    DataInicio =  "",
                    DataFim = "",
                    Motivo = 0,
                    Chamado =  "",
                    Adiantamento = 0.0,
                    Justificativa = "",
                    CentroCusto = binding.cboApurCentroCusto.selectedItemId.toInt(),
                    DebitoCredito = binding.txtApurTotalDevolverReceber.text.toString().toDouble(),
                    Matricula = 0,
                    Situacao = 1,
                    ViagemID = 0
                )

                viewModel.apuracao(arg.id, viagem)
                it.findNavController()
                    .navigate(ApuracaoFragmentDirections.actionApuracaoFragmentToListaViagensFragment())

            } catch (e: Exception) {
                Toast.makeText(context , "Erro ao enviar a apuração.", Toast.LENGTH_SHORT).show()
            }
        })

        vmUsuario.response.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.txtApurBanco.setText("${it.banco}")
                binding.txtApurAgencia.setText("${it.agencia}")
                binding.txtApurConta.setText("${it.conta_corrente}")
            }
        })

        return binding.root
    }

}