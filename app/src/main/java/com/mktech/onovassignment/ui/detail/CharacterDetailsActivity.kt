package com.mktech.onovassignment.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mktech.onovassignment.R
import com.mktech.onovassignment.databinding.ActivityCharachterDetailsBinding
import com.mktech.onovassignment.util.ResultState
import com.mktech.onovassignment.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {
    private val viewModel: CharacterDetailViewModel by viewModels()

    private lateinit var binding: ActivityCharachterDetailsBinding
    private var characterId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCharachterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        characterId = intent.getIntExtra("character_id", -1)

        observeData()

        if (characterId != -1) {
            viewModel.fetchCharacterDetails(characterId)
        }
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
                        val character = state.data

                        binding.nameTv.text = character.name?:"-"
                        binding.statusTv.text = character.status?:"-"
                        binding.speciesTv.text = character.species?:"-"
                        binding.genderTv.text = character.gender?:"-"
                        binding.locationTv.text = character.location.name?:"-"

                        Glide.with(this@CharacterDetailsActivity)
                            .load(character.image)
                            .into(binding.imageView)
                    }

                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Utility.showErrorDialog(
                            this@CharacterDetailsActivity,
                            "Error",
                            state.message
                        ) {
                            if (characterId != -1) {
                                viewModel.fetchCharacterDetails(characterId)
                            }
                        }
                    }
                }
            }
        }
    }
}