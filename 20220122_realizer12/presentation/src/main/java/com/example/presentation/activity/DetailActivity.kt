package com.example.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.RepoRepository
import com.example.data.repository.RepoRepositoryImpl
import com.example.data.retrofit.RetrofitHelper
import com.example.data.source.remote.RepoRemoteDataSourceImpl
import com.example.presentation.R
import com.example.presentation.adapter.RepoListRvAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import com.example.presentation.fragment.UserFragment
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.viewmodel.DetailViewModel
import com.example.presentation.viewmodel.factory.DetailViewModelFactory
import io.reactivex.rxjava3.kotlin.addTo

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    //유저 정보
    private var userInfo: PresentationSearchedUser? = null

    private lateinit var repoRvAdapter: RepoListRvAdapter
    private val repoRepository: RepoRepository by lazy {
        val remoteDataSource = RepoRemoteDataSourceImpl(RetrofitHelper)
        RepoRepositoryImpl(remoteDataSource)
    }

    //정보화면 뷰모델
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(
            this,
            DetailViewModelFactory(repoRepository)
        ).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSet()
        getDataFromViewModel()
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

    //뷰모델로부터  데이터 받아옴
    private fun getDataFromViewModel() {
        //검색  유저 리스트 업데이트
        detailViewModel.repoDetailPublishSubject.subscribe({
            binding.emptyView.visibility = View.GONE//데이터 가져오는 중 없앰.
            repoRvAdapter.submitList(it.toMutableList())
        }, {
            showToast(it.message.toString())
        }).addTo(compositeDisposable)
    }


    //유저의  레포지토리 리스트를 받아온다.
    private fun getUserRepoList(userName: String) {
        detailViewModel.getRepoDetails(userName = userName)
    }

}