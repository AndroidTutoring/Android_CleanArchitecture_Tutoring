package com.example.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.activity.DetailActivity
import com.example.presentation.activity.SplashActivity
import com.example.presentation.adapter.UserListRcyAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentUserBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.repository.UserRepository
import com.example.presentation.repository.UserRepositoryImpl
import com.example.presentation.room.FavoriteMarkDataBase
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSourceImpl
import com.example.presentation.util.Util.hideKeyboard
import com.example.presentation.util.Util.search
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

//유저 프래그먼트
class UserFragment:BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {
   private var page:Int = 1//페이지목록
   private var perPage:Int =10//페이지당 요청하는 데이터 수
   private var totalDataCount:Int? = 0//query 에 대한 전체 검색어 수

   private var searchedUsersList:ArrayList<SearchedUser>? = ArrayList()
   private lateinit var userListRcyAdapter: UserListRcyAdapter

    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = FavoriteMarkDataBase.getInstance(requireContext().applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl()
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase?.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource,remoteDataSource)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        listenerEvent()
    }

    //초기세팅
    private fun initSet(){

        binding.emptyView.visibility = View.VISIBLE

        userListRcyAdapter = UserListRcyAdapter()
        binding.rcyUserList.apply {
            adapter = userListRcyAdapter
        }

        //splash 에서 받아온  유저 리스트 먼저 뿌려줌.
        showSplashUserList()
    }

    override fun onResume() {
        super.onResume()
        val newList = userListRcyAdapter.currentList.toMutableList()

        newList.map { user->
            Timber.v("adasd ->"+user.login)
            userRepository.getFavoriteUsers()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({favoriteUser ->
                    if(!favoriteUser.any { it.id  == user.id}){
                        user.isMyFavorite = false
                    }
                    userListRcyAdapter.submitList(newList.toMutableList())
                    userListRcyAdapter.notifyDataSetChanged()//전체 업데이트가 안되어서 이렇게 notify넣어줌.
                }, {
                    showToast("로컬 디비 가져오는 중 문제가 생김")
                })?.addTo(compositeDisposable)
        }
    }




    //splash 에서 받아온  유저 리스트
    private fun showSplashUserList(){
        searchedUsersList =
            requireActivity().intent.getParcelableArrayListExtra<SearchedUser>(SplashActivity.PARAM_INIT_USER_INFO) as ArrayList<SearchedUser>
        if(!searchedUsersList.isNullOrEmpty()){

            searchedUsersList?.map {searchUser->
                userRepository.getFavoriteUsers()?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({favoriteUser ->
                        if(favoriteUser.any {eachUser-> eachUser.id  == searchUser.id}){
                            searchUser.isMyFavorite = true
                        }
                        userListRcyAdapter.submitList(searchedUsersList!!.toMutableList())
                        binding.editSearchUser.setText(searchedUsersList!![0].login.toString())
                        binding.emptyView.visibility = View.GONE
                    }, {
                        showToast("로컬 디비 가져오는 중 문제가 생김")
                    })?.addTo(compositeDisposable)
            }
        }
    }


    //리스너 기능 모음.
    private fun listenerEvent(){
        //유저 검색 클릭시
        binding.btnSearch.setOnClickListener {
            binding.emptyView.visibility = View.VISIBLE
            page = 1
            searchUsers()
            hideKeyboard()//키보드 내림
        }

        //imeoption action search 버튼 누르면,   유저 검색 버튼 눌리게함.
        binding.editSearchUser.search(binding.btnSearch)

        //리사이클러뷰
        binding.rcyUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    page = ++page
                    searchUsers()
                }
            }
        })

        //아이템 클릭시 처리
        userListRcyAdapter.setItemClickListener(object :UserListRcyAdapter.ItemCliCkListener{
            override fun onItemClickListener(searchedUser: SearchedUser) {
                gotoDetailActivity(searchedUser)
            }
        })

        //즐겨 찾기 클릭시 처리
            userListRcyAdapter.setFavoriteMarkClickListener(object :UserListRcyAdapter.FavoriteClickListener{
                override fun onFavoriteMarkListener(searchedUser: SearchedUser,position:Int) {

                if(searchedUser.isMyFavorite){
                    showToast("즐겨찾기 취소")
                    searchedUser.isMyFavorite = false
                    userRepository.deleteFavoriteUser(searchedUser)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.doOnComplete {

                            val newList = userListRcyAdapter.currentList.toMutableList()

                            newList.map { user->
                                if(searchedUser.id == user.id){
                                    user.isMyFavorite = searchedUser.isMyFavorite
                                }
                            }

                            userListRcyAdapter.submitList(newList.toMutableList())
                            userListRcyAdapter.notifyItemChanged(position)

                        }?.subscribe()
                }else{
                    showToast("즐겨찾기 추가")
                    searchedUser.isMyFavorite = true
                    userRepository.addFavoriteUser(searchedUser)?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.doOnComplete {

                            val newList = userListRcyAdapter.currentList.toMutableList()

                            newList.map { user->
                                if(searchedUser.id == user.id){
                                    user.isMyFavorite = searchedUser.isMyFavorite
                                }
                            }

                            userListRcyAdapter.submitList(newList.toMutableList())
                            userListRcyAdapter.notifyItemChanged(position)

                        }?.subscribe()
                }
            }
        })

    }


    //상세화면으로 가기
    private fun gotoDetailActivity(searchedUser: SearchedUser){
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(PARAM_USER_INFO,searchedUser)
        startActivity(intent)
    }


    //유저 검색
    private fun searchUsers() {


        //local , remote  zip으로  동시에 다 처리되면  뿌려지게 수정
        Single.zip(userRepository.getSearchUsers(binding.editSearchUser.text.toString(), page, perPage)
            .subscribeOn(Schedulers.io())
            .retry()
            .observeOn(AndroidSchedulers.mainThread()),
            userRepository.getFavoriteUsers()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread()),{
                    remote,local->
                totalDataCount = remote.body()?.total_count

                if(page ==1){
                    searchedUsersList = ArrayList()
                }

                remote.body()?.items.let { searUSerList->
                    if (!searUSerList.isNullOrEmpty()) {
                      searchedUsersList?.addAll(searUSerList)
                    }
                    searchedUsersList?.map { searchedUser ->
                        if(local.any{it.id == searchedUser.id}){
                            searchedUser.isMyFavorite = true
                        }
                    }
                }
                userListRcyAdapter.submitList(searchedUsersList?.toMutableList())//recyclerview 업데이트
                binding.emptyView.visibility = View.GONE
            }).onErrorReturn {
                  showToast("유저 검색 중 문제가 생김")
            }.subscribe()
    }

    companion object{
      const val PARAM_USER_INFO = "param_user_info"


    }

}