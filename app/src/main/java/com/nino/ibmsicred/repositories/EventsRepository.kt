package com.nino.ibmsicred.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nino.ibmsicred.apis.EventService
import com.nino.ibmsicred.models.Event
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**********************************************************************************************
 * EventsRepository - classe repository que define o acesso a API.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventsRepository {

    companion object {
        //Timeout em milisegundos para conexão.
        private const val TIMEOUT_CONNECT = 10

        //Timeout em milisegundos para escrita.
        private const val TIMEOUT_WRITE = 30

        //Timeout em milisegundos para leitura.
        private const val TIMEOUT_READ = 30

        //Url base para acesso à API.
        private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"
    }

    private val eventsService: EventService

    //Lista eventos obtidos
    private val eventsLiveData: MutableLiveData<List<Event>?>

    //Detalhes de evento obtido
    private val eventDetailLiveData: MutableLiveData<Event?>

    //Resultado da inserção.
    private val checkinInterestedLiveData: MutableLiveData<String?>

    /**********************************************************************************************
     * EventsRepository - construtor da classe.
     *********************************************************************************************/
    init {

        //Instancia a lista de eventos.
        eventsLiveData = MutableLiveData()
        //Instacia o objeto com os detalhes do evento.
        eventDetailLiveData = MutableLiveData()
        //Instancia a string com o resultado da inserção.
        checkinInterestedLiveData = MutableLiveData()

        //Instancia o objeto que fará a conexão.
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        //Instancia o serviço que envia a requisição e trata a resposta recebida.
        eventsService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventService::class.java)
    }

    /**********************************************************************************************
     * getEventListLiveData - método que retorna a lista de eventos obtida.
     *********************************************************************************************/
    val eventListLiveData: LiveData<List<Event>?>
        get() = eventsLiveData

    /**********************************************************************************************
     * getEvents - método que obtém a lista de eventos através da API.
     *********************************************************************************************/
    open fun getEventsList() {
        //Chama o serviço.
        eventsService.eventList?.enqueue(object : Callback<List<Event?>?> {
                //Chegou algo?
                override fun onResponse(
                    call: Call<List<Event?>?>,
                    response: Response<List<Event?>?>
                ) {
                    //Sim, mas deu tudo certo?
                    if (response.isSuccessful) {
                        //Sim, mas tem conteúdo?
                        if (response.body() != null) {
                            //Sim, retorna.
                            eventsLiveData.postValue(response.body() as List<Event>?)
                        }
                    } else {
                        //onFailure(null, Throwable())
                    }
                }

                //Deu erro?
                override fun onFailure(call: Call<List<Event?>?>, t: Throwable) {
                    //Sim, retorna nulo.
                    eventsLiveData.postValue(null)
                }
            })
    }

    /**********************************************************************************************
     * getEventDetailLiveData - método que retorna os detalhes do evento obtida.
     *********************************************************************************************/
    fun getEventDetailLiveData(): LiveData<Event?> {
        return eventDetailLiveData
    }

    /**********************************************************************************************
     * getEventDetail - método que obtém os detalhes de um eventos através da API.
     * @param id - Identificador do evento.
     *********************************************************************************************/
    fun getEventDetail(id: String?) {
        //Chama o serviço.
        eventsService.getEventDetail(id)?.enqueue(object : Callback<Event?> {
            //Chegou algo?
            override fun onResponse(call: Call<Event?>, response: Response<Event?>) {
                //Sim, mas deu tudo certo?
                if (response.isSuccessful) {
                    //Sim, mas tem conteúdo?
                    if (response.body() != null) {
                        //Sim, retorna.
                        eventDetailLiveData.postValue(response.body())
                    }
                } else {
                    //onFailure(null, Throwable())
                }
            }

            //Deu erro?
            override fun onFailure(call: Call<Event?>, t: Throwable) {
                //Sim, retorna nulo.
                eventDetailLiveData.postValue(null)
            }
        })
    }

    /**********************************************************************************************
     * getCheckinInterestedLiveData - método que retorna o resultadoa inserção de detrminada pessoa.
     *********************************************************************************************/
    fun getCheckinInterestedLiveData(): LiveData<String?> {
        return checkinInterestedLiveData
    }

    /**********************************************************************************************
     * checkinInterested - método que insere os dados de determinada pessoa associada a um evento.
     * @param eventId - Identificador do evento associado a pessoa.
     * @param name - Nome da pessoa.
     * @param email - e-mail da pessoa.
     *********************************************************************************************/
    fun checkinInterested(eventId: String?, name: String?, email: String?) {
        eventsService.checkinInterested(eventId, name, email)?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                //Sim, mas deu tudo certo?
                if (response.isSuccessful) {
                    //Sim, mas tem conteúdo?
                    if (response.body() != null) {
                        //Sim, retorna.
                        checkinInterestedLiveData.postValue(response.body())
                    }
                } else {
                    checkinInterestedLiveData.postValue(null)
                }
            }
            override fun onFailure(call: Call<String?>, t: Throwable) {
                checkinInterestedLiveData.postValue(null)
            }
        })
    }


}