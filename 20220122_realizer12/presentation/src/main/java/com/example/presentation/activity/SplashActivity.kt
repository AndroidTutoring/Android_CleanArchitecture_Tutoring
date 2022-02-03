package com.example.presentation.activity

import android.content.Intent
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.repository.UserRepository
import com.example.presentation.repository.UserRepositoryImpl
import com.example.presentation.room.FavoriteMarkDataBase
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSourceImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity:BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    private lateinit var disposable:Disposable

    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = FavoriteMarkDataBase.getInstance(this.applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl()
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase!!.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource,remoteDataSource)
    }

    override fun onResume() {
        super.onResume()

        disposable= Single.zip(
            getGitHubUserInfo().subscribeOn(Schedulers.io()).map {
            if(it.isSuccessful) {
                it.body()
            }else {
                throw Throwable()
            }
        }.retryWhen {errorObservable ->
            errorObservable.delay(3,TimeUnit.SECONDS).take(2)
        }.observeOn(AndroidSchedulers.mainThread())
            ,Single.timer(2,TimeUnit.SECONDS),
            { searchedUsers,_->
                gotoMainActivity(searchedUsers?.items)
            }).onErrorReturn {
               showToast("유저 정보를 가져올수가 없습니다.")
        }.subscribe()

    }

    //유저 정보 가져오기
    private fun getGitHubUserInfo()=userRepository.getSearchUsers(query = "realizer12",1,10)

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }

    //메인 가기
    private fun gotoMainActivity(searchUsers:ArrayList<SearchedUser>?){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra(PARAM_INIT_USER_INFO,searchUsers)
        startActivity(intent)
        finish()
    }

    companion object{
        const val PARAM_INIT_USER_INFO = "param_init_user_info"
    }

}