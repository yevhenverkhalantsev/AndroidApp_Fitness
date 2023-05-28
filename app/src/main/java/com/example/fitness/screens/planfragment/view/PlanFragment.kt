package com.example.fitness.screens.planfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.databinding.FragmentPlanBinding
import com.example.fitness.screens.planfragment.view.adapter.OnAdapterItemClickListener
import com.example.fitness.screens.planfragment.view.adapter.PlanRecyclerAdapter
import com.example.fitness.screens.planfragment.viewmodel.PlanViewModel
import com.example.fitness.screens.registration.model.Resource
import dagger.android.support.DaggerFragment
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject

class PlanFragment : DaggerFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: PlanViewModel by viewModels( factoryProducer = { factory } )

    private lateinit var userProgramSessions: List<UserProgramSession>
    private lateinit var userPrograms: MutableList<UserProgram>
    private var selectedDay: DayOfWeek = DayOfWeek.MONDAY
    private lateinit var selectedDayTV: TextView
    private lateinit var mAdapter : PlanRecyclerAdapter
    private lateinit var onItemClickListener: OnAdapterItemClickListener



    private var _binding : FragmentPlanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlanBinding.bind(view)
         selectedDayTV = binding.monday
        binding.selectYourProgramText.text = getString(R.string.select_program_for_plan,
            selectedDay.toString().lowercase())

        initFirstListeners()
        observeViewModel()

    }

    private fun initFirstListeners() {
        onItemClickListener = object : OnAdapterItemClickListener {
            override fun execute(program: UserProgram) {
                deleteUserProgram(program)
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserPrograms()
        }

    }

    private fun deleteUserProgram(program: UserProgram) {
        if (userPrograms.size == 1) {
            userPrograms.clear()
            mAdapter.updateData(listOf())
        }
        else {
            userPrograms.remove(program)
            mAdapter.updateData(userPrograms, null)
        }
        viewModel.deleteUserProgram(program)
    }

    private fun observeViewModel() {
        viewModel.userPrograms.observe(viewLifecycleOwner) {
            handleUserProgramsResourceResult(it)

        }
        viewModel.userProgramSessions.observe(viewLifecycleOwner) {
            handleProgramSessionsResourceResult(it)
        }
    }

    private fun handleUserProgramsResourceResult(result: Resource) {
        when (result) {
            is Resource.Success<*> -> handleUserProgramSuccess(result)
            is Resource.Error -> handleUserProgramSessionError(result)
            else -> {}
        }
    }

    private fun handleUserProgramSuccess(result: Resource.Success<*>) {
        showMainUI()
        userPrograms = result.element as MutableList<UserProgram>
    }

    private fun handleProgramSessionsResourceResult(result: Resource) {
        when (result) {
            is Resource.Success<*> -> handleUserProgramSessionSuccess(result)
            is Resource.Error -> handleUserProgramSessionError(result)
            else -> {}
        }
    }

    private fun handleUserProgramSessionError(error: Resource.Error) {
        showNoProgramsSavedScreen()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun handleUserProgramSessionSuccess(it: Resource.Success<*>) {
        binding.errorMessage.visibility = View.GONE
        userProgramSessions = it.element as List<UserProgramSession>
        binding.swipeRefreshLayout.isRefreshing = false
        setCurrentDayData()
        initListeners()
    }



    private fun setCurrentDayData() {
        if (userPrograms.isNotEmpty()) {
            if (userHasProgramForCurrentSelectedDay()) {
                setAdapterDataSelected()
            } else {
                setAdapterData()
            }
        }
        else {
            showNoProgramsSavedScreen()
        }

    }

    private fun showNoProgramsSavedScreen() {
        binding.confirmSelectedProgramButton.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = getString(R.string.no_programs_saved)
    }

    private fun showMainUI() {
        binding.confirmSelectedProgramButton.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
    }

    private fun setAdapterData() {
        mAdapter = PlanRecyclerAdapter(onItemClickListener)
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter.updateData(userPrograms, null)
    }

    private fun setAdapterDataSelected() {
        val selectedProgramId = getSelectedProgramName()
        mAdapter = PlanRecyclerAdapter(onItemClickListener)
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter.updateData(userPrograms, selectedProgramId)
    }

    private fun getSelectedProgramName(): Int? {
        if (userProgramSessions.isEmpty()) return null
        userProgramSessions.forEach {
            val localProgramDateTime = viewModel.convertDBDateTimeToLocal(it.startTime)
            if (localProgramDateTime.dayOfWeek == selectedDay) {
                return it.user_program_id
            }
        }
        return null
    }

    private fun userHasProgramForCurrentSelectedDay(): Boolean {
        if (userProgramSessions.isEmpty()) return false
        userProgramSessions.forEach {
            val localProgramDateTime = viewModel.convertDBDateTimeToLocal(it.startTime)
            if (localProgramDateTime.dayOfWeek == selectedDay) {
                return true
            }
        }
        return false
    }

    private fun initListeners() {
        binding.confirmSelectedProgramButton.setOnClickListener {
            if (mAdapter.selectedProgram != null) {
                saveUserProgramSession()
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.select_program), Toast.LENGTH_SHORT).show()
            }
        }
        binding.monday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.MONDAY)
        }
        binding.tuesday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.TUESDAY)
        }
        binding.wednesday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.WEDNESDAY)
        }
        binding.thursday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.THURSDAY)
        }
        binding.friday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.FRIDAY)
        }
        binding.saturday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.SATURDAY)
        }
        binding.sunday.setOnClickListener {
            setSelectedDay(it as TextView, DayOfWeek.SUNDAY)
        }
    }

    private fun setSelectedDay(selectedDayTextView: TextView, day: DayOfWeek) {
        selectedDayTV.background = null
        selectedDayTV = selectedDayTextView
        selectedDayTextView.setBackgroundResource(R.drawable.selected_day_background)
        selectedDay = day
        binding.selectYourProgramText.text = getString(R.string.select_program_for_plan, selectedDay.toString().lowercase())
        setCurrentDayData()
    }

    private fun saveUserProgramSession() {
        var time = LocalDateTime.now()
        for (i in 1..6) {
            if (time.dayOfWeek == selectedDay) {
                break
            }
            time = time.plusDays(1)
        }
        viewModel.saveUserProgramSession(mAdapter.selectedProgram!!, time)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
