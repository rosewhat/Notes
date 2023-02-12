package com.rosewhat.notes.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rosewhat.notes.data.InfoListRepositoryImpl
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository
import com.rosewhat.notes.domain.usecases.AddInfoItemUseCase
import com.rosewhat.notes.domain.usecases.EditInfoItemUseCase
import com.rosewhat.notes.domain.usecases.GetInfoItemUseCase
import kotlinx.coroutines.launch

class InfoItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InfoListRepository = InfoListRepositoryImpl(application)

    private val getInfoItemUseCase = GetInfoItemUseCase(infoListRepository = repository)
    private val addInfoItemUseCase = AddInfoItemUseCase(infoListRepository = repository)
    private val editInfoItemUseCase = EditInfoItemUseCase(infoListRepository = repository)


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _infoItem = MutableLiveData<InfoItem>()
    val infoItem: LiveData<InfoItem>
        get() = _infoItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getInfoItem(infoItemId: Int) {
        viewModelScope.launch {
            _infoItem.value = getInfoItemUseCase.getInfoItem(infoItemId)
        }
    }

    fun addInfoItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val infoItem = InfoItem(name = name, count = count, enabled = true)
                addInfoItemUseCase.addInfoItem(infoItem)
                finishWork()
            }

        }
    }

    fun editInfoItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _infoItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editInfoItemUseCase.editInfoItem(item)
                    finishWork()
                }

            }

        }

    }

    // пребразование введеных данных
    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: DEFAULT_COUNT
        } catch (e: Exception) {
            DEFAULT_COUNT
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }


    companion object {
        private const val DEFAULT_COUNT = 0
        private const val EXCEPTION_STRING = "Необходимо ввести число!"
    }
}