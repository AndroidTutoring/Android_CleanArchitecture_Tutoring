package com.example.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.SearchedUser
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.data.retrofit.RetrofitHelper
import com.example.data.room.LocalDataBase
import com.example.data.source.local.UserLocalDataSourceImpl
import com.example.data.source.remote.UserRemoteDataSourceImpl
import com.example.presentation.activity.DetailActivity
import com.example.presentation.activity.SplashActivity
import com.example.presentation.adapter.UserListRvAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentUserBinding
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.util.Util.hideKeyboard
import com.example.presentation.util.Util.search
import com.example.presentation.viewmodel.MainViewModel
import com.example.presentation.viewmodel.factory.ViewModelFactory

//유저 프래그먼트
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private var page: Int = 1//페이지목록
    private var perPage: Int = 10//페이지당 요청하는 데이터 수

    private var searchedUsersList: List<PresentationSearchedUser>? = listOf()
    private lateinit var userListRcyAdapter: UserListRvAdapter

    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = LocalDataBase.getInstance(requireContext().applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl(RetrofitHelper)
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    //메인 activity와 공유하는  뷰모델
    private val mainSharedViewModel: MainViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(userRepository)
        ).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        getDataFromViewModel()
        listenerEvent()
    }

    //초기세팅
    private fun initSet() {

        binding.emptyView.visibility = View.VISIBLE

        userListRcyAdapter = UserListRvAdapter()
        binding.rcyUserList.apply {
            adapter = userListRcyAdapter
        }

        //splash 에서 받아온  유저 리스트 먼저 뿌려줌.
        showSplashUserList()
    }


    //뷰모델로부터  데이터 받아옴
    private fun getDataFromViewModel() {
        //검색  유저 리스트 업데이트
        mainSharedViewModel.userFragmentUpdateUserList.subscribe({
            userListRcyAdapter.submitList(it as MutableList<PresentationSearchedUser>?)
            binding.emptyView.visibility = View.GONE
        }, {
            showToast(it.message.toString())
        })
    }


    //splash 에서 받아온  유저 리스트
    private fun showSplashUserList() {
        searchedUsersList =
            requireActivity().intent
                .getParcelableArrayListExtra<SearchedUser>(SplashActivity.PARAM_INIT_USER_INFO)
                    as List<PresentationSearchedUser>

        if (!searchedUsersList.isNullOrEmpty()) {

            binding.editSearchUser.setText(searchedUsersList!![0].login)
            binding.emptyView.visibility = View.GONE

            mainSharedViewModel.getSearchUserList(searchedUsersList!!)
            mainSharedViewModel.filterFavoriteUser()
        }
    }


    //리스너 기능 모음.
    private fun listenerEvent() {
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
        userListRcyAdapter.setItemClickListener(object : UserListRvAdapter.ItemClickListener {
            override fun onItemClickListener(searchedUser: PresentationSearchedUser) {
                gotoDetailActivity(searchedUser)
            }
        })

        //즐겨 찾기 클릭시 처리
        userListRcyAdapter.setFavoriteMarkClickListener(object :
            UserListRvAdapter.FavoriteClickListener {
            override fun onFavoriteMarkListener(
                searchedUser: PresentationSearchedUser,
                position: Int
            ) {
                val deepCopiedList = userListRcyAdapter.currentList.map { it.copy() }
                val deepCopiedSearchedUser = searchedUser.copy()
                returnFavoriteMarkStatus(deepCopiedSearchedUser, deepCopiedList)
            }
        })

    }

    //즐겨 찾기 취소 또는 추가 여부에 따른  delete  add 를 리턴
    private fun returnFavoriteMarkStatus(
        searchedUser: PresentationSearchedUser,
        currentList: List<PresentationSearchedUser>
    ) {
        if (searchedUser.isMyFavorite) {
            showToast("즐겨찾기 취소")
            searchedUser.isMyFavorite = false
            mainSharedViewModel.deleteFavoriteUsers(
                presentationSearchedUser = searchedUser,
                presentationSearchedUserList = currentList,
                shouldRemoveData = false
            )

        } else {
            showToast("즐겨찾기 추가")
            searchedUser.isMyFavorite = true
            mainSharedViewModel.addFavoriteUsers(
                presentationSearchedUser = searchedUser,
                presentationSearchedUserList = currentList
            )
        }
    }

    //상세화면으로 가기
    private fun gotoDetailActivity(searchedUser: PresentationSearchedUser) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(PARAM_USER_INFO, searchedUser)
        startActivity(intent)
    }


    //유저 검색
    private fun searchUsers() {
        mainSharedViewModel.searchUser(binding.editSearchUser.text.toString(), page, perPage)
    }

    companion object {
        const val PARAM_USER_INFO = "param_user_info"


    }

}