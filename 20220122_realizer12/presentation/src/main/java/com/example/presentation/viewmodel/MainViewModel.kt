package com.example.presentation.viewmodel

import com.example.data.repository.UserRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.model.PresentationSearchedUser.Companion.toDataModel
import com.example.presentation.model.PresentationSearchedUser.Companion.toPresentationModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class MainViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val behaviorSubject = BehaviorSubject.createDefault(0L)

    //메인 엑티비티 뒤로가기 관련 publish subject
    val mainBackPressPublishSubject: PublishSubject<Boolean> = PublishSubject.create()

    //즐겨찾기 프래그먼트 유저리스트 업데이트용
    val favoriteFragmentUpdateUserList: PublishSubject<List<PresentationSearchedUser>> =
        PublishSubject.create()

    //유저 프래그먼트 유저리스트 업데이트 용
    val userFragmentUpdateUserList: PublishSubject<List<PresentationSearchedUser>> =
        PublishSubject.create()

    var searchedUsersList: MutableList<PresentationSearchedUser>? = mutableListOf()

    fun getSearchUserList(searchedUserList: List<PresentationSearchedUser>) {
        this.searchedUsersList = searchedUserList as MutableList<PresentationSearchedUser>
    }


    //뒤로가기 프로세스 체크처리
    fun backPressCheck() {

        //항상 누른거 이전걸로 체크해야되니까 buffer count 2 주고 skip 1로줌.
        behaviorSubject.buffer(2, 1)
            .map { it[0] to it[1] }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                mainBackPressPublishSubject.onError(Throwable("뒤로가기 진행중 문제 발생"))
            }
            .subscribe {
                if (it.second - it.first < 2000L) {//첫번째 누른것과 두번째 누른 값의 차가 2초이내이면 뒤로가기 처리
                    mainBackPressPublishSubject.onNext(true)
                } else {
                    mainBackPressPublishSubject.onNext(false)
                }
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

                if (page == 1) {
                    searchedUsersList = ArrayList()
                }
                remote.body()?.items.let { dataModelSearchUserList ->
                    if (!dataModelSearchUserList.isNullOrEmpty()) {
                        val presentationSearchUserList =
                            dataModelSearchUserList.map { toPresentationModel(searchedUser = it) }
                        searchedUsersList?.addAll(presentationSearchUserList)
                    }
                    searchedUsersList?.map { searchedUser ->
                        if (local.any { it.id == searchedUser.id }) {
                            searchedUser.isMyFavorite = true
                        }
                    }
                }
                searchedUsersList

            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchedUsersList ->
                userFragmentUpdateUserList.onNext(searchedUsersList)
            }, {
                userFragmentUpdateUserList.onError(Throwable("유저를 검색하는 중에문제가 생감"))
            })
            .addTo(compositeDisposable)
    }


    //유저 즐겨찾기 추가
    fun addFavoriteUsers(
        presentationSearchedUser: PresentationSearchedUser,
        presentationSearchedUserList: List<PresentationSearchedUser>
    ) {

        userRepository.addFavoriteUser(toDataModel(presentationSearchedUser))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                userFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 추가하는 중 문제가 생김"))
            }
            .doOnComplete {
                val newList = presentationSearchedUserList.toMutableList()
                newList.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = true
                userFragmentUpdateUserList.onNext(newList)
                getFavoriteUsers()
            }
            .subscribe()
            .addTo(compositeDisposable)
    }


    //즐겨찾기한 유저 지우기
    fun deleteFavoriteUsers(
        presentationSearchedUser: PresentationSearchedUser,
        presentationSearchedUserList: List<PresentationSearchedUser>,
        shouldRemoveData: Boolean//favorite fragment에서는 지워주고, userfragment에서는  지워주면 안되고 뷰만 업데이트 해야되서 분기함.
    ) {

        val dataModelSearchedUser = toDataModel(presentationSearchedUser)

        userRepository.deleteFavoriteUser(dataModelSearchedUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                if (shouldRemoveData) {
                    favoriteFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 삭제하는 중 문제가 생김"))
                } else {
                    userFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 삭제하는 중 문제가 생김"))
                }
            }
            .doOnComplete {
                val newList = presentationSearchedUserList.toMutableList()
                if (shouldRemoveData) {//데이터를 지워야 하는 경우
                    newList.removeAll { it.id == dataModelSearchedUser.id }

                    favoriteFragmentUpdateUserList.onNext(newList)
                    filterFavoriteUser()

                } else {//데이터를 변경만 하는 경우
                    newList.find {
                        it.id == dataModelSearchedUser.id
                    }?.isMyFavorite = false
                    userFragmentUpdateUserList.onNext(newList)
                    getFavoriteUsers()
                }

            }.subscribe()
            .addTo(compositeDisposable)
    }

    //즐겨찾기 유저가  업데이트가 안된 경우에 업데이트를 진행한다.
    fun filterFavoriteUser() {
        //현재  검색된걸로 뿌려져있는 리스트에서 favorite에 있는 애들만 뽑아서 주고 나머지는 안줘야되는
        userRepository.getFavoriteUsers()
            .subscribeOn(Schedulers.io())
            .map { dataModelFavoriteUsers ->

                val newList = searchedUsersList?.map { it.copy() }
                newList?.map { searchedUsersList ->
                    searchedUsersList.isMyFavorite =
                        dataModelFavoriteUsers.any { it.id == searchedUsersList.id }
                }
                newList
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userFragmentUpdateUserList.onNext(it?.toMutableList())
            }, {
                userFragmentUpdateUserList.onError(Throwable("즐겨찾기 업데이트 중 문제 생김"))
            })
            .addTo(compositeDisposable)
    }

    //즐겨찾기 유저 가져오기
    fun getFavoriteUsers() {
        userRepository.getFavoriteUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dataModelFavoriteUsers ->
                favoriteFragmentUpdateUserList.onNext(dataModelFavoriteUsers.map {
                    toPresentationModel(
                        it
                    )
                })
            }, {
                favoriteFragmentUpdateUserList.onError(Throwable("즐겨찾기한 유저를 가져오는 중 문제가 생김"))
            })
            .addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
    }
}