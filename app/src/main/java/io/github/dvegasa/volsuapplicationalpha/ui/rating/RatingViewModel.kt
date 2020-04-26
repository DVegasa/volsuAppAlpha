package io.github.dvegasa.volsuapplicationalpha.ui.rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.repos.RatingRepo
import io.github.dvegasa.volsuapplicationalpha.utils.Statistics

class RatingViewModel : ViewModel() {

    val userSum = MutableLiveData<Int>()

    val userRating = MutableLiveData<Int>()

    val subjectRiches = RatingRepo().getTestDataset().apply {
        observeForever {
            userSum.value = Statistics.userSum(value!!)
            userRating.value = Statistics.userRating(value!!)
        }
    }
}
