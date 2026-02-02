package com.mktech.onovassignment.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mktech.onovassignment.R
import com.mktech.onovassignment.databinding.ActivityCharacterListBinding
import com.mktech.onovassignment.ui.detail.CharacterDetailsActivity
import com.mktech.onovassignment.util.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListActivity : AppCompatActivity() {
    private lateinit var adapter: CharacterAdapter
    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var binding: ActivityCharacterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = CharacterAdapter { character ->
            val intent = Intent(this, CharacterDetailsActivity::class.java)
            intent.putExtra("character_id", character.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        observeData()
        viewModel.fetchCharacters()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.submitList(state.data)
                    }

                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@CharacterListActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}