package com.team2127.presentation.ui.main.generate

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.team2127.presentation.R
import com.team2127.presentation.databinding.FragmentGenerateRoomBinding
import com.team2127.presentation.ui.base.BaseFragment
import com.team2127.presentation.ui.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GenerateRoomFragment: BaseFragment<FragmentGenerateRoomBinding>(
    FragmentGenerateRoomBinding::inflate
) {
    private val viewModel: GenerateRoomViewModel by viewModels()
    private var datePicker:  MaterialDatePicker<Pair<Long, Long>>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initPersonCountSpinner()
    }

    override fun initListener() {
        binding.toolBarGenerateRoom.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnGenerateRoomInputDate.setOnClickListener {
            initDateRangePicker()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner){
            viewModel.peopleCount.collectLatest {
                viewModel.peopleCountToInt()
            }
        }

        repeatOnStarted(viewLifecycleOwner){
            viewModel.startDate.collectLatest {
                binding.btnGenerateRoomInputDate.text = getString((R.string.generate_room_start_and_end_date), it, "")
            }
        }
    }

    private fun initPersonCountSpinner(){
        val array = resources.getStringArray(R.array.generate_room_input_count_array)
        viewModel.setSpinnerEntry(array.toList())
    }

    private fun initDateRangePicker(){
        if (datePicker == null){
            datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(
                    Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()
        }

        if (datePicker!!.isAdded.not()){
            datePicker!!.show(childFragmentManager, "Date Picker")
            datePicker!!.addOnPositiveButtonClickListener {
                viewModel.setStartEndDate(it)
            }
        }
    }

    private fun handleEvent(event: GenerateRoomViewModel.Event){
        when(event){
            GenerateRoomViewModel.Event.InvalidRequestException -> {
                Toast.makeText(context, "방 제목이 너무 길어요", Toast.LENGTH_SHORT).show()
            }
            GenerateRoomViewModel.Event.SuccessGenerateRoom -> {
                Toast.makeText(context, "새로운 방이 생성 되었습니다.", Toast.LENGTH_SHORT).show()
            }
            GenerateRoomViewModel.Event.UnKnownException -> {
                Toast.makeText(context, "알 수 없는 이유로방 생성에 실패 했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
