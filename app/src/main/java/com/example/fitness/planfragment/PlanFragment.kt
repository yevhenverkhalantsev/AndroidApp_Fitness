package com.example.fitness.planfragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fitness.R
import com.example.fitness.databinding.FragmentPerformingExercisesBinding
import com.example.fitness.databinding.FragmentPlanBinding
import com.example.fitness.planfragment.viewmodel.PlanViewModel
import com.example.fitness.registration.viewmodel.RegistrationViewModel
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import dagger.android.support.DaggerFragment
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PlanFragment : DaggerFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: PlanViewModel by viewModels( factoryProducer = { factory } )

    private var _binding : FragmentPlanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveUserProgramSession(DayOfWeek.MONDAY) //@TODO delete
    }

    private fun saveUserProgramSession(dayOfWeek: DayOfWeek) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        //val timeDateTime = LocalDateTime.now().format(formatter)
        var time = LocalDateTime.now()
        for (i in 1..6) {
            if (time.dayOfWeek == dayOfWeek) {
                break
            }
            time = time.plusDays(1)
        }
        viewModel.saveUserProgramSession(time = time.format(formatter))
        Log.e("TimeDateTimeCheck", "LocalDateTime.now().dayOfWeek = ${LocalDateTime.now().dayOfWeek}")
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
