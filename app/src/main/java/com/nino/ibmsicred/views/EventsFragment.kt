package com.nino.ibmsicred.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nino.ibmsicred.R
import com.nino.ibmsicred.adapters.EventsAdapter
import com.nino.ibmsicred.databinding.FragmentEventsBinding
import com.nino.ibmsicred.viewmodels.EventsViewModel

/**********************************************************************************************
 * EventsFragment - classe do fragment que lista os eventos.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding

    companion object {
        //Objeto view-model que gerencia a obteção da lista de eventos disponíveis.
        var eventsViewModel: EventsViewModel? = null
    }

    //Adapter que define os itens do recycler-view.
    private var eventsAdapter: EventsAdapter? = null

    /**********************************************************************************************
     * OnCreate - evento gerado ao criar o fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Instancia o objeto view-model.
        eventsViewModel = ViewModelProviders.of(this).get(
            EventsViewModel::class.java
        )
        //Inicia o objeto view-model.
        eventsViewModel!!.startup()

        //Define o processo de busca dos eventos.
        eventsViewModel!!.eventsLiveData!!.observe(this, { eventsResponse ->
            //Retornou alguma coisa?
            if (eventsResponse != null) {
                //Sim, associa a lista obtida com o adapter do recycler-view.
                eventsAdapter = EventsAdapter(eventsResponse) { idEvent: String ->

                    //Exibe o cursor de espera.
                    binding.progressBar.setVisibility(View.VISIBLE)

                    //Define o parâmetro
                    val bundle = bundleOf("id_event" to idEvent)

                    //Abre o fragment que exibe os detalhes do evento.
                    view?.findNavController()?.navigate(R.id.action_eventsFragment_to_eventDetailFragment, bundle)
                }
                binding.recyclerViewEvents.adapter = eventsAdapter
            } else {
                Snackbar.make(
                    binding.root,
                    resources.getString(R.string.error_get_events),
                    Snackbar.LENGTH_LONG
                ).show()
            }

            //Esconde o cursor de espera.
            binding.progressBar.visibility = View.GONE
        })
    }

    /**********************************************************************************************
     * onCreateView - evento gerado ao criara a view do fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Instancia o databinding.
        binding = FragmentEventsBinding.inflate(inflater, container, false)

        //Define o gerenciador do recycler view.
        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(context)

        //Inicia o processo de busca dos eventos.
        eventsViewModel!!.events

        return binding.getRoot()
    }
}