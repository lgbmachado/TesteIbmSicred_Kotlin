package com.nino.ibmsicred.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

/**********************************************************************************************
 * People - classe do modelo de dados de determinada pessoa associda a um evento.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class People {

    //Identificador da pessoa.
    @field:SerializedName("id") val id: Int = 0

    //Nome da pessoa
    @field:SerializedName("name") val name: String = ""

    //Identificador do evento associada a pessoa.
    @field:SerializedName("eventId") val eventId: Int = 0

    //Url da imagem da pessoa
    @field:SerializedName("picture") val picture: String = ""

    companion object {
        /**********************************************************************************************
         * loadImagePeople - método para obter a imagem da pessoa evento no formato circular.
         * @param view - Imageview onde será exibida a imagem.
         * @param imageUrl - Urls da imagem.
         *********************************************************************************************/
        @BindingAdapter("picturePeople")
        @JvmStatic
        fun loadImagePeople(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl).apply(RequestOptions().circleCrop())
                .into(view)
        }
    }
}