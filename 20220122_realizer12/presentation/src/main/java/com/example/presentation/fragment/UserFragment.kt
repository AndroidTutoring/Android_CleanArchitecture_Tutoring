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
import com.example.presentation.model.SearchedUsers
import com.example.presentation.repository.UserRepository
import com.example.presentation.repository.UserRepositoryImpl
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.room.FavoriteMarkDataBase
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSourceImpl
import com.example.presentation.util.Util
import com.example.presentation.util.Util.hideKeyboard
import com.example.presentation.util.Util.search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            if (getFavoriteUserList()?.any { favoriteUser -> favoriteUser.id == user.id} == false) {
                user.isMyFavorite = false
            }
        }
        userListRcyAdapter.submitList(newList.toMutableList())
        userListRcyAdapter.notifyDataSetChanged()//전체 업데이트가 안되어서 이렇게 notify넣어줌.
    }

    private fun getFavoriteUserList() = userRepository.getFavoriteUsers()


    //splash 에서 받아온  유저 리스트
    private fun showSplashUserList(){
        searchedUsersList =
            requireActivity().intent.getParcelableArrayListExtra<SearchedUser>(SplashActivity.PARAM_INIT_USER_INFO) as ArrayList<SearchedUser>
        if(!searchedUsersList.isNullOrEmpty()){

            searchedUsersList?.map {searchUser->
                if (getFavoriteUserList()?.any { favoriteUser -> favoriteUser.id == searchUser.id } == true) {
                    searchUser.isMyFavorite = true
                }
            }

            userListRcyAdapter.submitList(searchedUsersList!!.toMutableList())
            binding.editSearchUser.setText(searchedUsersList!![0].login.toString())
            binding.emptyView.visibility = View.GONE
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
                }else{
                    showToast("즐겨찾기 추가")
                    searchedUser.isMyFavorite = true
                    userRepository.addFavoriteUser(searchedUser)
                }

                val newList = userListRcyAdapter.currentList.toMutableList()

                newList.map { user->
                    if(searchedUser.id == user.id){
                        user.isMyFavorite = searchedUser.isMyFavorite
                    }
                }

                userListRcyAdapter.submitList(newList.toMutableList())
                userListRcyAdapter.notifyItemChanged(position)
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
        RetrofitHelper.apiServices.searchUsers(
            query = binding.editSearchUser.text.toString(),
            page = page,
            perPage = perPage
        ).enqueue(object : Callback<SearchedUsers> {
            override fun onResponse(call: Call<SearchedUsers>, response: Response<SearchedUsers>) {
                if(response.isSuccessful){//응답 성공
                    totalDataCount = response.body()?.total_count //전체 데이터 숫자 넣어줌.

                    if(page==1){//첫번째 페이지라면 리스트를 다시 clear 해준다.
                        searchedUsersList = ArrayList()
                        //userListRcyAdapter.submitList(null)//새로 그려주기 위해서 submitlist null보내서 초기화
                    }
                    response.body()?.items?.let {
                       if(!it.isNullOrEmpty()){//검색한  결과가 있는 경우
                           searchedUsersList?.addAll(it)

                           searchedUsersList?.map {searchUser->
                               if (getFavoriteUserList()?.any { favoriteUser -> favoriteUser.id == searchUser.id } == true) {
                                   searchUser.isMyFavorite = true
                               }
                           }

                           userListRcyAdapter.submitList(searchedUsersList?.toMutableList())//recyclerview 업데이트
                           binding.emptyView.visibility = View.GONE
                       }
                    }//검색한 데이터 모두 넣어줌.

                }
            }
            override fun onFailure(call: Call<SearchedUsers>, t: Throwable) {
                //통신 재시도
                Util.retryCall(call = call,callback = this)
            }
        })
    }

    companion object{
      const val PARAM_USER_INFO = "param_user_info"


    }

}