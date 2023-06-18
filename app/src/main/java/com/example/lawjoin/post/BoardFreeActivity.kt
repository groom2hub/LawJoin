package com.example.lawjoin.post

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lawjoin.common.ViewModelFactory
import com.example.lawjoin.databinding.ActivityFreeBoardListBinding

@RequiresApi(Build.VERSION_CODES.O)
open class BoardFreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFreeBoardListBinding
    private lateinit var postViewModel: PostViewModel
    lateinit var postadapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeBoardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvPostList = binding.rvPostList

        postViewModel = ViewModelProvider(this, ViewModelFactory())[PostViewModel::class.java]
        postViewModel.findAllPosts()

        postViewModel.posts.observe(this) { posts ->
            postadapter = PostAdapter(posts, this)
            rvPostList.adapter = postadapter

        }
        val postLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPostList.setHasFixedSize(true)
        rvPostList.layoutManager = postLayoutManager

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    postadapter.filter.filter(query)
                    return false
                }
            })

        binding.btnWrite.setOnClickListener {
            val intent = Intent(applicationContext, WritePostActivity::class.java)
            intent.putExtra("type", "free")
            startActivity(intent)
        }
    }
}