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
import timber.log.Timber

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

    //유저프래그먼트에서 사용되는 유저리스트
    var searchedUsersList: MutableList<PresentationSearchedUser>? = mutableListOf()

    //즐겨찾기프래그먼트에 사용되는 유저리스트
    var favoriteUserList: MutableList<PresentationSearchedUser>? = mutableListOf()

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
                return@zip searchedUsersList?.map { it.copy() }//깊은 복사 안하면 계속 adapter의 리스트와 동기화되어서  사용함.
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
    ) {

        userRepository.addFavoriteUser(toDataModel(presentationSearchedUser))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                userFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 추가하는 중 문제가 생김"))
            }
            .doOnComplete {

                //추가했으니까  favorite user list에도 업데이트 해줌.
                favoriteUserList?.add(presentationSearchedUser)
                favoriteFragmentUpdateUserList.onNext(favoriteUserList)

                //searcheduser 리스트에는  관련 id체크 해서  별표 여부 true값 바꿔주고 update
                searchedUsersList?.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = true
                userFragmentUpdateUserList.onNext(searchedUsersList?.map { it.copy() })//깊은 복사 안하면 계속 adapter의 리스트와 동기화되어서  사용함.

            }
            .subscribe()
            .addTo(compositeDisposable)
    }


    //즐겨찾기한 유저 지우기
    fun deleteFavoriteUsers(
        presentationSearchedUser: PresentationSearchedUser,
        shouldRemoveData: Boolean//favorite fragment에서는 지워주고, userfragment에서는  지워주면 안되고 뷰만 업데이트 해야되서 분기함.
    ) {

        userRepository.deleteFavoriteUser(toDataModel(presentationSearchedUser))
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

                //즐겨찾기 리스트에서 해당값 삭제 해주고 즐겨찾기 리스트 업데이트
                favoriteUserList?.removeAll { it.id == presentationSearchedUser.id }
                favoriteFragmentUpdateUserList.onNext(favoriteUserList)

                //searcheduser 리스트에는  관련 id체크 해서  별표 여부 false값 바꿔주고 update
                searchedUsersList?.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = false
                userFragmentUpdateUserList.onNext(searchedUsersList?.map { it.copy() })

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

                val newList = searchedUsersList?.map { it.copy() }
                newList?.map { searchedUsersList ->
                    searchedUsersList.isMyFavorite =
                        dataModelFavoriteUsers.any { it.id == searchedUsersList.id }
                }

                //즐겨찾기 리스트  미리 업데이트 함.
                favoriteUserList
                    ?.addAll(dataModelFavoriteUsers
                        .map { toPresentationModel(it) })

                return@map newList
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userFragmentUpdateUserList.onNext(it?.toMutableList())
            }, {
                userFragmentUpdateUserList.onError(Throwable("즐겨찾기 업데이트 중 문제 생김"))
            })
            .addTo(compositeDisposable)
    }

}