package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.UserRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.model.PresentationSearchedUser.Companion.toDataModel
import com.example.presentation.model.PresentationSearchedUser.Companion.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val behaviorSubject = BehaviorSubject.createDefault(0L)

    //메인 엑티비티 뒤로가기 가능 여부 post
    private val _isBackPressPossible = MutableLiveData<Boolean>()
    val isBackPressPossible:LiveData<Boolean> = _isBackPressPossible

    //뷰모델 안에서 사용되는 유저, 즐겨찾기 리스트
    val vmSearchedUsersList: MutableList<PresentationSearchedUser> = mutableListOf()
    val vmFavoriteUserList: MutableList<PresentationSearchedUser> = mutableListOf()

    //유저리스트 라이브데이터
    private val _searchedUserList = MutableLiveData<List<PresentationSearchedUser>>()
    val searchedUsersList: LiveData<List<PresentationSearchedUser>> = _searchedUserList

    //즐겨찾기 유저리스트 라이브데이터
    private val _favoriteUserList = MutableLiveData<List<PresentationSearchedUser>>()
    val favoriteUserList: LiveData<List<PresentationSearchedUser>> = _favoriteUserList


    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun getSearchUserList(searchedUserList: List<PresentationSearchedUser>) {
        this.vmSearchedUsersList.addAll(searchedUserList as MutableList<PresentationSearchedUser>)
    }


    //뒤로가기 프로세스 체크처리
    fun backPressCheck() {

        //항상 누른거 이전걸로 체크해야되니까 buffer count 2 주고 skip 1로줌.
        behaviorSubject.buffer(2, 1)
            .map { it[0] to it[1] }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = Throwable("뒤로가기 진행중 문제 발생")
            }
            .subscribe {
                _isBackPressPossible.value = it.second - it.first < 2000L
            }.addTo(compositeDisposable)
    }

    fun searchUser(searchQuery: String, page: Int, perPage: Int) {
        //local , remote  zip으로  동시에 다 처리되면  뿌려지게 수정
        Single.zip(
            userRepository.getSearchUsers(
                searchQuery,
                page,
                perPage
            ).subscribeOn(Schedulers.io())
                .retry(2),
            userRepository.getFavoriteUsers(), { remote, local ->

                //if(remote.isSuccessful){//remote 검색 성공했을때만 진행
                    if (page == 1) {
                        vmSearchedUsersList.clear()
                    }
                    remote.items.let { dataModelSearchUserList ->
                        if (!dataModelSearchUserList.isNullOrEmpty()) {
                            val presentationSearchUserList =
                                dataModelSearchUserList.map { toPresentationModel(searchedUser = it) }
                            vmSearchedUsersList.addAll(presentationSearchUserList)
                        }
                        vmSearchedUsersList.map { searchedUser ->
                            if (local.any { it.id == searchedUser.id }) {
                                searchedUser.isMyFavorite = true
                            }
                        }
                    }
                    return@zip vmSearchedUsersList.map { it.copy() }//깊은 복사 안하면 계속 adapter의 리스트와 동기화되어서  사용함.

                //}else{//검색 실패시에는 에러 던짐
                 //   throw RuntimeException()
                //}

            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchedUsersList ->
                _searchedUserList.value = searchedUsersList
            }, {
                _error.value = Throwable("유저를 검색하는 중에문제가 생감")
            })
            .addTo(compositeDisposable)
    }


    //유저 즐겨찾기 추가
    fun addFavoriteUsers(
        presentationSearchedUser: PresentationSearchedUser,
    ) {

        userRepository.addFavoriteUser(toDataModel(presentationSearchedUser))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = Throwable("즐겨찾기 유저 추가하는 중 문제가 생김")
            }
            .doOnComplete {

                //추가했으니까  favorite user list에도 업데이트 해줌.
                vmFavoriteUserList?.add(presentationSearchedUser)
                _favoriteUserList.value = vmFavoriteUserList

                //searcheduser 리스트에는  관련 id체크 해서  별표 여부 true값 바꿔주고 update
                vmSearchedUsersList?.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = true
                _searchedUserList.value =
                    vmSearchedUsersList?.map { it.copy() }//깊은 복사 안하면 계속 adapter의 리스트와 동기화되어서  사용함.

            }
            .subscribe()
            .addTo(compositeDisposable)
    }


    //즐겨찾기한 유저 지우기
    fun deleteFavoriteUsers(
        presentationSearchedUser: PresentationSearchedUser
    ) {

        userRepository.deleteFavoriteUser(toDataModel(presentationSearchedUser))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = Throwable("즐겨찾기 유저 삭제하는 중 문제가 생김")
            }
            .doOnComplete {

                //즐겨찾기 리스트에서 해당값 삭제 해주고 즐겨찾기 리스트 업데이트
                vmFavoriteUserList?.removeAll { it.id == presentationSearchedUser.id }
                _favoriteUserList.value = vmFavoriteUserList

                //searcheduser 리스트에는  관련 id체크 해서  별표 여부 false값 바꿔주고 update
                vmSearchedUsersList?.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = false

                _searchedUserList.value = vmSearchedUsersList?.map { it.copy() }

            }.subscribe()
            .addTo(compositeDisposable)
    }

    //초기 스플래시에서 넘어온 값들 즐겨찾기 여부 체크해주고,
    //favoriteuserlist 미리  업데이트 해놓음.
    fun initialListSetting() {
        //현재  검색된걸로 뿌려져있는 리스트에서 favorite에 있는 애들만 뽑아서 주고 나머지는 안줘야되는
        userRepository.getFavoriteUsers()
            .subscribeOn(Schedulers.io())
            .map { dataModelFavoriteUsers ->

                val newList = vmSearchedUsersList?.map { it.copy() }
                newList?.map { searchedUsersList ->
                    searchedUsersList.isMyFavorite =
                        dataModelFavoriteUsers.any { it.id == searchedUsersList.id }
                }

                //즐겨찾기 리스트  미리 업데이트 함.
                vmFavoriteUserList
                    ?.addAll(dataModelFavoriteUsers
                        .map { toPresentationModel(it) })

                return@map newList
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteUserList.value = vmFavoriteUserList
                _searchedUserList.value = it?.toMutableList()
            }, {
                _error.value = Throwable("즐겨찾기 업데이트 중 문제 생김")
            })
            .addTo(compositeDisposable)
    }

}