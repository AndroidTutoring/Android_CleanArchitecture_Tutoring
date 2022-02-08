package com.example.presentation.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.adapter.MainViewPagerAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.repository.UserRepository
import com.example.presentation.repository.UserRepositoryImpl
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.room.LocalDataBase
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSourceImpl
import com.example.presentation.viewmodel.MainViewModel
import com.example.presentation.viewmodel.factory.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val behaviorSubject = BehaviorSubject.createDefault(0L)

    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = LocalDataBase.getInstance(applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl(RetrofitHelper)
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(userRepository)).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSet()
        backPressCheck()
    }

    override fun onBackPressed() {
        //누른 시점 시간 넘겨줌.
        behaviorSubject.onNext(System.currentTimeMillis())
    }

    //뒤로가기 프로세스 체크처리
    private fun backPressCheck() {
        //항상 누른거 이전걸로 체크해야되니까 buffer count 2 주고 skip 1로줌.
        behaviorSubject.buffer(2, 1)
            .map { it[0] to it[1] }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.second - it.first < 2000L) {//첫번째 누른것과 두번째 누른 값의 차가 2초이내이면 뒤로가기 처리
                    super.onBackPressed()
                } else {
                    showToast("앱을 종료 하려면 한번 더 눌러주세요.")
                }
            }.addTo(compositeDisposable)
    }

    //초기 뷰 세팅
    private fun initSet() {



        //메인 뷰페이져  FragmentStateAdapter 연결
        binding.vpMain.apply {
            this.adapter = MainViewPagerAdapter(this@MainActivity)
        }

        //tablayout , 뷰페이저  연결  및 각 탭 구성
        TabLayoutMediator(binding.tabMain, binding.vpMain) { tab, position ->
            when (position) {
                0 -> tab.text = "유저"
                1 -> tab.text = "즐겨찾기"
            }
        }.attach()

    }
}