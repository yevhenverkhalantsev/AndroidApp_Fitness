package com.example.fitness.description.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.databinding.FragmentDescriptionExercisesBinding
import com.example.fitness.description.model.ExerciseWithDescription
import com.example.fitness.description.view.adapter.DescriptionExercisesRecyclerAdapter
import com.example.fitness.utils.ExercisesManager
import com.example.fitness.utils.Utils
import com.example.fitness.utils.interfaces.OnItemClickListener
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import javax.inject.Inject

class DescriptionExercisesFragment : Fragment() {
    private var _binding: FragmentDescriptionExercisesBinding? = null
    private val binding get() = _binding!!
    private lateinit var exerciseDescription: String
    private lateinit var exerciseName: String
    @Inject lateinit var viewModel: ExercisesViewModel
    private lateinit var exercise: ExerciseWithDescription
    private var exercise_number: Int = -1

    private lateinit var exercisesAdapter: DescriptionExercisesRecyclerAdapter



    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

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
            findNavController().navigate(R.id.action_descriptionExercisesFragment_to_exersicesFragment)
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = exercisesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setAdapterData() {
        exercisesAdapter.exercises = ExercisesManager.getExercises(requireContext(), viewModel.selectedCategory)
        binding.numberExercises.text = getString(R.string.amount_from_amount, exercise_number + 1, exercisesAdapter.exercises.size)
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
        binding.imageExercises.setImageDrawable(exercise.exercise.exercise_image)
        binding.nameExercises.text = exercise.exercise.exercise_name
        binding.descriptionExercises.text = exercise.description.joinToString(prefix = "\n-", separator = "\n-").removeSuffix("\n-")
        //@TODO init adapter for recycler view
    }

    private fun getExerciseInfo() {
        exercise_number = requireArguments().getInt(Utils.exerciseKeyID)
        exercise = ExercisesManager.getExercisesWithDescription(requireContext(), viewModel.selectedCategory, exercise_number)
        showExerciseInfo()
    }

    private fun setLoseWeightExerciseInfo(id: Int) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}