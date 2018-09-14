package jessehj.urllinker.scene.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jessehj.urllinker.R
import jessehj.urllinker.model.Link
import kotlinx.android.synthetic.main.list_item_link.view.*

class LinkAdapter : RecyclerView.Adapter<LinkAdapter.LinkItemViewHolder>() {

    interface ItemClickListener {
        fun onLinkItemClick(position: Int)
        fun onLinkItemLongClick(position: Int)
    }

    lateinit var viewModels: MutableList<Link>
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkItemViewHolder {
        return LinkItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_link, parent, false))
    }

    override fun getItemCount(): Int {
        return if (::viewModels.isInitialized) viewModels.count() else 0
    }

    override fun onBindViewHolder(holder: LinkItemViewHolder, position: Int) {
        if (::viewModels.isInitialized) {
            val viewModel = viewModels[position]

            holder.itemView.apply {
                title_text_view.text = viewModel.title
                url_text_view.text = viewModel.url
            }
        }
    }

    inner class LinkItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            if (::itemClickListener.isInitialized) {
                view.setOnClickListener {
                    itemClickListener.onLinkItemClick(adapterPosition)
                }
                view.setOnLongClickListener {
                    itemClickListener.onLinkItemLongClick(adapterPosition)
                    true
                }
            }
        }
    }
}