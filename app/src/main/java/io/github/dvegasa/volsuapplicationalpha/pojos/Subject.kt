package io.github.dvegasa.volsuapplicationalpha.pojos

/**
 * Created by Ed Khalturin @DVegasa
 */
data class Subject (
    val audi: String,
    val title: String,
    val subtitle: String,
    val style: SubjectStyle
)

enum class SubjectStyle {
    NORMAL, CANCELLED, ACCENT, INACTIVE
}