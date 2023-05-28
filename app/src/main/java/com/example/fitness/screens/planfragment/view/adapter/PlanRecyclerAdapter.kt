package com.example.fitness.screens.planfragment.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.data.remotesource.model.UserProgram

class PlanRecyclerAdapter(private val onDeleteClickListener: OnAdapterItemClickListener) : RecyclerView.Adapter<PlanRecyclerAdapter.ProgramViewHolder>() {

    private var programs: List<UserProgram> = listOf()
    private var selectedProgramId: Int? = null

    fun updateData(programs: List<UserProgram>, selectedProgramId: Int? = null) {
        this.programs = programs
        this.selectedProgramId = selectedProgramId
        previousSelectedProgramBox?.let {
            it.isChecked = false
        }
        _selectedProgram = null
        notifyDataSetChanged()
    }

    private var previousSelectedProgramBox: CheckBox? = null
    private var _selectedProgram : UserProgram? = null
    val selectedProgram : UserProgram?
        get() = _selectedProgram

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.raw_plan, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programs[position]
        holder.bind(program)
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    inner class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val programNameTextView: TextView = itemView.findViewById(R.id.categoryName)
        private val programImage: ImageView = itemView.findViewById(R.id.categoryImage)
        private val programCheckBox: CheckBox = itemView.findViewById(R.id.rawCategoryCheckBox)
        private val programDeleteIcon: ImageView = itemView.findViewById(R.id.rawDeleteIcon)

        fun bind(program: UserProgram) {
            programDeleteIcon.setOnClickListener {
                onDeleteClickListener.execute(program)
            }
            if (program.id == selectedProgramId) {
                previousSelectedProgramBox?.let {
                    it.isChecked = false
                }
                programCheckBox.isChecked = true
                previousSelectedProgramBox = programCheckBox
                _selectedProgram = program
            }
            programNameTextView.text = itemView.context.getString(program.name!!.toInt())
            programImage.setImageResource(program.icon!!.toInt())
            programCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (previousSelectedProgramBox != null) {
                        previousSelectedProgramBox!!.isChecked = false
                    }
                    previousSelectedProgramBox = programCheckBox
                    programCheckBox.isChecked = true
                    _selectedProgram = program
                }
                else {
                    if (previousSelectedProgramBox != null) {
                        previousSelectedProgramBox!!.isChecked = false
                    }
                    previousSelectedProgramBox = null
                    _selectedProgram = null
                }
            }

        }
    }
}
