package com.nino.ibmsicred.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nino.ibmsicred.R

/**********************************************************************************************
 * EventsActivity - classe da activity principal do aplicativo.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
class EventsActivity : AppCompatActivity() {

    companion object {
        var context: Context? = null
    }

    /**********************************************************************************************
     * OnCreate - evento gerado ao criar a activity.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        context = applicationContext
    }
}