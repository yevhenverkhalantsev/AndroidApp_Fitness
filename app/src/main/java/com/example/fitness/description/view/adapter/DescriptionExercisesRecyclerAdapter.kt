package com.example.fitness.description.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.screens.exercises.someother.model.Exercise
import com.example.fitness.utils.interfaces.OnItemClickListener

class DescriptionExercisesRecyclerAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<DescriptionExercisesRecyclerAdapter.ExerciseViewHolder>() {

    var exercises: List<Exercise> = listOf()
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exercise_image: ImageView = itemView.findViewById(R.id.exercise_image)
        val exercise_name: TextView = itemView.findViewById(R.id.exercise_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_description_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        Log.i("krembolin", "items = $exercises")
        val exercise = exercises[position]
        holder.exercise_name.text = exercise.exercise_name
        holder.exercise_image.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, exercise.exercise_image))
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(position) }

    }

}