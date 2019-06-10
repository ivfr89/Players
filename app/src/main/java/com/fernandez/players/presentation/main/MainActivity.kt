package com.fernandez.players.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandez.players.R
import com.fernandez.players.core.Failure
import com.fernandez.players.core.ScreenState
import com.fernandez.players.presentation.adapters.PlayersSectionAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

/*
* @author  Iván Fernández Rico
*/

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
    private lateinit var mPlayerAdapter: SectionedRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initStates()
        initListeners()

        configureUI()

        mViewModel.playersFromServer()

    }

    private fun initStates()
    {
        mViewModel.state.observe(::getLifecycle,::updateUI)
        mViewModel.failure.observe(::getLifecycle,::handleErrors)
    }

    private fun initListeners()
    {
        swipeRefresh.setOnRefreshListener {
            mViewModel.playersFromServer(force = true)
        }
    }


    private fun configureUI()
    {
        mPlayerAdapter = SectionedRecyclerViewAdapter()
        rcvPlayers.layoutManager = LinearLayoutManager(this)
        rcvPlayers.adapter = mPlayerAdapter
    }



    private fun handleErrors(failure: Failure?) {

//        Handle the errors and hide loader
        hideProgress()

        when(failure)
        {
            is Failure.NullResult -> {
                Toast.makeText(this, getString(R.string.player_null),Toast.LENGTH_LONG).show()
            }
            is Failure.NetworkConnection -> {
                Toast.makeText(this, getString(R.string.no_connection),Toast.LENGTH_LONG).show()
            }

            is Failure.ServerErrorCode ->{

                handleErrorCodes(failure)

            }
        }

    }

    private fun handleErrorCodes(failure: Failure.ServerErrorCode) {

//        This method depends on the failure request code given by the server.
//        In this case there isnt specification but the method exists for this purpose
    }




    private fun updateUI(screenState: ScreenState<MainScreenState>?) {

        when(screenState)
        {
            is ScreenState.Loading ->{
                showProgress()
            }

            is ScreenState.Render -> {
                hideProgress()
                renderScreenStates(screenState.renderState)
            }

        }

    }

    private fun renderScreenStates(renderState: MainScreenState) {

        when(renderState)
        {
            is MainScreenState.GetPlayersFromServer -> {


                renderState.listPlayers.forEach{
                    mPlayerAdapter.addSection(PlayersSectionAdapter(it))
                }

                mPlayerAdapter.notifyDataSetChanged()
            }
            else ->{

            }
        }


    }


    private fun showProgress()
    {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgress()
    {
        swipeRefresh.isRefreshing = false
    }





}
