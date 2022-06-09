package com.example.recyclerexercise.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
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
    private val userDetailFragmentArgs: UserDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.userData = userDetailFragmentArgs.userData
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

            binding.tvType.text = it.type
            binding.tvUserId.text = getString(R.string.user_detail_id).format(it.id)
            binding.tvNodeId.text = getString(R.string.user_detail_node_id).format(it.node_id)
            binding.tvUrl.text = it.url
            binding.tvHtmlUrl.text = it.html_url
            binding.tvFollowerUrl.text = it.followers_url
            binding.tvFollowingUrl.text = it.following_url
            binding.tvGistUrl.text = it.gists_url
            binding.tvStarredUrl.text = it.starred_url
            binding.tvSubscriptionUrl.text = it.subscriptions_url
            binding.tvOrganizationUrl.text = it.organizations_url
            binding.tvRepoUrl.text = it.repos_url
            binding.tvEventsUrl.text = it.events_url
            binding.tvReceivedEventsUrl.text = it.received_events_url
        }

    }
}