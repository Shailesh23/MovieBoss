package com.themovieguide.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.themovieguide.R

/**
 * A simple [Fragment] subclass.
 * Use the [TvFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TvFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        childFragmentManager.apply {
            beginTransaction().apply {
                add(R.id.movie_items_container, ListItemFragment.newInstance("airing_today", "Airing Today", "tv"), null)
                add(R.id.movie_items_container, ListItemFragment.newInstance("popular", "Popular", "tv"), null)
                add(R.id.movie_items_container, ListItemFragment.newInstance("on_the_air", "On Air", "tv"), null)
                add(R.id.movie_items_container, ListItemFragment.newInstance("top_rated", "Top Movies", "tv"), null)
                commitAllowingStateLoss()
            }
        }
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }
}