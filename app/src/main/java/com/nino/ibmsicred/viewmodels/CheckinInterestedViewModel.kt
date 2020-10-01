package com.nino.ibmsicred.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nino.ibmsicred.repositories.EventsRepository

/**********************************************************************************************
 * EventsRepository - classe view-model que faz o checkin de determinada pessoa associada a um evento.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class CheckinInterestedViewModel

    (application: Application) : AndroidViewModel(application) {
    //Repositório para acesso aos dados.
    private var eventsRepository: EventsRepository? = null

    //Dados da resposta obtida.
    private var checkinInterestedLiveData: LiveData<String?>? = null

    /**********************************************************************************************
     * startup - método que instancia os objetos.
     *********************************************************************************************/
    fun startup() {
        eventsRepository = EventsRepository()
        checkinInterestedLiveData = eventsRepository!!.getCheckinInterestedLiveData()
    }

    /**********************************************************************************************
     * checkinInterested - método que chama a API que insere uma pessoa associada a um evento.
     * @param eventId - Identificador do evento.
     * @param name - Nome da pessoa.
     * @param email - e-mail da pessoa.
     *********************************************************************************************/
    fun checkinInterested(eventId: String?, name: String?, email: String?) {
        eventsRepository!!.checkinInterested(eventId, name, email)
    }

    /**********************************************************************************************
     * checkinInterestedLiveData - método que retorna o resultado da inserção.
     *********************************************************************************************/
    fun checkinInterestedLiveData(): LiveData<String?>? {
        return checkinInterestedLiveData
    }
}