package com.nino.ibmsicred.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nino.ibmsicred.R
import com.nino.ibmsicred.databinding.LayoutEventItemBinding
import com.nino.ibmsicred.models.Event

/**********************************************************************************************
 * EventsAdapter - classe adapter que define os itens do recycler-view que exibe os eventos.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventsAdapter constructor(private val events: List<Event>, val clickListener: (String) -> Unit) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    private lateinit var binding: LayoutEventItemBinding
    private lateinit var layoutInflater: LayoutInflater

    /**********************************************************************************************
     * onCreateViewHolder - evento gerado ao criar o adapter.
     * @param parent - View pai do item.
     * @param viewType - Tipo da View.
     *********************************************************************************************/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_event_item, parent, false)

        return EventsViewHolder(binding)
    }

    /**********************************************************************************************
     * getItemCount - método de exibe a quantidade de itens a ser exibida.
     *********************************************************************************************/
    override fun getItemCount() = events.size

    /**********************************************************************************************
     * onBindViewHolder - evento gerado ao exibir determinado item.
     * @param holder - Objeto que representa o ítem a ser exibido.
     * @param position - Índice do item na lista.
     *********************************************************************************************/
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        createClickListener(events.get(position).id)?.let { holder.bind(events.get(position), it) }
    }

    /**********************************************************************************************
     * createClickListener - método de chama o evento gerado ao selecionar algum item da lista.
     * @param eventId - Id do evento selecionado.
     *********************************************************************************************/
    private fun createClickListener(eventId: String): View.OnClickListener? {
        return View.OnClickListener { view ->
            clickListener(eventId)
        }
    }

    /**********************************************************************************************
     * EventsViewHolder - classe local que define o ítem a ser exido.
     * HISTÓRICO
     * 30/09/2020 - Luiz Guilherme - Versão inicial.
     **********************************************************************************************/
    class EventsViewHolder(binding: LayoutEventItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var binding: LayoutEventItemBinding

        /**********************************************************************************************
         * init - construtor da classe.
         * @param binding - Databinding para acessar os componentes declarados no layout do adapter.
         *********************************************************************************************/
        init {
            this.binding = binding
        }

        /**********************************************************************************************
         * bind - método que associa determinado evento.
         * @param event - Evento selecionado.
         * @param clickListener - Listener que vai monitorar o evento.
         *********************************************************************************************/
        fun bind(event: Event, clickListener: View.OnClickListener) {
            binding.event = event
            binding.setClickListener(clickListener)
            binding.executePendingBindings()
        }


    }
}