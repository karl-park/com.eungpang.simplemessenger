package com.eungpang.simplemessenger.ui.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.databinding.ActivityFriendsBinding

class FriendsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}