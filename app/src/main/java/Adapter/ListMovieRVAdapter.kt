package Adapter

import Interface.CardListener
import Model.Movie
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R
import com.example.movielist.databinding.MovieCardBinding

class ListMovieRVAdapter(val listMovie: ArrayList<Movie>, val cardListener: CardListener):
    RecyclerView.Adapter<ListMovieRVAdapter.viewHolder>() {

    class viewHolder(itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView) {
        val binding = MovieCardBinding.bind(itemView)

        fun setData(data:Movie){
            binding.ClassNameText.text=data.classname

            if(!data.imageUri!!.isEmpty()){
                binding.ClassProfileCard.setImageURI(Uri.parse(data.imageUri))
            }
            itemView.setOnClickListener{
                cardListener1.onCardClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_card, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listMovie[position])
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }
}