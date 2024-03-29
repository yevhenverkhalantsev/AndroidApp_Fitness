package com.example.fitness.screens.exercises.exercisePerforming.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.databinding.FragmentPerformingExercisesBinding
import com.example.fitness.screens.exercises.exercisePerforming.model.PerformingExercise
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import com.example.fitness.utils.ExerciseManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PerformingExercisesFragment : DaggerFragment() {
    private var _binding : FragmentPerformingExercisesBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ExercisesViewModel by activityViewModels( factoryProducer = { factory } )
    @Inject lateinit var exercisesManager: ExerciseManager
    private val performingExercises: List<PerformingExercise>
        by lazy { exercisesManager.getPerformingExercises(viewModel.selectedCategory) }
    private var currentExerciseId = 0
    private var isStopped = true
    private var isExercise = true


    private val spendTime: CountDownTimer by lazy { object : CountDownTimer(9000000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (!isStopped) {
                binding.exerciseProgressBar.progress = binding.exerciseProgressBar.progress + 1
                binding.timeText.text = (binding.timeText.text.toString().toInt() - 1).toString()
                if (binding.exerciseProgressBar.progress == binding.exerciseProgressBar.max) {
                    if (isExercise) startCurrentRest()
                    else {
                        currentExerciseId++
                        showCurrentExercise()
                    }
                }
            }
        }

        override fun onFinish() {
        } } }

    private fun startCurrentRest() {
        binding.exerciseProgressBar.max = performingExercises[currentExerciseId].time.restTime
        binding.timeText.text = performingExercises[currentExerciseId].time.restTime.toString()
        binding.exerciseProgressBar.progress = 0
        binding.mainLayout.setBackgroundColor(resources.getColor(R.color.yellow, null))
        binding.exerciseName.text = getString(R.string.rest)
        isExercise = false
    }

    override fun onStart() {
        super.onStart()
        spendTime.start()
    }

    override fun onStop() {
        super.onStop()
        spendTime.cancel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPerformingExercisesBinding.bind(view)

        initListeners()
        showCurrentExercise()


    }


    private fun showCurrentExercise() {
        if (currentExerciseId == performingExercises.size) {
            showFinishedScreen()
        }
        else {
            binding.mainLayout.setBackgroundColor(resources.getColor(R.color.blue, null))
            binding.amountFromAmount.text =
                getString(R.string.amount_from_amount, currentExerciseId+1, performingExercises.size)
            binding.exerciseName.text =
                performingExercises[currentExerciseId].exercise.exercise_name
            binding.exerciseImage.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                performingExercises[currentExerciseId].exercise.exercise_image))
            binding.exerciseProgressBar.max =
                performingExercises[currentExerciseId].time.performingTime
            binding.exerciseProgressBar.progress = 0
            binding.timeText.text =
                performingExercises[currentExerciseId].time.performingTime.toString()
            isExercise = true
        }
    }

    private fun initListeners() {
        binding.playexercise.setOnClickListener {
            isStopped = if (isStopped) {
                binding.playexercise.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.pause))
                false
            } else {
                binding.playexercise.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.play))
                true
            }

        }

        binding.repeatexercise.setOnClickListener {
            findNavController().navigate(R.id.action_performingExercisesFragment_self)
        }
        binding.nextexercise.setOnClickListener {
            currentExerciseId++
            isStopped = true
            showCurrentExercise()
        }

    }

    private fun showFinishedScreen() {
        binding.exerciseName.text = getString(R.string.exercise_finished)
        binding.timeText.visibility = View.INVISIBLE
        spendTime.cancel()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_performing_exercises, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}