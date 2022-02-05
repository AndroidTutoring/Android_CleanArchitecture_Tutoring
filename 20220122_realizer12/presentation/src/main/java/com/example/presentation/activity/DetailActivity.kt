package com.example.presentation.activity

import android.os.Bundle
import android.view.View
import com.example.presentation.adapter.RepoListRvAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import com.example.presentation.fragment.UserFragment
import com.example.presentation.model.SearchedUser
import com.example.presentation.repository.RepoRepository
import com.example.presentation.repository.RepoRepositoryImpl
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.source.remote.RepoRemoteDataSourceImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailActivity : BaseActivity<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }) {

    //유저 정보
    private var userInfo: SearchedUser? = null

    private lateinit var repoRvAdapter: RepoListRvAdapter
    private val repoRepository: RepoRepository by lazy {
        val remoteDataSource = RepoRemoteDataSourceImpl(RetrofitHelper)
        RepoRepositoryImpl(remoteDataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSet()
        getUserRepoList(userInfo?.login.toString())//해당 유저의  레포 리스트 가져오기
    }

    //초기 세팅
    private fun initSet() {
        //유저 정보 가져옴.
        userInfo = intent.getParcelableExtra(UserFragment.PARAM_USER_INFO)

        //리시이클러뷰 세팅
        repoRvAdapter = RepoListRvAdapter()
        binding.rcyUserRepoList.apply {
            adapter = repoRvAdapter
        }
    }


    //유저의  레포지토리 리스트를 받아온다.
    private fun getUserRepoList(userName: String) {

        repoRepository.getUserRepoList(userName = userName)
            .subscribeOn(Schedulers.io())
            .filter { !it.isNullOrEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userRepoList ->
                binding.emptyView.visibility = View.GONE//데이터 가져오는 중 없앰.
                repoRvAdapter.submitList(userRepoList)
            }, { t ->
                showToast(t.message.toString())
            })
    }

}