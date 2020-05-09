package io.github.dvegasa.volsuapplicationalpha.feature.rating

/**
 * Created by Ed Khalturin @DVegasa
 */
data class SubjectRating(
    var name: String,
    var rates: MutableList<Int>,
    var userRate: Int,
    var ekzamen: String = ""
)