package com.nino.ibmsicred.models

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

/**********************************************************************************************
 * Event - classe do modelo de dados de determinado evento.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class Event{
    //Lista das pessoas associadas ao evento.
    @field:SerializedName("people")val people: List<People>? = null

    //Data do evento
    @field:SerializedName("date")val date: Long = 0

    //Descrição do evento.
    @field:SerializedName("description")val description: String = ""

    //Url da imagem do evento.
    @field:SerializedName("image")val image: String = ""

    //Longitude da localização do evento.
    @field:SerializedName("longitude")val longitude: Double = 0.0

    //Latitude da localização do evento.
    @field:SerializedName("latitude")val latitude: Double = 0.0

    //Preço do evento.
    @field:SerializedName("price")val price: Double = 0.0

    //Título do evento.
    @field:SerializedName("title") val title: String = ""

    //Identificador do evento
    @field:SerializedName("id") val id: String = ""

    companion object {

        /**********************************************************************************************
         * getDateFormated - método para formatar a data a partir do valor numérico do campo "date".
         * @param view - Textview onde será exibida a data.
         * @param dateLong - data em formato numérico.
         *********************************************************************************************/
        @BindingAdapter("dateFormated")
        @JvmStatic
        fun getDateFormated(view: TextView, dateLong: Long) {
            view.text = DateFormat.format("dd/MM/yyyy HH:mm", Date(dateLong)).toString()
        }

        /**********************************************************************************************
         * getPriceFormated - método para formatar o valor no padrão monetário local.
         * @param view - Textview onde será exibido o valor.
         * @param price - valor.
         *********************************************************************************************/
        @BindingAdapter("priceFormated")
        @JvmStatic
        fun getPriceFormated(view: TextView, price: Double) {
            view.text = NumberFormat.getCurrencyInstance().format(price)
        }

        /**********************************************************************************************
         * loadImageList - método para obter a imagem do evento no formato circular para ser usado
         *                 no recycle-view.
         * @param view - Imageview onde será exibida a imagem.
         * @param imageUrl - Urls da imagem.
         *********************************************************************************************/
        @BindingAdapter("imageList")
        @JvmStatic
        fun loadImageList(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions().circleCrop())
                .into(view)
        }

        /**********************************************************************************************
         * loadImageDetail - método para obter a imagem do evento no formato retangular para ser
         *                   usado nos detalhes.
         * @param view - Imageview onde será exibida a imagem.
         * @param imageUrl - Urls da imagem.
         *********************************************************************************************/
        @BindingAdapter("imageDetail")
        @JvmStatic
        fun loadImageDetail(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions().centerCrop())
                .into(view)
        }
    }
}