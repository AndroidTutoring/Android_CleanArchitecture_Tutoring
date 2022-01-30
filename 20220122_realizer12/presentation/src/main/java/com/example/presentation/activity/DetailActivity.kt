package com.example.presentation.activity

import android.os.Bundle
import android.view.View
import com.example.presentation.adapter.RepoListRcyAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import com.example.presentation.fragment.UserFragment
import com.example.presentation.model.SearchedUser
import com.example.presentation.model.UserRepo
import com.example.presentation.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : BaseActivity<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }) {

    //유저 정보
    private var userInfo: SearchedUser? = null

    private lateinit var repoRcyAdapter: RepoListRcyAdapter

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
        RetrofitHelper.apiServices.getUserRepoInfo(userName)
            .enqueue(object : Callback<ArrayList<UserRepo>> {
                override fun onResponse(
                    call: Call<ArrayList<UserRepo>>,
                    response: Response<ArrayList<UserRepo>>
                ) {
                    response.body().apply {
                        if(!this.isNullOrEmpty()){
                           binding.emptyView.visibility = View.GONE//데이터 가져오는 중 없앰.
                           repoRcyAdapter.submitList(this)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserRepo>>, t: Throwable) {
                    showToast(t.message.toString())
                }
            })
    }


}