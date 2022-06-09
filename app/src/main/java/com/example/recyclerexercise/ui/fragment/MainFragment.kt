package com.example.recyclerexercise.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recyclerexercise.adapter.GithubUserListAdapter
import com.example.recyclerexercise.databinding.FragmentMainBinding
import com.example.recyclerexercise.viewmodel.GithubUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    companion object{
        const val TAG: String = "MainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val githubUserViewModel: GithubUserViewModel by viewModel<GithubUserViewModel>()
    private val githubUserListAdapter: GithubUserListAdapter by lazy {
        GithubUserListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()

        binding.rvUserList.adapter = githubUserListAdapter
        githubUserViewModel.loadGithubUsers()
    }

    private fun observeData(){
        githubUserViewModel.apply {
            githubUsers.observe(this@MainFragment) {
                githubUserListAdapter.submitList(it)
            }
            isListRefreshing.observe(this@MainFragment) {
                binding.srlRefreshLayout.isRefreshing = it
            }
            scrollListToTop.observe(this@MainFragment) {
                binding.rvUserList.scrollToPosition(0)
            }
        }
    }

    private fun setListener(){
        binding.apply {
            srlRefreshLayout.setOnRefreshListener {
                githubUserViewModel.refreshGithubUsers(true)
            }
        }
    }
}