package com.example.presentation.activity

import android.os.Bundle
import android.view.View
import com.example.presentation.adapter.RepoListRcyAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import com.example.presentation.fragment.UserFragment
import com.example.presentation.model.SearchedUser
import com.example.presentation.model.UserRepo
import com.example.presentation.repository.RepoRepository
import com.example.presentation.repository.RepoRepositoryImpl
import com.example.presentation.repository.UserRepository
import com.example.presentation.repository.UserRepositoryImpl
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.room.FavoriteMarkDataBase
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.RepoRemoteDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSourceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : BaseActivity<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }) {

    //유저 정보
    private var userInfo: SearchedUser? = null

    private lateinit var repoRcyAdapter: RepoListRcyAdapter
    private val repoRepository: RepoRepository by lazy {
        val remoteDataSource = RepoRemoteDataSourceImpl()
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
        repoRcyAdapter = RepoListRcyAdapter()
        binding.rcyUserRepoList.apply {
            adapter = repoRcyAdapter
        }
    }


    //유저의  레포지토리 리스트를 받아온다.
    private fun getUserRepoList(userName: String) {

        repoRepository.getUserRepoList(userName = userName,{userRepoList->
            if(!userRepoList.isNullOrEmpty()){
                binding.emptyView.visibility = View.GONE//데이터 가져오는 중 없앰.
                repoRcyAdapter.submitList(userRepoList)
            }
        },{t->
            showToast(t.message.toString())
        })
    }

}