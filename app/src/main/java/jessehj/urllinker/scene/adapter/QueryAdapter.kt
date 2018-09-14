package jessehj.urllinker.scene.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jessehj.urllinker.R
import jessehj.urllinker.model.Query
import kotlinx.android.synthetic.main.list_item_query.view.*

class QueryAdapter : RecyclerView.Adapter<QueryAdapter.QueryItemViewHolder>() {

    var queryList = mutableListOf(Query("", ""))
    lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onQueryInputted(position: Int, query: Query)
        fun add(position: Int)
        fun remove(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryItemViewHolder {

        return QueryItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_query, parent, false))
    }

    override fun getItemCount(): Int {
        return queryList.size
    }

    override fun onBindViewHolder(holder: QueryItemViewHolder, position: Int) {

        holder.itemView.apply {

            queryList[position].let {
                query_key_edit_text.text = Editable.Factory.getInstance().newEditable(it.key)
                query_value_edit_text.text = Editable.Factory.getInstance().newEditable(it.value)

                if (position + 1 == queryList.size) {
                    add_image_button.visibility = View.VISIBLE
                    remove_image_button.visibility = View.GONE
                } else {
                    add_image_button.visibility = View.GONE
                    remove_image_button.visibility = View.VISIBLE
                }
            }
        }
    }

    inner class QueryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            if (::itemClickListener.isInitialized) {
                view.query_key_edit_text.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        val query = Query(p0.toString(), view.query_value_edit_text.text.toString())
                        itemClickListener.onQueryInputted(adapterPosition, query)
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }
                })

                view.query_value_edit_text.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        val query = Query(view.query_key_edit_text.text.toString(), p0.toString())
                        itemClickListener.onQueryInputted(adapterPosition, query)
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }
                })

                view.add_image_button.setOnClickListener {
                    itemClickListener.add(adapterPosition)
                }

                view.remove_image_button.setOnClickListener {
                    itemClickListener.remove(adapterPosition)
                }

            }
        }
    }
}