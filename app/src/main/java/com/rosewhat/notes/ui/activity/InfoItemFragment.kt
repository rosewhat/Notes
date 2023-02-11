package com.rosewhat.notes.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rosewhat.notes.R
import com.rosewhat.notes.databinding.FragmentInfoItemBinding
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.models.InfoItem.Companion.UNDEFINED_ID
import com.rosewhat.notes.ui.viewModel.InfoItemViewModel


class InfoItemFragment : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentInfoItemBinding? = null
    private val binding: FragmentInfoItemBinding
        get() = _binding ?: throw RuntimeException("FragmentInfoItemBinding == null")

    private lateinit var viewModel: InfoItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var screenModeId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    // контекст - активити
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("activity must impl listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_info_item, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[InfoItemViewModel::class.java]
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Error"
            } else {
                null
            }
            binding.tilCount.error = message
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Error"
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            // фрагмент еще не прикреплен или отписался
            // requireActivity - способ не безопасный и будет краш
            onEditingFinishedListener.editingFinished()

        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }


    }

    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun launchEditMode() {

        viewModel.getInfoItem(screenModeId)
        viewModel.infoItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.buttonInfoSave.setOnClickListener {
            viewModel.editInfoItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.buttonInfoSave.setOnClickListener {
            viewModel.addInfoItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }
    }

    private fun parseParams() {
        var args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw java.lang.RuntimeException("Screen mode is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw java.lang.RuntimeException("Screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_INFO_ITEM_ID)) {
                throw java.lang.RuntimeException("info item id is absent")
            }
            screenModeId = args.getInt(EXTRA_INFO_ITEM_ID, InfoItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun editingFinished()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_INFO_ITEM_ID = "extra_info_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): InfoItemFragment {
            return InfoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(infoItemId: Int): InfoItemFragment {
            return InfoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_INFO_ITEM_ID, screenModeId)
                }
            }
        }

    }
}