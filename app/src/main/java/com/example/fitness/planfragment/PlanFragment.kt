package com.example.fitness.planfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fitness.R
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.databinding.FragmentPlanBinding
import com.example.fitness.planfragment.viewmodel.PlanViewModel
import com.example.fitness.registration.model.Resource
import dagger.android.support.DaggerFragment
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PlanFragment : DaggerFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: PlanViewModel by viewModels( factoryProducer = { factory } )

    private lateinit var userProgramSessions: List<UserProgramSession>
    private lateinit var userPrograms: List<UserProgram>
    private var selectedDay: DayOfWeek = DayOfWeek.MONDAY
    private var selectedDayTV: TextView = binding.monday



    private var _binding : FragmentPlanBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getUserPrograms()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlanBinding.bind(view)

        //initListeners()
        observeViewModel()

        saveUserProgramSession(DayOfWeek.MONDAY) //@TODO delete
    }

    private fun observeViewModel() {
        viewModel.userPrograms.observe(viewLifecycleOwner) {
            Log.e("Kranbomin", "userPrograms: $it")
            handleUserProgramsResourceResult(it)

        }
        viewModel.userProgramSessions.observe(viewLifecycleOwner) {
            Log.e("Kranbomin", "userProgramsSessions: $it")
            handleProgramSessionsResourceResult(it)
        }
    }

    private fun handleUserProgramsResourceResult(result: Resource) {
        when (result) {
            is Resource.Success<*> -> handleUserProgramSuccess(result)
            is Resource.Error -> handleUserProgramSessionError(result)
            is Resource.Loading -> setLoadingAnimationVisibility(View.VISIBLE)
        }
    }

    private fun handleUserProgramSuccess(result: Resource.Success<*>) {
        userPrograms = result.element as List<UserProgram>
    }

    private fun handleProgramSessionsResourceResult(result: Resource) {
        when (result) {
            is Resource.Success<*> -> handleUserProgramSessionSuccess(result)
            is Resource.Error -> handleUserProgramSessionError(result)
            is Resource.Loading -> setLoadingAnimationVisibility(View.VISIBLE)
        }
    }

    private fun handleUserProgramSessionError(error: Resource.Error) {
        setLoadingAnimationVisibility(View.GONE)
        //showErrorStatus() //@TODO add on UI button "Retry" ()and show here
    }

    private fun handleUserProgramSessionSuccess(it: Resource.Success<*>) {
        userProgramSessions = it.element as List<UserProgramSession>
        setLoadingAnimationVisibility(View.GONE)
        setCurrentDayData()
        initListeners()
    }

    private fun setLoadingAnimationVisibility(visibility: Int) {
        //binding.animationView.visibility = visibility //@TODO add animation
    }

    private fun setCurrentDayData() {
        //do user have program session for this day
        //if yes -> show program session name and option to CHANGE program session for this day
        //if no -> show option to select program session

    }

    private fun initListeners() {
        binding.monday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.MONDAY)
            Log.e("Kranbomin", "monday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.MONDAY)
        }
        binding.tuesday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.TUESDAY)
            Log.e("Kranbomin", "tuesday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.TUESDAY)
        }
        binding.wednesday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.WEDNESDAY)
            Log.e("Kranbomin", "wednesday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.WEDNESDAY)
        }
        binding.thursday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.THURSDAY)
            Log.e("Kranbomin", "thursday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.THURSDAY)
        }
        binding.friday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.FRIDAY)
            Log.e("Kranbomin", "friday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.FRIDAY)
        }
        binding.saturday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.SATURDAY)
            Log.e("Kranbomin", "saturday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.SATURDAY)
        }
        binding.sunday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.SUNDAY)
            Log.e("Kranbomin", "sunday.setOnClickListener")
            saveUserProgramSession(DayOfWeek.SUNDAY)
        }
    }

    private fun setSelectedDay(selectedDayTextView: TextView, day: DayOfWeek) {
        selectedDayTV.background = null
        selectedDayTV = selectedDayTextView
        selectedDayTextView.setBackgroundResource(R.drawable.selected_day_background)
        selectedDay = day
        setCurrentDayData()
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
//        viewModel.saveUserProgramSession(time = time.format(formatter))
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
