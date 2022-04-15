package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.usecase.*
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.SearchedUserPresentationModel
import com.example.presentation.model.SearchedUserPresentationModel.Companion.toEntity
import com.example.presentation.model.SearchedUserPresentationModel.Companion.toPresentationModel
import com.example.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSearchedUsersListUseCase: GetSearchedUsersListInMainUseCase,
    private val addFavoriteUsersUseCase: AddFavoriteUsersUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val updateSearchedUsersFavoriteUseCase: UpdateSearchedUsersFavoriteUseCase,
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase
) : BaseViewModel() {

    val behaviorSubject = BehaviorSubject.createDefault(0L)

    //메인 엑티비티 뒤로가기 가능 여부 post
    private val _isBackPressPossible = MutableLiveData<Boolean>()
    val isBackPressPossible:LiveData<Boolean> = _isBackPressPossible

    //뷰모델 안에서 사용되는 유저, 즐겨찾기 리스트
    val tempSearchedUsersList: MutableList<SearchedUserPresentationModel> = mutableListOf()
    val tempFavoriteUserList: MutableList<SearchedUserPresentationModel> = mutableListOf()

    //유저리스트 라이브데이터
    private val _searchedUserList = MutableLiveData<List<SearchedUserPresentationModel>>()
    val searchedUsersList: LiveData<List<SearchedUserPresentationModel>> = _searchedUserList

    //즐겨찾기 유저리스트 라이브데이터
    private val _favoriteUserList = MutableLiveData<List<SearchedUserPresentationModel>>()
    val favoriteUserList: LiveData<List<SearchedUserPresentationModel>> = _favoriteUserList

    //즐겨찾기 유저리스트 라이브데이터
    private val _initialSetting = MutableLiveData<Event<Boolean>>()
    val initialSetting: LiveData<Event<Boolean>> = _initialSetting

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun getSearchUserList(searchedUserList: List<SearchedUserPresentationModel>) {
        this.tempSearchedUsersList.addAll(searchedUserList as MutableList<SearchedUserPresentationModel>)
    }


    init {
        _initialSetting.value = Event(true)

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
        getSearchedUsersListUseCase.searchUsers(searchQuery, page, perPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchedUsersList ->

                if(page == 1) {
                    tempSearchedUsersList.clear()
                }
                tempSearchedUsersList.addAll(searchedUsersList.map { toPresentationModel(it) })
                _searchedUserList.value =tempSearchedUsersList
            }, {
                _error.value = Throwable("유저를 검색하는 중에문제가 생감")
            })
            .addTo(compositeDisposable)
    }


    //유저 즐겨찾기 추가
    fun addFavoriteUsers(
        presentationSearchedUser: SearchedUserPresentationModel,
    ) {
        addFavoriteUsersUseCase.addFavoriteUsers(toEntity(presentationSearchedUser))
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = Throwable("즐겨찾기 유저 추가하는 중 문제가 생김")
            }
            .doOnComplete {

                //추가했으니까  favorite user list에도 업데이트 해줌.
                tempFavoriteUserList.add(presentationSearchedUser)
                _favoriteUserList.value = tempFavoriteUserList

                //searcheduser 리스트에는  관련 id체크 해서  별표 여부 true값 바꿔주고 update
                tempSearchedUsersList.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = true
                _searchedUserList.value =
                    tempSearchedUsersList.map { it.copy() }//깊은 복사 안하면 계속 adapter의 리스트와 동기화되어서  사용함.

            }
            .subscribe()
            .addTo(compositeDisposable)
    }


    //즐겨찾기한 유저 지우기
    fun deleteFavoriteUsers(
        presentationSearchedUser: SearchedUserPresentationModel
    ) {

        deleteFavoriteUseCase.deleteFavoriteUsers(toEntity(presentationSearchedUser))
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = Throwable("즐겨찾기 유저 삭제하는 중 문제가 생김")
            }
            .doOnComplete {

                //즐겨찾기 리스트에서 해당값 삭제 해주고 즐겨찾기 리스트 업데이트
                tempFavoriteUserList.removeAll { it.id == presentationSearchedUser.id }
                _favoriteUserList.value = tempFavoriteUserList

                //searcheduser 리스트에는  관련 id체크 해서  별표 여부 false값 바꿔주고 update
                tempSearchedUsersList.find {
                    it.id == presentationSearchedUser.id
                }?.isMyFavorite = false

                _searchedUserList.value = tempSearchedUsersList.map { it.copy() }

            }.subscribe()
            .addTo(compositeDisposable)
    }

    //초기 스플래시에서 넘어온 값들 즐겨찾기 여부 체크해주고,
    //favoriteuserlist 미리  업데이트 해놓음.
    fun initialListSetting() {


        getFavoriteUsersUseCase
            .getFavoriteUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 tempFavoriteUserList.addAll(it.map { toPresentationModel(it) })
                _favoriteUserList.value = tempFavoriteUserList
            }, {
                _error.value = Throwable("즐겨찾기 업데이트 중 문제 생김")
            })




        //현재  검색된걸로 뿌려져있는 리스트에서 favorite에 있는 애들만 뽑아서 주고 나머지는 안줘야되는
        updateSearchedUsersFavoriteUseCase.updateFavoriteValue(tempSearchedUsersList.map { toEntity(it) })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchedUserList.value = it.map { toPresentationModel(it) }
            },{
                _error.value = Throwable("즐겨찾기 업데이트 중 문제 생김")
            }).addTo(compositeDisposable)


    }

}