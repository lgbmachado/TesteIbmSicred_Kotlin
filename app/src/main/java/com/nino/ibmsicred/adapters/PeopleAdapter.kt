package com.nino.ibmsicred.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nino.ibmsicred.R
import com.nino.ibmsicred.databinding.LayoutPeopleItemBinding
import com.nino.ibmsicred.models.People

/**********************************************************************************************
 * PeopleAdapter - classe adapter que define os itens do recycler-view que exibe as pessoas
 *                 associadas ao eventos.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class PeopleAdapter constructor(private val people: List<People>) : RecyclerView.Adapter<PeopleAdapter.EventsViewHolder>() {

    private lateinit var binding: LayoutPeopleItemBinding
    private lateinit var layoutInflater: LayoutInflater

    /**********************************************************************************************
     * onCreateViewHolder - evento gerado ao criar o adapter.
     * @param parent - View pai do item.
     * @param viewType - Tipo da View.
     *********************************************************************************************/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleAdapter.EventsViewHolder {
        layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_people_item, parent, false)

        return EventsViewHolder(binding)
    }

    /**********************************************************************************************
     * getItemCount - método de exibe a quantidade de itens a ser exibida.
     *********************************************************************************************/
    override fun getItemCount() = people.size

    /**********************************************************************************************
     * onBindViewHolder - evento gerado ao exibir determinado item.
     * @param holder - Objeto que representa o ítem a ser exibido.
     * @param position - Índice do item na lista.
     *********************************************************************************************/
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(people.get(position))
    }

    /**********************************************************************************************
     * EventsViewHolder - classe local que define o ítem a ser exido.
     * HISTÓRICO
     * 30/09/2020 - Luiz Guilherme - Versão inicial.
     **********************************************************************************************/
    class EventsViewHolder(binding: LayoutPeopleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var binding: LayoutPeopleItemBinding

        /**********************************************************************************************
         * init - construtor da classe.
         * @param binding - Databinding para acessar os componentes declarados no layout do adapter.
         *********************************************************************************************/
        init {
            this.binding = binding
        }

        /**********************************************************************************************
         * bind - método que associa determinada pessoa.
         * @param people - Pessoa selecionado.
         *********************************************************************************************/
        fun bind(people: People) {
            binding.people = people
            binding.executePendingBindings()
        }
    }
}