package com.github.yeetologist.palindromechecker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.yeetologist.palindromechecker.data.DataItem
import com.github.yeetologist.palindromechecker.databinding.ActivityThirdBinding
import com.github.yeetologist.palindromechecker.util.PreferenceManager

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var thirdViewModel: ThirdViewModel

    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserListAdapter()

        thirdViewModel = ViewModelProvider(this)[ThirdViewModel::class.java]

        preferenceManager = PreferenceManager(this)

        thirdViewModel.listUsers.observe(this) {
            setListUsers(it.peekContent())
        }

        thirdViewModel.getUser()

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupSwipeToRefresh()
    }

    private fun setupSwipeToRefresh() {
        // on below line we are notifying adapter
        // that data has been updated.
//        binding.rvUser.notifyDataSetChanged()
        adapter.notifyDataSetChanged()

        // on below line we are adding refresh listener
        // for our swipe to refresh method.
        binding.swipeRefresh.setOnRefreshListener {

            thirdViewModel.getUser()

            // on below line we are setting is refreshing to false.
            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun setListUsers(items: List<DataItem>?) {
        adapter.submitList(items)
        binding.rvUser.adapter = adapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(object : UserListAdapter.OnItemClickListener {
            override fun onItemClick(result: DataItem) {
                preferenceManager.setSelected("${result.firstName} ${result.lastName}")
            }
        })
    }


}