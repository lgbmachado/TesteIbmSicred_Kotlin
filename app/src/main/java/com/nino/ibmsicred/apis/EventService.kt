package com.nino.ibmsicred.apis

import com.nino.ibmsicred.models.Event
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**********************************************************************************************
 * EventService - interface que faz as chamadas da API.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
interface EventService {
    /**********************************************************************************************
     * getEventList - chama o método GET que lista os eventos.
     *********************************************************************************************/
    @get:GET("events")
    val eventList: Call<List<Event?>?>?

    /**********************************************************************************************
     * getEventList - chama o método GET que obtém detalher de determinado evento.
     * @param id - Identificador do evento a ser pesquisado.
     *********************************************************************************************/
    @GET("events/{id}")
    fun getEventDetail(@Path("id") id: String?): Call<Event?>?

    /**********************************************************************************************
     * checkinInterested - chama o método POST que envia dados de determinada pessoa interessada no evento.
     * @param eventId - Identificador do evento.
     * @param name - Nome da pessoa.
     * @param email - e-mail da pessoa.
     *********************************************************************************************/
//    @Headers("Content-Type: application/json")
//    @POST("checkin")
//    suspend fun checkinInterested(
//        @Body dataCheckin: String
//    ): ResponseBody

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("checkin")
    fun checkinInterested(
        @Field("eventId") eventId: String?,
        @Field("name") name: String?,
        @Field("email") email: String?
    ): Call<String?>?

//    @Headers("Content-Type: text/html")
//    @FormUrlEncoded
//    @POST("/checkin")
//    fun checkinInterested(
//        @Field("eventId") eventId: String?,
//        @Field("name") name: String?,
//        @Field("email") email: String?
//    ): Call<String?>?
}