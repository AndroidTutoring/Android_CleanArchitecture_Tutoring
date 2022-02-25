package com.example.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.data.repository.RepoRepository
import com.example.presentation.R
import com.example.presentation.adapter.RepoListRvAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import com.example.presentation.fragment.UserFragment
import com.example.presentation.model.SearchedUserPresentationModel
import com.example.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {


    //유저 정보
    private var userInfo: SearchedUserPresentationModel? = null

    private lateinit var repoRvAdapter: RepoListRvAdapter


    //정보화면 뷰모델
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSet()
        getDataFromViewModel()
        getUserRepoList(userInfo?.login.toString())//해당 유저의  레포 리스트 가져오기
    }

    //초기 세팅
    private fun initSet() {

        binding.thisActivity = this
        binding.lifecycleOwner = this
        binding.vm = detailViewModel

        //유저 정보 가져옴.
        userInfo = intent.getParcelableExtra(UserFragment.PARAM_USER_INFO)

        //리시이클러뷰 세팅
        repoRvAdapter = RepoListRvAdapter()
        binding.rvUserRepoList.apply {
            adapter = repoRvAdapter
        }
    }

    //뷰모델로부터  데이터 받아옴
    private fun getDataFromViewModel() {

        //error 관련 처리
        detailViewModel.error.observe(this, Observer {
            showToast(it.message.toString())
        })

    }


    //유저의  레포지토리 리스트를 받아온다.
    private fun getUserRepoList(userName: String) {
        detailViewModel.getRepoDetails(userName = userName)
    }

}