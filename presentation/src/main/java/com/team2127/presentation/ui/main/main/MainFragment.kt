package com.team2127.presentation.ui.main.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.team2127.presentation.databinding.FragmentMainBinding
import com.team2127.presentation.ui.base.BaseFragment
import com.team2127.presentation.ui.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
) {
    private val viewModel: MainViewModel by viewModels()
    private val roomListAdapter = RoomListAdapter{roomId ->
        viewModel.deleteRoom(roomId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initRoomListRecyclerView()
    }

    override fun initListener() {
        binding.btnMainInputCode.setOnClickListener {
            moveToSearchRoom()
        }

        binding.lyMainRoomPlus.setOnClickListener{
            moveToGenerateRoom()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner){
            viewModel.event.collectLatest { event ->
                handleEvent(event)
            }
        }

        repeatOnStarted(viewLifecycleOwner){
            viewModel.roomList.collectLatest { list ->
                roomListAdapter.submitList(list)
            }
        }
    }

    override fun onDestroyView() {
        binding.rvMainRoomList.adapter = null
        super.onDestroyView()
    }

    private fun initRoomListRecyclerView(){
        binding.rvMainRoomList.adapter = roomListAdapter
    }

    private fun moveToSearchRoom(){
    }

    private fun moveToGenerateRoom(){
        val action = MainFragmentDirections.actionMainFragmentToGenerateRoomFragment()
        findNavController().navigate(action)
    }

    private fun handleEvent(event: MainViewModel.Event){
        when(event){
            MainViewModel.Event.SuccessGetRoomList -> {
                Toast.makeText(context, "방 검색 성공!", Toast.LENGTH_SHORT).show()
            }
            MainViewModel.Event.ForbiddenException -> {
                Toast.makeText(context, "로그인 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            MainViewModel.Event.InternetServerException -> {
                Toast.makeText(context, "인터넷 연결상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            MainViewModel.Event.UnKnownException -> {
                Toast.makeText(context, "알수 없는 오류가 있습니다. 앱을 재실행해주세요.", Toast.LENGTH_SHORT).show()
            }
            MainViewModel.Event.AlreadyDeleteRoomException -> {
                Toast.makeText(context, "이미 삭제된 방입니다.", Toast.LENGTH_SHORT).show()
            }
            MainViewModel.Event.NotExistRoomException -> {
                Toast.makeText(context, "존재하지 않는 방이에요", Toast.LENGTH_SHORT).show()
            }
            MainViewModel.Event.SuccessDeleteRoomList -> {
                Toast.makeText(context, "성공적으로 방을 삭제 했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
