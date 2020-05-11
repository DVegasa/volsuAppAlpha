package io.github.dvegasa.volsuapplicationalpha.feature.rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RatingViewModel : ViewModel() {

    val chosenSemestr = MutableLiveData(2).apply {
        observeForever {
            // TODO Загрузить рейтинг для данного семестра
        }
    }

    val userSum = MutableLiveData<Int>()

    val userRating = MutableLiveData<Int>()

    val subjectRiches = RatingRepo()
        .getTestDataset().apply {
        observeForever {
            userSum.value = Statistics.userSum(value!!)
            userRating.value = Statistics.userRating(value!!)
        }
    }
}
