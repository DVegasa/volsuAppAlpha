package io.github.dvegasa.volsuapplicationalpha.pojos

/**
 * Created by Ed Khalturin @DVegasa
 */
data class SubjectRating(
    var name: String,
    var rates: MutableList<Int>,
    var userRate: Int,
    var ekzamen: String = ""
)