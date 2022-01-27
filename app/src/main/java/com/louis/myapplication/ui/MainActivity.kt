package com.louis.myapplication.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.louis.myapplication.R
import com.louis.myapplication.databinding.ActivityMainBinding

class MainActivity<T : MainViewModel> :
    AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var recycleviewUtil: RecycleviewUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initView()
        doObserve()
    }

    private fun initView() {
        mBinding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchUsers(mBinding.etSearch.text.toString())
                true
            }
            false
        }

        userListAdapter = UserListAdapter(this, arrayListOf())
        mBinding.rvUsers.adapter = userListAdapter
        mBinding.rvUsers.layoutManager = LinearLayoutManager(this)
        recycleviewUtil = RecycleviewUtil(mBinding.rvUsers)
        recycleviewUtil.apply {
            setLoadMoreListener(object : LoadMoreListener {
                override fun onLoadMore() {
                    viewModel.loading()
                    viewModel.loadMore(mBinding.etSearch.text.toString())
                }
            })
        }
    }

    private fun doObserve() {
        viewModel.apply {
            users.observe(this@MainActivity, {
                userListAdapter.setItems(it)
            })

            usersLoadMore.observe(this@MainActivity, {
                userListAdapter.addItems(it)
            })

            loading.observe(this@MainActivity, {
                mBinding.clLoading.visibility = if (it) View.VISIBLE else View.GONE
            })

            error.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            })
        }
    }
}