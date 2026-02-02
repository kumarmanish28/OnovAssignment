package com.mktech.onovassignment.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mktech.onovassignment.R
import com.mktech.onovassignment.util.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {
    private val viewModel: CharacterDetailViewModel by viewModels()

    private lateinit var imageView: ImageView
    private lateinit var nameTv: TextView
    private lateinit var statusTv: TextView
    private lateinit var speciesTv: TextView
    private lateinit var genderTv: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_charachter_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imageView)
        nameTv = findViewById(R.id.nameTv)
        statusTv = findViewById(R.id.statusTv)
        speciesTv = findViewById(R.id.speciesTv)
        genderTv = findViewById(R.id.genderTv)
        progressBar = findViewById(R.id.progressBar)

        val characterId = intent.getIntExtra("character_id", -1)

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
                        progressBar.visibility = View.VISIBLE
                    }

                    is ResultState.Success -> {
                        progressBar.visibility = View.GONE
                        val character = state.data

                        nameTv.text = character.name
                        statusTv.text = character.status
                        speciesTv.text = character.species
                        genderTv.text = character.gender

                        Glide.with(this@CharacterDetailsActivity)
                            .load(character.image)
                            .into(imageView)
                    }

                    is ResultState.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@CharacterDetailsActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}