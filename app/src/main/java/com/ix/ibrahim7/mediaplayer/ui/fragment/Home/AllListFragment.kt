package com.ix.ibrahim7.mediaplayer.ui.fragment.Home

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ferfalk.simplesearchview.SimpleSearchView
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.adapter.SongAdapter
import com.ix.ibrahim7.mediaplayer.databinding.FragmentAboutBinding
import com.ix.ibrahim7.mediaplayer.databinding.FragmentAllListBinding
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.ui.viewModel.AllListViewModel
import com.ix.ibrahim7.mediaplayer.ui.viewModel.SongViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_all_list.*
import kotlinx.android.synthetic.main.fragment_songs.*
import timber.log.Timber

class AllListFragment : Fragment() ,SongAdapter.onClick{


    private lateinit var mBinding: FragmentAllListBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[AllListViewModel::class.java]
    }

    private val viewModel2 by lazy {
        ViewModelProvider(requireActivity())[SongViewModel::class.java]
    }

    private val song_adapter by lazy {
        SongAdapter(
            requireActivity(),
            ArrayList(),
            this
        ,0)
    }

    lateinit var array: ArrayList<SongModel>

    private val argument by lazy {
        requireArguments()
    }


    private val getType by lazy {
        argument.getInt(Constant.TYPE, 0)
    }

    private val getArray by lazy {
        argument.getParcelableArrayList<SongModel>(Constant.ARRAY)
    }


    val array_favorite =ArrayList<SongModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().toolbar.visibility = View.VISIBLE
        mBinding = FragmentAllListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().toolbar.title="All Song"


        song_adapter.SongSearchModelliveData.observe(viewLifecycleOwner, Observer {
            if (it == 0){
                empty.visibility=View.VISIBLE
                list_all.visibility=View.GONE

            }else{
                empty.visibility=View.GONE
                list_all.visibility=View.VISIBLE
            }
        })


        if (getType == 0) {
            recentItem()
            viewModel.getdate().observe(requireActivity(), Observer {recent->
                if (recent.isEmpty()){
                    empty_favorite.visibility=View.VISIBLE
                }
                array= recent as ArrayList<SongModel>
                song_adapter.data!!.clear()
                song_adapter.data!!.addAll(recent)
                song_adapter.notifyDataSetChanged()
                Log.e("eeee recent", recent.toString())
            })
        }else if (getType == 1){
            array= getArray as ArrayList<SongModel>
            song_adapter.data!!.clear()
            song_adapter.data!!.addAll(array)
            song_adapter.notifyDataSetChanged()
            Log.e("eeee array", array.toString())
        }else if (getType == 2){
            requireActivity().tabs.visibility = View.GONE
            requireActivity().toolbar.visibility = View.VISIBLE
            empty.visibility=View.GONE
            viewModel2.SongModelliveData.observe(viewLifecycleOwner, Observer {
                array = it
                song_adapter.data!!.addAll(it)
                Timber.d("${Constant.TAG} $it")
            })
            requireActivity().searchView.visibility = View.VISIBLE
            requireActivity().searchView.showSearch()

            requireActivity().searchView.setOnSearchViewListener(object :SimpleSearchView.SearchViewListener{
                override fun onSearchViewShownAnimation() {
                }

                override fun onSearchViewClosed() {
                   findNavController().navigateUp()
                }

                override fun onSearchViewClosedAnimation() {

                }

                override fun onSearchViewShown() {
                }

            })

            requireActivity().searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener{


                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        song_adapter.filter.filter(newText)
                        list_all.apply {
                            adapter = song_adapter
                            song_adapter.data!!.addAll(song_adapter.data!!)
                            song_adapter.notifyDataSetChanged()
                        }
                    }else{
                        song_adapter.data!!.clear()
                        song_adapter.data!!.addAll(array)
                        song_adapter.notifyDataSetChanged()
                    }
                    return false
                }

                override fun onQueryTextCleared(): Boolean {
                    empty.visibility=View.GONE
                    list_all.visibility=View.VISIBLE
                    return false
                }

            })
        }else{
            recentItem()
            viewModel.getFavorite().observe(viewLifecycleOwner, Observer {favorite->
                if (favorite.isEmpty()){
                    empty_favorite.visibility=View.VISIBLE
                }
                song_adapter.data!!.clear()
                array_favorite.clear()
                favorite.forEach {song->
                    array_favorite.add(
                        SongModel(song.id,song.title,song.artistName,song.composer,song.albumName,song.albumArt,song.data,song.trackNumber
                            ,song.year,song.duration,song.dateModified,song.dateModified,song.albumId,song.artistId,song.bookmark)
                    )
                }
                    Log.e("eeee fav",favorite.toString())
                    Log.e("eeee fav array_favorite",array_favorite.toString())
                array=array_favorite

                song_adapter.data!!.addAll(array_favorite)
                    song_adapter.notifyDataSetChanged()
                Log.e("eee favorite",array_favorite.toString())
            })
        }


        list_all.apply {
            adapter =song_adapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.recyclerview_layout_animation
            )
        }

        super.onViewCreated(view, savedInstanceState)
    }


    fun recentItem(){
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return true // true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
               if (getType == 0) {
                   viewModel.deleteSong(song_adapter.data!![viewHolder.adapterPosition]).also {
                       song_adapter.notifyItemRemoved(swipeDir)
                       if (song_adapter.data!!.isEmpty()){
                           empty_favorite.visibility=View.VISIBLE
                       }else{
                           empty_favorite.visibility=View.GONE
                       }
                   }
               }else{
                   viewModel.deleteFavorite(song_adapter.data!![viewHolder.adapterPosition]).also {
                       song_adapter.notifyItemRemoved(swipeDir)
                       if (song_adapter.data!!.isEmpty()){
                           empty_favorite.visibility=View.VISIBLE
                       }else{
                           empty_favorite.visibility=View.GONE
                       }
                   }
               }

            }
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        Color.parseColor("#D9519D")
                    )
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(list_all)
    }
    override fun onClickItem(position: Int, type: Int) {
        when (type) {
            1 -> {
                var arr = arrayOfNulls<SongModel>(array.size)
                arr = array.toArray(arr)

                val action = AllListFragmentDirections.actionAllListFragmentToPlayerFragment2(
                    position.toString(),
                    arr.requireNoNulls()
                )

                findNavController().navigate(action)
            }
        }
    }

}