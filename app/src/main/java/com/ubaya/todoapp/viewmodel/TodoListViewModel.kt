package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.model.TodoDatabase
import com.ubaya.todoapp.util.buildDb
import com.ubaya.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodoListViewModel(application: Application): AndroidViewModel(application),CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh(){
        todoLoadErrorLD.value = false
        loadingLD.value = true
        launch {
            //val db = Room.databaseBuilder(getApplication(),TodoDatabase::class.java,"newtododb").build()
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo: Todo){
        launch {
            //val db = Room.databaseBuilder(getApplication(),TodoDatabase::class.java,"newtododb").build()
            val db = buildDb(getApplication())
            //db.todoDao().Todo(todo)
            db.todoDao().updateis_doneTodo(todo.uuid)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
}