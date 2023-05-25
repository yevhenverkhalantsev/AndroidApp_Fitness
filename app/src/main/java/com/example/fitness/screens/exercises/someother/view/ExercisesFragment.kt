package com.example.fitness.screens.exercises.someother.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.databinding.FragmentExersicesBinding
import com.example.fitness.domain.model.RemoteResource
import com.example.fitness.domain.model.UserProgramUI
import com.example.fitness.registration.model.Resource
import com.example.fitness.registration.model.User
import com.example.fitness.screens.exercises.someother.model.Exercise
import com.example.fitness.screens.exercises.someother.model.UserExerciseUI
import com.example.fitness.screens.exercises.someother.view.adapter.ExercisesRecyclerAdapter
import com.example.fitness.utils.Utils
import com.example.fitness.utils.interfaces.OnItemClickListener
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import com.example.fitness.utils.ExerciseManager
import com.example.fitness.utils.UserManager
import dagger.android.support.DaggerFragment
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ExercisesFragment : DaggerFragment() {
    //class ExercisesFragment : DaggerFragment(), OnItemClickListener {
    private var _binding: FragmentExersicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var exercises: List<Exercise>

    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ExercisesViewModel by activityViewModels( factoryProducer = { factory } )
//    @Inject lateinit var exercisesManager: ExerciseManager
    @Inject lateinit var exercisesManager : ExerciseManager //= ExerciseManager(requireContext())

    private val exerciseAdapter: ExercisesRecyclerAdapter by lazy { ExercisesRecyclerAdapter(object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            showExerciseDescription(position)
        }

    }) }

    override fun onAttach(context: Context) {
        //(requireActivity().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        exercisesManager = ExerciseManager(requireContext())
//        viewModel = ExercisesViewModel(exerciseManager = exercisesManager, remoteDatabaseRepository = ExercisesRemoteRepository())
//        viewModel.selectedCategory = Category.LOSE_WEIGHT
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
        binding.addExercises.setOnClickListener {
            addExercisesToCurrentUser()
        }

        viewModel.savingState.observe(viewLifecycleOwner) {
            handleSaveResult(it)
        }
    }

    private fun handleSaveResult(result: RemoteResource) {
//        when (result) {
//            is RemoteResource.OnConflict -> showOnConflict(result.message)
//            is RemoteResource.Success<*> -> {
//                binding.addExercises.setImageDrawable()
//                binding.addExercises.setOnClickListener(null)
//                Toast.makeText(requireContext(), getString(R.string.success_exercises_added), Toast.LENGTH_LONG).show()
//            }
//            is RemoteResource.Error -> {
//                Toast.makeText(requireContext(), getString(R.string.error_saving_exercises), Toast.LENGTH_LONG).show()
//            }
//            is RemoteResource.Loading -> {
//                if (result.inProcess) {
//                    hideMainUI(View.GONE)
//                    binding.animationView.visibility = View.VISIBLE
//                }
//                else {
//                    binding.animationView.visibility = View.GONE
//                }
//            }
//        }
    }

    private fun showOnConflict(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.addExercises.visibility = View.GONE
    }

    private fun addExercisesToCurrentUser() {
        viewModel.saveUserProgram(
            UserProgramUI(
                name = viewModel.selectedCategory.nameResourceId.toString(),
                exercises = exercisesManager.getDatabaseExercises(viewModel.selectedCategory),
                icon = exercisesManager.getProgramIcon(viewModel.selectedCategory)
            ))
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
        exercises = exercisesManager.getExercises(viewModel.selectedCategory)
        Log.i("kelo", "exercise = $exercises")
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

//    override fun onItemClick(position: Int) {
//        showExerciseDescription(position)
//    }

}