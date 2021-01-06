package com.realmatesoft.pexelsdemo.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.realmatesoft.pexelsdemo.PexelsDemoApplication
import com.realmatesoft.pexelsdemo.ui.PhotoDetailsActivity
import com.realmatesoft.pexelsdemo.R
import com.realmatesoft.pexelsdemo.backend.data.Photo
import com.realmatesoft.pexelsdemo.databinding.PhotosListItemBinding


class PhotosRVAdapter : ListAdapter<Photo, PhotosRVAdapter.PhotoViewHolder>(PhotoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotosListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        // set the data in items
        holder.bindTo(getItem(position))
    }

    class PhotoItemDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
    }

    inner class PhotoViewHolder(private val binding: PhotosListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(photoItem: Photo){
            with (photoItem) {
                binding.photographerNameText.text = photographer
                Glide.with(binding.root)
                        .load(src.portrait)
                        .centerCrop()
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.photoIv)
                binding.root.setOnClickListener {
                    with(Intent(PexelsDemoApplication.instance.baseContext, PhotoDetailsActivity::class.java)) {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra(PhotoDetailsActivity.photoIdArgument, id)
                        startActivity(PexelsDemoApplication.instance.baseContext, this, null)
                    }
                }
            }
        }
    }

}