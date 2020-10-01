package com.nino.ibmsicred.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.nino.ibmsicred.R
import com.nino.ibmsicred.adapters.EventsAdapter
import com.nino.ibmsicred.adapters.PeopleAdapter
import com.nino.ibmsicred.databinding.FragmentEventDetailBinding
import com.nino.ibmsicred.viewmodels.EventDetailsViewModel

/**********************************************************************************************
 * EventDetailFragment - classe do fragment que lista os eventos.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventDetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentEventDetailBinding

            companion object {
        //Objeto view-model para obter os detalhes do evento.
        var eventDetailsViewModel: EventDetailsViewModel? = null
    }

    //Identificador do evento.
    private var idEvent: String? = null

    //View que vai receber o fragment.
    private var container: ViewGroup? = null

    //Objeto que gerencia os mapas e a loccalização.
    private var mapFragment: SupportMapFragment? = null

    //Adapter que define os itens do recycler-view.
    private var peopleAdapter: PeopleAdapter? = null

     /**********************************************************************************************
     * OnCreate - evento gerado ao criar o fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Obtém o id do evento.
         idEvent =  arguments?.getString("id_event")

        //Instancia o objeto view-model.
        eventDetailsViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel::class.java)

        //Inicia o objeto view-model.
        eventDetailsViewModel!!.startup()

        //Define o processo de busca dos detalhes do evento.
        eventDetailsViewModel!!.eventDetailLiveData!!.observe(this,
            { eventDetailsResponse ->
                //Retornou alguma coisa?
                if (eventDetailsResponse != null) {
                    //Sim atualiza a tela
                    binding.setEvent(eventDetailsResponse)

                    //Conseguiu obter a localização?
                    if (eventDetailsResponse.latitude != 0.0 && eventDetailsResponse.longitude != 0.0){
                        //Exibe a localização
                        mapFragment!!.getMapAsync(this)
                    }
                    //Tem pessoas cadastradas?
                    if (eventDetailsResponse.people?.size!! > 0){
                        //Sim, exibe
                        binding.layPeople.visibility = View.VISIBLE

                        peopleAdapter = PeopleAdapter(eventDetailsResponse.people)
                        binding.recyclerViewPeople.adapter = peopleAdapter
                    }
                } else {
                    binding.getRoot()?.let {
                        Snackbar.make(
                            it,
                            resources.getString(R.string.error_get_events),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }

    /**********************************************************************************************
     * onCreateView - evento gerado ao criar a view do fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Obtém o container do fragment.
        this.container = container

        //Instancia o databinding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false)

        //Define o gerenciador do recycler view.
        binding.recyclerViewPeople.layoutManager = LinearLayoutManager(context)

        //Exibe o cursor de espera.
        binding.progressBar.setVisibility(View.VISIBLE)

        //Define ícone do botão de retorno.
        binding.detailToolbar.setNavigationIcon(R.drawable.ic_back)

        //Define o evento ao clicar no botão de retorno.
        binding.detailToolbar.setNavigationOnClickListener(
            View.OnClickListener {
                //Volta para a tela anterior.
                requireActivity().onBackPressed()
            })

        //Inicia o processo de busca dos detalhes do evento.
        eventDetailsViewModel!!.getEventDetail(idEvent)

        //Define o evento a clicar no botão par inserir interessado
        binding.btnAddPeople.setOnClickListener(
            View.OnClickListener {
                //Instancia a caixa de diálogo que será exibida.
                val checkinInterestedDialog: CheckinInterestedDialog? = CheckinInterestedDialog().startup(idEvent)
                //Deu tudo certo até agora?
                if (checkinInterestedDialog != null) {
                    //Sim, exibe
                    checkinInterestedDialog.show(requireActivity().supportFragmentManager, "Cadastrar Interessados")
                }
            })
        return binding.getRoot()
    }

    /**********************************************************************************************
     * onCreateView - evento gerado após criar a view do fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.mapEvent) as SupportMapFragment?
    }

    /**********************************************************************************************
     * onMapReady - evento gerado após o mapa estar disponível
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onMapReady(googleMap: GoogleMap) {
        //Define a posição.
        val position = binding.getEvent()?.latitude?.let {
            binding.getEvent()?.longitude?.let { it1 -> LatLng(it, it1) }
        }
        //Inclui o marcador.
        googleMap.addMarker(
            position?.let {
                MarkerOptions().position(it)
                    .title(binding.getEvent()?.title)
            }
        )
        //Posiciona e define o zoom.
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14f))
        binding.progressBar.visibility = View.GONE
    }
}