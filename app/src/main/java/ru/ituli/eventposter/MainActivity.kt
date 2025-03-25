package ru.ituli.eventposter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ituli.eventposter.adapters.ChoiceSpectacleAdapter
import ru.ituli.eventposter.databinding.ActivityMainBinding
import ru.ituli.eventposter.ktx.CoroutinesViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainBinding
    private val choiceAdapter: ChoiceSpectacleAdapter = ChoiceSpectacleAdapter()
    private val viewModel: CoroutinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.apply {
            spectacleList.adapter = choiceAdapter
            spectacleList.layoutManager = LinearLayoutManager(this@MainActivity)

            swipeRefreshLayout.setOnRefreshListener { viewModel.loadSpectacles() }
        }

        viewModel.loadState
            .onEach {render(it)}
            .launchIn(lifecycleScope)

//        viewModel.callError()
    }

    private fun render(state: ChoiceScreenState) {
        when(state) {
            is ChoiceScreenState.Error -> {
                viewBinding.apply {
                    errorLayout.isVisible = true
                    error.text = "An error occured!: \n${state.message}"
                    loadingLayout.isVisible = false
                    spectacleList.isVisible = false
                }
            }
            ChoiceScreenState.Loading -> {
                viewBinding.apply {
                    errorLayout.isVisible = false
                    loadingLayout.isVisible = true
                    spectacleList.isVisible = false
                }
            }
            is ChoiceScreenState.Data -> {
                viewBinding.apply {
                    errorLayout.isVisible = false
                    loadingLayout.isVisible = false
                    spectacleList.isVisible = true
                    choiceAdapter.submitList(state.data)
                    swipeRefreshLayout.isRefreshing = false
                }
            }
            ChoiceScreenState.Init -> {}
        }
    }
}