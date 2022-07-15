package com.example.tasks.data.model

import com.example.tasks.R
import java.util.*

enum class Category (val color: Int, val icon: Int) {
    NONE(R.color.light_gray, R.drawable.ic_none),
    WORK(R.color.work_category, R.drawable.ic_work),
    SPORT(R.color.sport_category, R.drawable.ic_sport),
    HOME(R.color.home_category, R.drawable.ic_home),
    STUDY(R.color.study_category, R.drawable.ic_study),
    SOCIAL(R.color.social_category, R.drawable.ic_social);

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}