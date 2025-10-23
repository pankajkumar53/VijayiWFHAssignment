package com.pandroid.vijayiwfhassignment.ui.home

import androidx.lifecycle.ViewModel
import com.pandroid.vijayiwfhassignment.core.State
import com.pandroid.vijayiwfhassignment.data.model.MediaDetails
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

    private val _homeState = MutableStateFlow<State<Pair<List<Title>, List<Title>>>>(State.Idle)
    val homeState: StateFlow<State<Pair<List<Title>, List<Title>>>> = _homeState

    private val _detailState = MutableStateFlow<State<MediaDetails>>(State.Idle)
    val detailState: StateFlow<State<MediaDetails>> = _detailState

    init {
        loadData()
    }

    fun loadData() {
        _homeState.value = State.Loading
        disposable.add(
            repository.fetchMoviesAndShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> _homeState.value = State.Success(result) },
                    { error -> _homeState.value = State.Error(error.message, error) }
                )
        )
    }

    fun getMediaDetails(id: Int) {
        _detailState.value = State.Loading
        disposable.add(
            repository.getMediaDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> _detailState.value = State.Success(result) },
                    { error -> _detailState.value = State.Error(error.message, error) }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}