package com.pos.despviagemapp.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pos.despviagemapp.*
import com.pos.despviagemapp.models.Resposta
import com.pos.despviagemapp.models.Viagem
import com.pos.despviagemapp.ui.declaracoes.DeclaracoesViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ViagemAdapter (
    private val context: Context,
    private val dataset: List<Viagem>

) : RecyclerView.Adapter<ViagemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val txtViagemId: TextView = view.findViewById(R.id.txtViagemId)
            val txtDataInicio: TextView = view.findViewById(R.id.txtDataInicio)
            val txtDataFim: TextView = view.findViewById(R.id.txtDataFim)
            val txtSituacao: TextView = view.findViewById(R.id.txtSituacao)
            val btnEditar: ImageView = view.findViewById(R.id.btnEditar)
            val btnExcluir: ImageView = view.findViewById(R.id.btnExcluir)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
                val adapterLayout = LayoutInflater
                                        .from(parent.context)
                                        .inflate(R.layout.list_item, parent, false)
                return ItemViewHolder(adapterLayout)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = dataset[position]

            val inputFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
            val dateInicio = LocalDate.parse(item.DataInicio.toString(), inputFormatter)
            val dateFim = LocalDate.parse(item.DataFim.toString(), inputFormatter)

            holder.txtViagemId.text = item.ViagemID.toString()
            holder.txtDataInicio.text = outputFormatter.format(dateInicio)
            holder.txtDataFim.text = outputFormatter.format(dateFim)

            val situacao = when (item.Situacao) {
                0 -> "Preparada"
                1 -> "Eviada"
                2 -> "Rejeitada"
                3 -> "Concluída"
                else -> "Excluída"
            }

            holder.txtSituacao.text = situacao

            if (item.Situacao === 3 || item.Situacao === 1) {
                holder.btnEditar.isVisible = false
                holder.btnExcluir.isVisible = false
            }

            holder.btnEditar.setOnClickListener(View.OnClickListener {
                it.findNavController()
                    .navigate(ListaViagensFragmentDirections.actionListaViagensFragmentToDeclaracaoFragment(item.ViagemID!!.toInt()))
            })

            holder.btnExcluir.setOnClickListener(View.OnClickListener{

                try {
                    var viewModel = ListaViagemViewModel()
                    viewModel.delete(item.ViagemID!!.toInt())
                    Toast.makeText(context , "Viagem sobre protocolo: ${item.ViagemID.toString()} excluída com sucesso.", Toast.LENGTH_SHORT).show()
                }
                catch (e: Exception) {
                    Toast.makeText(context , "Viagem não excluída.", Toast.LENGTH_SHORT).show()
                }
            })
        }

    override fun getItemCount() = dataset.size
}


