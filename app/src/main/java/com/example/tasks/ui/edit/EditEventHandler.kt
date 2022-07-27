package com.example.tasks.ui.edit

interface EditEventHandler {
    fun editTitle()
    fun editDateTime()
    fun editCategory()
    fun editPriority()
    fun deleteTask()
    fun saveChanges()
}