package com.example.fitness.planfragment.view.adapter

import com.example.fitness.data.remotesource.model.UserProgram

interface OnAdapterItemClickListener {
    fun execute(program: UserProgram)
}