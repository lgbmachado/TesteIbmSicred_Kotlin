package com.nino.ibmsicred.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.nino.ibmsicred.R
import com.nino.ibmsicred.databinding.LayoutDialogInterestedBinding
import com.nino.ibmsicred.viewmodels.CheckinInterestedViewModel

/**********************************************************************************************
 * CheckinInterestedDialog - fragmento do tipo caixa de diálogo utilizado para obter os dados de
 *                           checkin de uma pessoa interessada no evento.
 * HISTÓRICO
 * 30/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
open class CheckinInterestedDialog : DialogFragment() {

    //Databinding para acesso aos componentes visuais do layout.
    private lateinit var binding: LayoutDialogInterestedBinding

            companion object {
        //Objeto view-model para o checkin da pessoa.
        var checkinInterestedViewModel: CheckinInterestedViewModel? = null
    }

    //Identificador do evento.
    public var eventId: String? = null

    /**********************************************************************************************
     * CheckinInterestedDialog - construtor da classe que instancia o objeto estático.
     * @param eventId - Id dos envento a ser associado a pessoa.
     *********************************************************************************************/
    open fun startup(eventId: String?): CheckinInterestedDialog? {


        //Instancia o objeto.
        val frag = CheckinInterestedDialog()

        //Envia os agumentos
        val args = Bundle()
        args.putString("event_id", eventId)
        frag.arguments = args

        //Retorna a caixa de diálogo
        return frag
    }


    /**********************************************************************************************
     * onCreateView - evento gerado ao criar a view da caixa de diálogo.
     * @param inflater - Objeto inflater.
     * @param container
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Instancia o databinding.
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.layout_dialog_interested, null, false
        )

        //Obtém os argumentos
        eventId = requireArguments().getString("event_id", "-1")

        //Instancia o objeto view-model
        checkinInterestedViewModel = ViewModelProviders.of(this).get(
            CheckinInterestedViewModel::class.java
        )
        //Inicia o objeto view-model.
        checkinInterestedViewModel!!.startup()

        //Define o processo de inserção dos dados da pessoa.
        checkinInterestedViewModel!!.checkinInterestedLiveData()!!
            .observe(this, { checkinInterestedResponse ->
                //Retornou alguma coisa?
                if (checkinInterestedResponse != null) {
                    binding.getRoot().let {
                        Snackbar.make(
                            it, resources.getString(
                                R.string.success_send_data_interested
                            ), Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else {
                    binding.getRoot().let {
                        Snackbar.make(
                            it, resources.getString(
                                R.string.error_send_data_interested
                            ), Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                dismiss()
            })

        //Define o evento ao clicar no botão de cancelar a operação
        binding.buttonCancel.setOnClickListener(
            View.OnClickListener { //Fecha a caixa de diálogo.
                dismiss()
            })

        //Define o evento ao clicar no botão de inserir a pessoa.
        binding.buttonOk.setOnClickListener(
            View.OnClickListener {
                if (binding.edtNameInterested.getText().toString()
                        .trim().length > 0 && binding.edtEMail.getText()
                        .toString().trim().length > 0
                ) {
                    //Inicia o processo de inserção da pessoa.
                    checkinInterestedViewModel!!.checkinInterested(
                        eventId,
                        binding.edtNameInterested.getText().toString(),
                        binding.edtEMail.getText().toString()
                    )
                    //Fecha a caixa de diálogo.
                    dismiss()
                } else {
                    binding.getRoot().let { it1 ->
                        Snackbar.make(
                            it1,
                            resources.getString(R.string.incomplete_data_interested),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            })
        return binding.getRoot()
    }

    /**********************************************************************************************
     * onViewCreated - evento gerado após a criação da view da caixa de diálogo.
     * @param view - view criada.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Força a exibição do tecladinho.
        //dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }
}