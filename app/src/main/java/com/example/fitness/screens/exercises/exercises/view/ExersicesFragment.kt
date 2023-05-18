package com.example.fitness.screens.exercises.exercises.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.databinding.FragmentExersicesBinding
import com.example.fitness.screens.exercises.exercises.model.Exercise
import com.example.fitness.screens.exercises.exercises.view.adapter.ExercisesRecyclerAdapter
import com.example.fitness.utils.ExercisesManager
import com.example.fitness.utils.Utils
import com.example.fitness.utils.interfaces.OnItemClickListener
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import javax.inject.Inject

class ExersicesFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentExersicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var exercises: List<Exercise>
    @Inject lateinit var viewModel: ExercisesViewModel
    private val exerciseAdapter: ExercisesRecyclerAdapter by lazy { ExercisesRecyclerAdapter(this) }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exersices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExersicesBinding.bind(view)

        initListeners()
        getExercises()
        setRecyclerView()
    }

    private fun initListeners() {
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_exersicesFragment_to_performingExercisesFragment)
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        exerciseAdapter.exercises = exercises
    }

    private fun showExerciseDescription(exerciseId: Int) {
        findNavController().navigate(R.id.action_exersicesFragment_to_descriptionExercisesFragment,
            Bundle().apply { putInt(Utils.exerciseKeyID, exerciseId) })
    }

    private fun getExercises() {
        //@TODO
        exercises = ExercisesManager.getExercises(requireContext(), viewModel.selectedCategory)
        Log.i("kelo", "exercise = $exercises")
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onItemClick(position: Int) {
        showExerciseDescription(position)
    }

}