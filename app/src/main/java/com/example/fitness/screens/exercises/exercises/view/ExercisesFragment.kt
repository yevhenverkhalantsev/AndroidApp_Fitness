package com.example.fitness.screens.exercises.exercises.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.databinding.FragmentExersicesBinding
import com.example.fitness.domain.model.RemoteResource
import com.example.fitness.domain.model.UserProgramUI
import com.example.fitness.screens.exercises.exercises.model.Exercise
import com.example.fitness.screens.exercises.exercises.view.adapter.ExercisesRecyclerAdapter
import com.example.fitness.utils.Utils
import com.example.fitness.utils.interfaces.OnItemClickListener
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import com.example.fitness.utils.ExerciseManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ExercisesFragment : DaggerFragment(), OnItemClickListener {
    private var _binding: FragmentExersicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var exercises: List<Exercise>

    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ExercisesViewModel by activityViewModels(factoryProducer = { factory })
    @Inject lateinit var exercisesManager: ExerciseManager

    private val exerciseAdapter: ExercisesRecyclerAdapter by lazy { ExercisesRecyclerAdapter(object : OnItemClickListener{
        override fun onItemClick(position: Int) {
            showExerciseDescription(position)
        }


    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExersicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        getExercises()
        setRecyclerView()
    }

    private fun initListeners() {
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_exersicesFragment_to_performingExercisesFragment)
        }
        binding.addExercises.setOnClickListener {
            addExercisesToCurrentUser()
        }

        viewModel.savingState.observe(viewLifecycleOwner) {
            handleSaveResult(it)
        }
    }

    private fun handleSaveResult(result: RemoteResource) {
         when (result) {
             is RemoteResource.OnConflict -> showOnConflict(result.message)
             is RemoteResource.Loading -> {
                if (result.inProcess) {
                    hideMainUI(View.GONE)
                    binding.animationView.visibility = View.VISIBLE
                    binding.addExercises.visibility = View.INVISIBLE
                } else {
                    binding.addExercises.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.add))
                    hideMainUI(View.VISIBLE)
                    binding.animationView.visibility = View.GONE
                }
             }
             is RemoteResource.Success<*> -> {
                 binding.addExercises.visibility = View.VISIBLE
                 binding.addExercises.setOnClickListener(null)
                 binding.addExercises.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.done))
                 hideMainUI(View.VISIBLE)
                 binding.animationView.visibility = View.GONE
                 Toast.makeText(requireContext(), getString(R.string.success_exercises_added), Toast.LENGTH_LONG).show()
             }
             is RemoteResource.Error -> {
                 hideMainUI(View.VISIBLE)
                 binding.addExercises.setOnClickListener(null)
                 binding.addExercises.visibility = View.INVISIBLE
                 binding.animationView.visibility = View.GONE
                 Toast.makeText(requireContext(), getString(R.string.you_already_have_this_program_saved), Toast.LENGTH_LONG).show()
             }
         }
    }

    private fun hideMainUI(visibility: Int) {
        binding.startButton.visibility = visibility
        binding.recyclerView.visibility = visibility
        binding.addExercises.visibility = visibility
    }

    private fun showOnConflict(message: String) {
        hideMainUI(View.VISIBLE)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.animationView.visibility = View.GONE
        binding.addExercises.visibility = View.VISIBLE
        binding.addExercises.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.done))
        binding.addExercises.setOnClickListener(null)
    }

    private fun addExercisesToCurrentUser() {
        viewModel.saveUserProgram(
            UserProgramUI(
                name = viewModel.selectedCategory.nameResourceId,
                exercises = exercisesManager.getDatabaseExercises(viewModel.selectedCategory),
                icon = exercisesManager.getProgramIcon(viewModel.selectedCategory)
            )
        )
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        exerciseAdapter.exercises = exercises
    }

    private fun showExerciseDescription(exerciseId: Int) {
        findNavController().navigate(
            R.id.action_exersicesFragment_to_descriptionExercisesFragment,
            Bundle().apply { putInt(Utils.exerciseKeyID, exerciseId) }
        )
    }

    private fun getExercises() {
        exercises = exercisesManager.getExercises(viewModel.selectedCategory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearState()
    }

    override fun onItemClick(position: Int) {
        showExerciseDescription(position)
    }
}
