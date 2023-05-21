package com.example.fitness.planfragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitness.R
import com.example.fitness.databinding.FragmentPerformingExercisesBinding
import com.example.fitness.databinding.FragmentPlanBinding
import com.example.fitness.registration.viewmodel.RegistrationViewModel
import javax.inject.Inject

class PlanFragment : Fragment() {

    private var _binding : FragmentPlanBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

//    private val spendTime = object : CountDownTimer(30000, 1000) {
//        override fun onTick(millisUntilFinished: Long) {
//            Log.i("PerformingExercisesFragment", "onTick: $millisUntilFinished")
//            binding.countdown.text = (millisUntilFinished / 1000).toString()
//        }
//
//        override fun onFinish() {
//            Log.i("PerformingExercisesFragment", "onFinish: ")
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        spendTime.start()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        spendTime.cancel()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
