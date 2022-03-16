package com.osmar.examen.ui.uploadImage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osmar.examen.databinding.ItemImagesBinding

class UploadImageAdapter(
    var images: List<String>
): RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder>(){

    class UploadImageViewHolder(
        private val context: Context,
        private val vBind: ItemImagesBinding
    ) : RecyclerView.ViewHolder(vBind.root) {

        fun bind(url : String){
                Glide
                    .with(context)
                    .load(url)
                    .into(vBind.ivImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vBind = ItemImagesBinding.inflate(inflater, parent, false)
        return UploadImageViewHolder(parent.context, vBind)
    }

    override fun onBindViewHolder(holder: UploadImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
       return images.size
    }

    fun setList(images: List<String>){
        this.images = images

        notifyDataSetChanged()
    }


}