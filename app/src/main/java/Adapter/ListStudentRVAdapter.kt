package Adapter

//
import Interface.CardListener
import Model.Student
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R
import com.example.movielist.databinding.StudentCardBinding

class ListStudentRVAdapter(val listStudent: ArrayList<Student>, val cardListener: CardListener):
    RecyclerView.Adapter<ListStudentRVAdapter.viewHolder>() {

    class viewHolder(itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView) {
        val binding = StudentCardBinding.bind(itemView)

        fun setData(data:Student){
            binding.StudentNameText.text=data.name

            if(!data.imageUri!!.isEmpty()){
                binding.StudentProfileCard.setImageURI(Uri.parse(data.imageUri))
            }
            itemView.setOnClickListener{
                cardListener1.onCardClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.student_card, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listStudent[position])
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }
}