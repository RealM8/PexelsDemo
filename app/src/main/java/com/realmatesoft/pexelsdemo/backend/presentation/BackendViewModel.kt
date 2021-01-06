package com.realmatesoft.pexelsdemo.backend.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.slickypasscode.archcomponents.Event
import com.realmatesoft.pexelsdemo.R
import com.realmatesoft.pexelsdemo.backend.IBackendRepository
import com.realmatesoft.pexelsdemo.backend.data.Photo
import com.realmatesoft.pexelsdemo.backend.utils.ConnectionChecker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BackendViewModel(private var backendRepository : IBackendRepository, private var connectionChecker : ConnectionChecker) : ViewModel() {

    var getPhotosFromPexelsDisposable: Disposable? = null
    var getSpecificPhotoFromPexelsDisposable: Disposable? = null

    private val photosListViewState = MutableLiveData<PhotosListViewState>()
    fun getPhotosListViewState () : MutableLiveData<PhotosListViewState> {return photosListViewState}
    private val photoDetailsViewState = MutableLiveData<PhotoDetailsViewState>()
    fun getPhotoDetailsViewState () : MutableLiveData<PhotoDetailsViewState> {return photoDetailsViewState}

    init {
        getPhotosFromPexels()
    }

    fun getPhotosFromPexels() {
        getPhotosFromPexelsDisposable =
            backendRepository.getPhotosFromPexels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { photos -> photosListViewState.value = PhotosListViewState.Success(photos, Event(
                            if(connectionChecker.isNetworkAvailable()) 0 else R.string.offline_mode_warning)
                    )},
                    { photosListViewState.value = PhotosListViewState.Error(Event(R.string.accessing_database_failure), ErrorType.DATABASE_FAILURE) }
                )
        photosListViewState.value = PhotosListViewState.Loading
    }

    fun getSpecificPhotoFromLocalCache(pictureId : Int) {
        getSpecificPhotoFromPexelsDisposable =
            backendRepository.getSpecificPhotoFromPexels(pictureId, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { photo -> photoDetailsViewState.value = PhotoDetailsViewState.Success(photo, Event(0)) },
                    { photoDetailsViewState.value = PhotoDetailsViewState.Error(Event(R.string.accessing_database_failure), ErrorType.DATABASE_FAILURE) }
                )
        photoDetailsViewState.value = PhotoDetailsViewState.Loading
    }

    fun getSpecificPhotoFromPexels(pictureId : Int) {
        getSpecificPhotoFromPexelsDisposable =
                backendRepository.getSpecificPhotoFromPexels(pictureId, false)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { photo -> photoDetailsViewState.value = PhotoDetailsViewState.Success(photo, Event(if(connectionChecker.isNetworkAvailable()) 0 else R.string.offline_mode_warning)) },
                                { photoDetailsViewState.value = PhotoDetailsViewState.Error(Event(R.string.accessing_database_failure), ErrorType.DATABASE_FAILURE) }
                        )
        photoDetailsViewState.value = PhotoDetailsViewState.Loading
    }

    sealed class PhotosListViewState() {
        object Loading : PhotosListViewState()
        data class Success(val photos: List<Photo>, val messageAboutSuccessResId : Event<Int>): PhotosListViewState()
        data class Error(val errorMessageResId: Event<Int>, val errorType : ErrorType): PhotosListViewState()
    }

    sealed class PhotoDetailsViewState() {
        object Loading : PhotoDetailsViewState()
        data class Success(val photo: Photo, val messageAboutSuccessResId : Event<Int>): PhotoDetailsViewState()
        data class Error(val errorMessageResId: Event<Int>, val errorType : ErrorType): PhotoDetailsViewState()
    }

    override fun onCleared() {
        super.onCleared()
        getPhotosFromPexelsDisposable?.dispose()
    }
}