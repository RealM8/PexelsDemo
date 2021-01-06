package com.realmatesoft.pexelsdemo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.slickypasscode.archcomponents.provideViewModel
import com.realmatesoft.pexelsdemo.PexelsDemoApplication
import com.realmatesoft.pexelsdemo.R
import com.realmatesoft.pexelsdemo.backend.presentation.BackendViewModel
import com.realmatesoft.pexelsdemo.databinding.ActivityPhotoDetailsBinding


class PhotoDetailsActivity : AppCompatActivity() {

    companion object {
        const val photoIdArgument = "photo_id"
    }

    private lateinit var photoDetailsActivityBinding: ActivityPhotoDetailsBinding

    private val backendViewModel: BackendViewModel by provideViewModel {
        BackendViewModel(PexelsDemoApplication.instance.backendRepository, PexelsDemoApplication.instance.connectionChecker)
    }

    private var photoId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoDetailsActivityBinding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(photoDetailsActivityBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        photoId = intent.getIntExtra(photoIdArgument, 0)
        if (photoId == 0) finish()
        if (savedInstanceState == null) backendViewModel.getSpecificPhotoFromLocalCache(photoId)

        setupViews()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_refresh -> {
                photoDetailsActivityBinding.swipeToRefreshPhotoDetails.isRefreshing = true
                backendViewModel.getSpecificPhotoFromPexels(photoId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun setupViews() {
        photoDetailsActivityBinding.swipeToRefreshPhotoDetails.setOnRefreshListener { backendViewModel.getSpecificPhotoFromPexels(photoId) }
    }

    private fun setupObservers() {
        backendViewModel.getPhotoDetailsViewState().observe(this, { viewState ->
            when (viewState) {
                is BackendViewModel.PhotoDetailsViewState.Loading -> {
                    if (photoDetailsActivityBinding.swipeToRefreshPhotoDetails.isRefreshing) {
                        photoDetailsActivityBinding.swipeToRefreshPhotoDetails.isRefreshing = true
                    }
                }
                is BackendViewModel.PhotoDetailsViewState.Success -> {
                    photoDetailsActivityBinding.swipeToRefreshPhotoDetails.isRefreshing = false
                    if (!viewState.messageAboutSuccessResId.hasBeenHandled) {
                        val messageResId = viewState.messageAboutSuccessResId.peekContent()
                        if (messageResId != 0) showSnackbarWithMessage(photoDetailsActivityBinding.root, messageResId)
                    }
                    photoDetailsActivityBinding.photoDetailsText.text = resources.getString(R.string.photo_details_activity_details_header, viewState.photo.photographer)
                    Glide.with(photoDetailsActivityBinding.root)
                            .load(viewState.photo.src.portrait)
                            .centerCrop()
                            .error(R.drawable.ic_baseline_broken_image_24)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(photoDetailsActivityBinding.photoIv)
                }
                is BackendViewModel.PhotoDetailsViewState.Error -> {
                    photoDetailsActivityBinding.swipeToRefreshPhotoDetails.isRefreshing = false
                    if (!viewState.errorMessageResId.hasBeenHandled) {
                        showSnackbarWithMessage(photoDetailsActivityBinding.root, viewState.errorMessageResId.peekContent())
                    }
                }
            }
        })
    }
}