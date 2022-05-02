package com.pos.despviagemapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pos.despviagemapp.R
import com.pos.despviagemapp.models.Lancamento
import com.pos.despviagemapp.ui.declaracoes.DeclaracoesViewModel

class LancamentoAdapter (
    private val context: Context,
    private val dataset: List<Lancamento>

) : RecyclerView.Adapter<LancamentoAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val lancamentoID: TextView = view.findViewById(R.id.txtLstDespLancamentoID)
        val tipoDespesa: TextView = view.findViewById(R.id.txtLstDespTipoDespesa)
        val valorDespesa: TextView = view.findViewById(R.id.txtLstDespValor)
        val btnExcluir: ImageView = view.findViewById(R.id.btnDespExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val adapterLayout = LayoutInflater
                                    .from(parent.context)
                                    .inflate(R.layout.list_despesa, parent, false)
            return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.lancamentoID.text = item.LancamentoID.toString()

        val situacao = when(item.TipoDespesaID)
        {
            0 -> "Alimentação"
            1 -> "Hospedagem"
            2 -> "Taxi"
            3 -> "Ônibus"
            4 -> "Avião"
            5 -> "Combustível"
            else -> "Outros"
        }
        holder.tipoDespesa.text = situacao
        holder.valorDespesa.text = item.ValorDespesa.toString()


        holder.btnExcluir.setOnClickListener(View.OnClickListener{
            try {
                var viewModel = DeclaracoesViewModel()
                viewModel.deleteLancamento(item.LancamentoID!!.toInt())
                Toast.makeText(context , "Despesa excluída com sucesso.", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(context , "Despesa não excluída.", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun getItemCount() = dataset.size

}
