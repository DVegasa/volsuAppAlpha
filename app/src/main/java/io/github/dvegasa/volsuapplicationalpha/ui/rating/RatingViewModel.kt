package io.github.dvegasa.volsuapplicationalpha.ui.rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.utils.default
import io.github.dvegasa.volsuapplicationalpha.repos.RatingRepo
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.Statistics

class RatingViewModel : ViewModel() {

    val chosenSemestr = MutableLiveData<Int>().default(2).apply {
        observeForever {
            // TODO Загрузить рейтинг для данного семестра
        }
    }

    val userSum = MutableLiveData<Int>()

    val userRating = MutableLiveData<Int>()

    val subjectRiches = RatingRepo().getTestDataset().apply {
        observeForever {
            userSum.value = Statistics.userSum(value!!)
            userRating.value = Statistics.userRating(value!!)
        }
    }
}
