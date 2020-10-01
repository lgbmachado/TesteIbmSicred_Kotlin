package com.nino.ibmsicred.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nino.ibmsicred.models.Event
import com.nino.ibmsicred.repositories.EventsRepository

/**********************************************************************************************
 * EventDetailsViewModel - classe view-model que obtém a lista de eventos.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventsViewModel

    (application: Application) : AndroidViewModel(application) {
    //Repositório para acesso aos dados.
    private var eventsRepository: EventsRepository? = null

    //Dados da resposta obtida.
    var eventsLiveData: LiveData<List<Event>?>? = null
        private set

    /**********************************************************************************************
     * startup - método que instancia os objetos.
     *********************************************************************************************/
    fun startup() {
        eventsRepository = EventsRepository()
        eventsLiveData = eventsRepository!!.eventListLiveData
    }

    /**********************************************************************************************
     * init - método que chama a API que obtém os detalhes do evento.
     *********************************************************************************************/
    val events: Unit
        get() {
            eventsRepository!!.getEventsList()
        }
}