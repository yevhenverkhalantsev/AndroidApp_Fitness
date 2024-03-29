package com.example.fitness.screens.description.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.databinding.FragmentDescriptionExercisesBinding
import com.example.fitness.screens.description.model.ExerciseWithDescription
import com.example.fitness.screens.description.view.adapter.DescriptionExercisesRecyclerAdapter
import com.example.fitness.utils.Utils
import com.example.fitness.utils.interfaces.OnItemClickListener
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import com.example.fitness.utils.ExerciseManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DescriptionExercisesFragment : DaggerFragment() {
    private var _binding: FragmentDescriptionExercisesBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ExercisesViewModel by activityViewModels( factoryProducer = { factory } )
    @Inject lateinit var exerciseManager: ExerciseManager
    private lateinit var exercise: ExerciseWithDescription
    private var exerciseNumber: Int = -1

    private lateinit var exercisesAdapter: DescriptionExercisesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDescriptionExercisesBinding.bind(view)

        initListeners()
        getExerciseInfo()
        initAdapter()
        setRecyclerView()
        setAdapterData()
    }

    private fun initListeners() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = exercisesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("StringFormatMatches")
    private fun setAdapterData() {
        exercisesAdapter.exercises = exerciseManager.getExercises(viewModel.selectedCategory)
        binding.numberExercises.text = getString(R.string.amount_from_amount, exerciseNumber + 1, exercisesAdapter.exercises.size)
    }

    private fun initAdapter() {
        exercisesAdapter = DescriptionExercisesRecyclerAdapter(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt(Utils.exerciseKeyID, position)
                findNavController().navigate(R.id.action_descriptionExercisesFragment_self, bundle)
            }
        })
    }

    private fun showExerciseInfo() {
        binding.imageExercises.setImageDrawable(AppCompatResources.getDrawable(requireContext(), exercise.exercise.exercise_image))
        binding.nameExercises.text = exercise.exercise.exercise_name
        binding.descriptionExercises.text = exercise.description.joinToString(prefix = "\n-", separator = "\n-").removeSuffix("\n-")

    }

    private fun getExerciseInfo() {
        exerciseNumber = requireArguments().getInt(Utils.exerciseKeyID)
        exercise = exerciseManager.getExercisesWithDescription(viewModel.selectedCategory, exerciseNumber)
        showExerciseInfo()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}