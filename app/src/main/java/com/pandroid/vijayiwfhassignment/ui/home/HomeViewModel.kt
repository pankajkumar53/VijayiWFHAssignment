package com.pandroid.vijayiwfhassignment.ui.home

import androidx.lifecycle.ViewModel
import com.pandroid.vijayiwfhassignment.core.State
import com.pandroid.vijayiwfhassignment.data.model.Title
import com.pandroid.vijayiwfhassignment.data.repo.MediaRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val repository: MediaRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _state = MutableStateFlow<State<Pair<List<Title>, List<Title>>>>(State.Idle)
    val uiState: StateFlow<State<Pair<List<Title>, List<Title>>>> = _state

    fun loadData() {
        _state.value = State.Loading
        disposable.add(
            repository.fetchMoviesAndShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> _state.value = State.Success(result) },
                    { error -> _state.value = State.Error(error.message, error) }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}