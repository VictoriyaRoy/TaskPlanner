package com.example.tasks.data.model

import com.example.tasks.R

enum class Category(val color: Int, val icon: Int) {
    NONE(R.color.text_secondary, R.drawable.ic_none),
    WORK(R.color.work_category, R.drawable.ic_work),
    SHOP(R.color.shopping_category, R.drawable.ic_shopping),
    SPORT(R.color.sport_category, R.drawable.ic_sport),
    HOME(R.color.home_category, R.drawable.ic_home),
    STUDY(R.color.study_category, R.drawable.ic_study),
    SOCIAL(R.color.social_category, R.drawable.ic_social),
    HEALTH(R.color.health_category, R.drawable.ic_health);

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}