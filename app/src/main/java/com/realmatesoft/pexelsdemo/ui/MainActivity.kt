package com.realmatesoft.pexelsdemo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.slickypasscode.archcomponents.provideViewModel
import com.realmatesoft.pexelsdemo.PexelsDemoApplication
import com.realmatesoft.pexelsdemo.R
import com.realmatesoft.pexelsdemo.backend.presentation.BackendViewModel
import com.realmatesoft.pexelsdemo.databinding.ActivityMainBinding
import com.realmatesoft.pexelsdemo.ui.adapters.PhotosRVAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    private val backendViewModel: BackendViewModel by provideViewModel {
        BackendViewModel(PexelsDemoApplication.instance.dependencies.backendRepository, PexelsDemoApplication.instance.dependencies.connectionChecker)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        setupViews()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                mainActivityBinding.swipeToRefreshPhotos.isRefreshing = true
                backendViewModel.getPhotosFromPexels()
                return true
            }
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {
        mainActivityBinding.swipeToRefreshPhotos.setOnRefreshListener { backendViewModel.getPhotosFromPexels() }
        val gridLayoutManager = GridLayoutManager(this, 2)
        mainActivityBinding.photosRv.layoutManager = gridLayoutManager
        mainActivityBinding.photosRv.adapter = PhotosRVAdapter()

    }

    private fun setupObservers() {
        backendViewModel.getPhotosListViewState().observe(this, { viewState ->
            when (viewState) {
                is BackendViewModel.PhotosListViewState.Loading -> {
                    mainActivityBinding.swipeToRefreshPhotos.isRefreshing = true
                }
                is BackendViewModel.PhotosListViewState.Success -> {
                    mainActivityBinding.swipeToRefreshPhotos.isRefreshing = false
                    (mainActivityBinding.photosRv.adapter as PhotosRVAdapter).submitList(viewState.photos)
                    if (!viewState.messageAboutSuccessResId.hasBeenHandled) {
                        val messageResId = viewState.messageAboutSuccessResId.peekContent()
                        if (messageResId != 0) showSnackbarWithMessage(mainActivityBinding.root, messageResId)
                    }
                }
                is BackendViewModel.PhotosListViewState.Error -> {
                    mainActivityBinding.swipeToRefreshPhotos.isRefreshing = false
                    if (!viewState.errorMessageResId.hasBeenHandled) {
                        showSnackbarWithMessage(mainActivityBinding.root, viewState.errorMessageResId.peekContent())
                    }
                }
            }
        })
    }

}