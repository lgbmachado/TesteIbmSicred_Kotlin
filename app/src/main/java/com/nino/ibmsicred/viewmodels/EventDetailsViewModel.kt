package com.nino.ibmsicred.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nino.ibmsicred.models.Event
import com.nino.ibmsicred.repositories.EventsRepository

/**********************************************************************************************
 * EventDetailsViewModel - classe view-model que obtém detalhes de um evento.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventDetailsViewModel

    (application: Application) : AndroidViewModel(application) {
    //Repositório para acesso aos dados.
    private var eventsRepository: EventsRepository? = null

    //Dados da resposta obtida.
    var eventDetailLiveData: LiveData<Event?>? = null
        private set

    /**********************************************************************************************
     * startup - método que instancia os objetos.
     *********************************************************************************************/
    fun startup() {
        eventsRepository = EventsRepository()
        eventDetailLiveData = eventsRepository!!.getEventDetailLiveData()
    }

    /**********************************************************************************************
     * getEventDetail - método que chama a API que obtém os eventos.
     * @param id - Identificador do evento.
     *********************************************************************************************/
    fun getEventDetail(id: String?) {
        eventsRepository!!.getEventDetail(id)
    }
}