package com.example.presentation.viewmodel

import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.SearchedUser
import com.example.presentation.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class MainViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    //즐겨찾기 프래그먼트 유저리스트 업데이트용
    val favoriteFragmentUpdateUserList: PublishSubject<List<SearchedUser>> =
        PublishSubject.create()

    //유저 프래그먼트 유저리스트 업데이트 용
    val userFragmentUpdateUserList: PublishSubject<List<SearchedUser>> =
        PublishSubject.create()

    var searchedUsersList: MutableList<SearchedUser>? = mutableListOf()

    fun getSearchUserList(searchedUserList: List<SearchedUser>){
       this.searchedUsersList = searchedUserList as MutableList<SearchedUser>
    }

    fun searchUser(searchQuery:String,page:Int,perPage:Int){
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
                    remote.body()?.items.let { searUserList ->
                        if (!searUserList.isNullOrEmpty()) {
                            searchedUsersList?.addAll(searUserList)
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
            .subscribe ({ searchedUsersList ->
                userFragmentUpdateUserList.onNext(searchedUsersList)
            },{
                userFragmentUpdateUserList.onError(Throwable("유저를 검색하는 중에문제가 생감"))
            })
            .addTo(compositeDisposable)
    }


    //유저 즐겨찾기 추가
    fun addFavoriteUsers(
        searchedUser: SearchedUser,
        currentList: List<SearchedUser>
    ){
        userRepository.addFavoriteUser(searchedUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                userFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 추가하는 중 문제가 생김"))
            }
            .doOnComplete {
                val newList = currentList.toMutableList()
                newList.find {
                    it.id == searchedUser.id
                }?.isMyFavorite = true
                userFragmentUpdateUserList.onNext(newList)
                getFavoriteUsers()
            }
            .subscribe()
            .addTo(compositeDisposable)
    }


    //즐겨찾기한 유저 지우기
    fun deleteFavoriteUsers(
        searchedUser: SearchedUser,
        currentList: List<SearchedUser>,
        shouldRemoveData:Boolean//favorite fragment에서는 지워주고, userfragment에서는  지워주면 안되고 뷰만 업데이트 해야되서 분기함.
    ) {
        userRepository.deleteFavoriteUser(searchedUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                if(shouldRemoveData){
                    favoriteFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 삭제하는 중 문제가 생김"))
                }else{
                    userFragmentUpdateUserList.onError(Throwable("즐겨찾기 유저 삭제하는 중 문제가 생김"))
                }
            }
            .doOnComplete {
                val newList = currentList.toMutableList()
                if(shouldRemoveData){//데이터를 지워야 하는 경우
                    newList.removeAll { it.id == searchedUser.id }

                    favoriteFragmentUpdateUserList.onNext(newList)
                    filterFavoriteUser()

                }else{//데이터를 변경만 하는 경우
                    newList.find {
                        it.id == searchedUser.id
                    }?.isMyFavorite = false
                    userFragmentUpdateUserList.onNext(newList)
                    getFavoriteUsers()
                }

            }.subscribe()
            .addTo(compositeDisposable)
    }

    //즐겨찾기 유저가  업데이트가 안된 경우에 업데이트를 진행한다.
    fun filterFavoriteUser(){
          //현재  검색된걸로 뿌려져있는 리스트에서 favorite에 있는 애들만 뽑아서 주고 나머지는 안줘야되는
          userRepository.getFavoriteUsers()
              .subscribeOn(Schedulers.io())
              .map { localFavoriteUsers->
                  val newList = searchedUsersList?.map { it.copy() }
                  newList?.map { searchedUsersList->
                      searchedUsersList.isMyFavorite = localFavoriteUsers.any{it.id == searchedUsersList.id}
                  }
                  newList
              }
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({
                  userFragmentUpdateUserList.onNext(it?.toMutableList())
              },{
                  userFragmentUpdateUserList.onError(Throwable("즐겨찾기 업데이트 중 문제 생김"))
              })
              .addTo(compositeDisposable)
    }

    //즐겨찾기 유저 가져오기
    fun getFavoriteUsers() {
        userRepository.getFavoriteUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favoriteUsers ->
                favoriteFragmentUpdateUserList.onNext(favoriteUsers)
            }, {
                favoriteFragmentUpdateUserList.onError(Throwable("즐겨찾기한 유저를 가져오는 중 문제가 생김"))
            })
            .addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
    }
}