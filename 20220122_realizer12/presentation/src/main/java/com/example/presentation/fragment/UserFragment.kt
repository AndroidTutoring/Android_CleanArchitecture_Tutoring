package com.example.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.presentation.adapter.UserListRcyAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentUserBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.model.SearchedUsers
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.util.Util
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
        clickEvent()
    }

    //초기세팅
    private fun initSet(){
        userListRcyAdapter = UserListRcyAdapter()
        binding.rcyUserList.apply {
            adapter = userListRcyAdapter
        }

    }

    //클릭 이벤트 모음.
    private fun clickEvent(){

        //유저 검색 클릭시
        binding.btnSearch.setOnClickListener {
            searchUsers()
        }

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
                    }
                    searchedUsersList = response.body()?.items//검색한 데이터 모두 넣어줌.
                    userListRcyAdapter.submitList(searchedUsersList)//recyclerview 업데이트

                }else{//응답 실패
                    //통신 재시도
                    Util.retryCall(call = call,callback = this)
                }
            }
            override fun onFailure(call: Call<SearchedUsers>, t: Throwable) {
                //통신 재시도
                Util.retryCall(call = call,callback = this)
            }
        })
    }



}