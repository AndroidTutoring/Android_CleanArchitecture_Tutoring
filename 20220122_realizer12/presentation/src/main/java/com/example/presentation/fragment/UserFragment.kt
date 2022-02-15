package com.example.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.SearchedUser
import com.example.presentation.R
import com.example.presentation.activity.DetailActivity
import com.example.presentation.activity.SplashActivity
import com.example.presentation.adapter.UserListRvAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentUserBinding
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.util.Util.hideKeyboard
import com.example.presentation.viewmodel.MainViewModel
import timber.log.Timber

//유저 프래그먼트
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    private var page: Int = 1//페이지목록
    private var perPage: Int = 10//페이지당 요청하는 데이터 수


    private lateinit var userListRcyAdapter: UserListRvAdapter

    //메인 activity와 공유하는  뷰모델
    private val mainSharedViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        listenerEvent()
    }

    //초기세팅
    private fun initSet() {

        //데바 필요한 값 연결
        binding.thisFragment = this
        binding.lifecycleOwner = this
        binding.mainViewModel = mainSharedViewModel

        //현재 프래그먼트 연결
        userListRcyAdapter = UserListRvAdapter()
        binding.rcyUserList.apply {
            adapter = userListRcyAdapter
        }

        //splash 에서 받아온  유저 리스트 먼저 뿌려줌.
        showSplashUserList()
    }


    //splash 에서 받아온  유저 리스트
    private fun showSplashUserList() {
        Timber.v("dadasdasd ")
        val splashSearchedUsersList = requireActivity().intent
            .getParcelableArrayListExtra<SearchedUser>(SplashActivity.PARAM_INIT_USER_INFO)
                as List<PresentationSearchedUser>

        if (!splashSearchedUsersList.isNullOrEmpty()) {

            binding.editSearchUser.setText(splashSearchedUsersList[0].login)
            binding.emptyView.visibility = View.GONE

            //받아온 검색된 유저리스트  업데이트  해주고, favorite filter 적용하면서, favorite리스트도 미리 받아둔다.
            //아직  favorite fragment가  create되기 전이므로 ..
            mainSharedViewModel.getSearchUserList(splashSearchedUsersList.map { it.copy() })
            mainSharedViewModel.initialListSetting()

            //configuration change로 다시 불릴때는 값 적용안되게 clear 시켜줌.
            requireActivity().intent.getParcelableArrayListExtra<SearchedUser>(SplashActivity.PARAM_INIT_USER_INFO)
                ?.clear()

        } else {

            //맨처음  splash 적용된후  이제 기존 뷰모델에 저장한 값들  다시 넣어주면됨
            binding.emptyView.visibility = View.GONE
            userListRcyAdapter.submitList(mainSharedViewModel.vmSearchedUsersList?.map { it.copy() })
        }
    }

    //검색 실행
    fun searchUserClickEvent() {
        binding.emptyView.visibility = View.VISIBLE
        page = 1
        searchUsers()
        hideKeyboard()//키보드 내림
    }


    //리스너 기능 모음.
    private fun listenerEvent() {

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
                val deepCopiedSearchedUser = searchedUser.copy()
                returnFavoriteMarkStatus(deepCopiedSearchedUser)
            }
        })

    }

    //즐겨 찾기 취소 또는 추가 여부에 따른  delete  add 를 리턴
    private fun returnFavoriteMarkStatus(
        searchedUser: PresentationSearchedUser
    ) {
        if (searchedUser.isMyFavorite) {
            showToast("즐겨찾기 취소")
            searchedUser.isMyFavorite = false
            mainSharedViewModel.deleteFavoriteUsers(
                presentationSearchedUser = searchedUser
            )

        } else {
            showToast("즐겨찾기 추가")
            searchedUser.isMyFavorite = true
            mainSharedViewModel.addFavoriteUsers(
                presentationSearchedUser = searchedUser
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