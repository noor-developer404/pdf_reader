package com.android.pdfreader.other

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.pdfreader.PDFview
import com.android.pdfreader.R

class adapter: RecyclerView.Adapter<adapter.holder> {

    lateinit var list:ArrayList<modal>
    lateinit var context:Context

    constructor(list: ArrayList<modal>, context: Context) : super() {
        this.list = list
        this.context = context
    }


    class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name:TextView = itemView.findViewById(R.id.rv_item_name)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false)
        return holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
      holder.name.text= list.get(position).name

        holder.itemView.setOnClickListener{
            var intent = Intent(context,PDFview::class.java)
            intent.putExtra("path",list.get(position).path)
            context.startActivity(intent)
        }
    }
}