package com.rosewhat.notes.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.templates.TemperatureControlTemplate.MODE_UNKNOWN
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.rosewhat.notes.R
import com.rosewhat.notes.databinding.ActivityInfoItemBinding
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.ui.viewModel.InfoItemViewModel
import java.lang.RuntimeException

class InfoItemActivity : AppCompatActivity(), InfoItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: InfoItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var screenModeId = InfoItem.UNDEFINED_ID


    private val binding by lazy {
        ActivityInfoItemBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            launchRightMode()
        }





    }

    override fun editingFinished() {
        TODO("Not yet implemented")
        finish()
    }

    // parseIntent()

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> InfoItemFragment.newInstanceEditItem(screenModeId)
            MODE_ADD  -> InfoItemFragment.newInstanceAddItem()
            else      -> throw RuntimeException("Unknown screen mode $screenMode")

        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.info_item_container, fragment)
            .commit()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_INFO_ITEM_ID = "extra_info_item_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, InfoItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditITem(context: Context, infoItem: Int): Intent {
            val intent = Intent(context, InfoItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_INFO_ITEM_ID, infoItem)
            return intent
        }
    }
}