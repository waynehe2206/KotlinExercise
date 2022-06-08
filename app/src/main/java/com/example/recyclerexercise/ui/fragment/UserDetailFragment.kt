package com.example.recyclerexercise.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.recyclerexercise.R
import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.databinding.FragmentUserDetailBinding

private const val DATA_USER_DETAIL = "userData"

/**
 * A simple [Fragment] subclass.
 * Use the [UserDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var userData: GithubUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(DATA_USER_DETAIL)?.let {
            if (it is GithubUser) userData = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userData?.let {
            binding.tvUserName.text = it.login
            binding.ivUserThumbnail.apply {
                Glide.with(view)
                    .load(it.avatar_url)
                    .transform(CenterCrop())
                    .into(this)
            }
        }

    }
}