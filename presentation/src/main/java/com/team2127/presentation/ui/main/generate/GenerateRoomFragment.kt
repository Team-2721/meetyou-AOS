package com.team2127.presentation.ui.main.generate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initSpinner()
    }

    override fun initListener() {
        binding.toolBarGenerateRoom.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner){
            viewModel.peopleCount.collectLatest {
                Log.d("spinner", "value : $it")
            }
        }
    }

    private fun initSpinner(){
        val array = resources.getStringArray(R.array.generate_room_input_count_array)
        viewModel.setSpinnerEntry(array.toList())
    }

    private fun initDatePickerDialog(){

    }
}
