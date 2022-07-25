package com.example.tasks.ui

import android.app.Application
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val SUCCESS_ADD_TASK = "added"
        const val SUCCESS_UPDATE_TASK = "updated"
        const val SUCCESS_DELETE_TASK = "deleted"

        const val ERROR_ADD_TITLE = "add the title of the task"
    }

    fun showSuccessToast(title: String, action: String) {
        val toastText = "Task '$title' successfully $action"
        Toast.makeText(getApplication(), toastText, Toast.LENGTH_SHORT).show()
    }

    fun showErrorToast(action: String) {
        Toast.makeText(getApplication(), "Please $action", Toast.LENGTH_SHORT).show()
    }

    fun fromEditText(editText: EditText) = editText.text.toString().trim()
}