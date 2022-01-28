package com.example.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recylcerviewtest01.GithubRepo
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var profileAdapter: ProfileAdapter
    val datas = mutableListOf<GithubRepo>()
    private var backPressedTime : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickFavorite()

        retrofitService.Createretrofit.getRepos("tkdgusl94")
            .enqueue(object : Callback<List<GithubRepo>> {
                override fun onResponse(
                    call: Call<List<GithubRepo>>,
                    response: Response<List<GithubRepo>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            setAdapter(it)
                            progressBar.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                    Log.d("MainActivity","에러")
                    retrofitService.Createretrofit.getRepos("tkdgusl94")
                        .enqueue(object : Callback<List<GithubRepo>> {
                            override fun onResponse(
                                call: Call<List<GithubRepo>>,
                                response: Response<List<GithubRepo>>
                            ) {
                                if (response.isSuccessful) {
                                    val body = response.body()
                                    body?.let {
                                        setAdapter(it)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT)
                                    .show()

                            }

                        })
                }

            })

    }

    private fun setAdapter(userList : List<GithubRepo>){
        val mAdapter = ProfileAdapter(userList,this)
        val rv_profile : RecyclerView = findViewById(R.id.rv_profile)
        rv_profile.adapter = mAdapter
        rv_profile.layoutManager= LinearLayoutManager(this)
        rv_profile.setHasFixedSize(false)
        mAdapter.notifyDataSetChanged()

    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - backPressedTime <2000){
            finish()
            return
        }
        Toast.makeText(this, "한 번 더 뒤로가기 버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    private fun clickFavorite(){
        btn2.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

}