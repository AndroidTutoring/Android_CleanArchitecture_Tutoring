package com.example.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.activity.DetailActivity
import com.example.presentation.activity.MainActivity
import com.example.presentation.adapter.UserListRcyAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentUserBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.model.SearchedUsers
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.util.Util
import com.example.presentation.util.Util.hideKeyboard
import com.example.presentation.util.Util.search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//유저 프래그먼트
class UserFragment:BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {
   private var page:Int = 1//페이지목록
   private var perPage:Int =10//페이지당 요청하는 데이터 수
   private var totalDataCount:Int? = 0//query 에 대한 전체 검색어 수

   private var searchedUsersList:ArrayList<SearchedUser>? = ArrayList()
   private lateinit var userListRcyAdapter: UserListRcyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        listenerEvent()
    }

    //초기세팅
    private fun initSet(){
        userListRcyAdapter = UserListRcyAdapter()
        binding.rcyUserList.apply {
            adapter = userListRcyAdapter
        }

    }



    //리스너 기능 모음.
    private fun listenerEvent(){
        //유저 검색 클릭시
        binding.btnSearch.setOnClickListener {
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
                    val items = response.body()?.items

                    if(page==1){//첫번째 페이지라면 리스트를 다시 clear 해준다.
                        searchedUsersList = ArrayList()
                    }

                    response.body()?.items?.let {
                        searchedUsersList?.addAll(it)
                        userListRcyAdapter.submitList(searchedUsersList?.toMutableList())//recyclerview 업데이트
                    }//검색한 데이터 모두 넣어줌.


                }else{//응답 실패
                    //통신 재시도
                    //Util.retryCall(call = call,callback = this)
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