package com.howlstagram.mvvmhowlstagram.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.mvvmhowlstagram.R
import com.howlstagram.mvvmhowlstagram.databinding.FragmentDetailViewBinding
import com.howlstagram.mvvmhowlstagram.databinding.ItemDetailBinding
import com.howlstagram.mvvmhowlstagram.model.ContentModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentDetailViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail_view, container, false)

        binding.detailviewRecyclerveiw.adapter = DetailViewRecyclerviewAdpter()
        binding.detailviewRecyclerveiw.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    inner class DetailViewHolder(var binding : ItemDetailBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DetailViewRecyclerviewAdpter() : RecyclerView.Adapter<DetailViewHolder>() {

        var firestore = FirebaseFirestore.getInstance()
        var contentModels = arrayListOf<ContentModel>()
        init {

            firestore.collection("images").addSnapshotListener { value, error ->
                contentModels.clear()
                for (item in value!!.documents) {
                    var contentModel = item.toObject(ContentModel::class.java)
                    contentModels.add(contentModel!!)
                }
                notifyDataSetChanged()
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
            var view = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DetailViewHolder(view)
        }

        override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
            var contentModel = contentModels[position]

            holder.binding.profileTextview.text = contentModel.userId
            holder.binding.explainTextview.text = contentModel.explain
            holder.binding.likeTextview.text = "like " + contentModel.favoriteCount
            Glide.with(holder.itemView.context).load(contentModel.imageUrl).into(holder.binding.contentImageview)

        }

        override fun getItemCount(): Int {
            return contentModels.size
        }

    }
}