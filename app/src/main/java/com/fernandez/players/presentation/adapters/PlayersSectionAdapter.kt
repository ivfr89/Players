package com.fernandez.players.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.fernandez.players.domain.models.PlayerList
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import android.widget.TextView
import com.fernandez.players.R
import com.fernandez.players.core.extensions.loadImage
import com.fernandez.players.domain.models.Player


class PlayersSectionAdapter(val section: PlayerList) : StatelessSection(
    SectionParameters.builder()
        .itemResourceId(R.layout.item_profile)
        .headerResourceId(R.layout.item_section_profile)
        .build()
) {

    override fun getContentItemsTotal(): Int {
        return section.players.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        // return a custom instance of ViewHolder for the items of this section
        return PlayerViewHolder(view)
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return SectionViewHolder(view!!)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as? PlayerViewHolder

        itemHolder?.onBind(section.players[position])
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val sectionHolder = holder as? SectionViewHolder

        sectionHolder?.onBind(section)
    }

    private inner class SectionViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val txtTitle: TextView
        private val txtCount: TextView

        init {

            txtTitle = view.findViewById(R.id.txtSectionTitle)
            txtCount = view.findViewById(R.id.txtSectionTotal)
        }

        fun onBind(item: PlayerList)
        {
            txtTitle.text = item.title
            txtCount.text = itemView.context.getString(R.string.n_items,item.players.size)
        }
    }

    private inner class PlayerViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val imgProfile: ImageView
        private val txtProfileName: TextView
        private val txtProfileSubname: TextView
        private val txtProfileDate: TextView

        init {

            imgProfile = view.findViewById(R.id.imgProfile)
            txtProfileName = view.findViewById(R.id.txtProfileName)
            txtProfileSubname = view.findViewById(R.id.txtProfileSubname)
            txtProfileDate = view.findViewById(R.id.txtProfileDate)
        }

        fun onBind(item: Player)
        {
            imgProfile.loadImage(item.image)
            txtProfileName.text = item.name
            txtProfileSubname.text = item.surname
            txtProfileDate.text = item.date
        }
    }


}// call constructor with layout resources for this Section header and items