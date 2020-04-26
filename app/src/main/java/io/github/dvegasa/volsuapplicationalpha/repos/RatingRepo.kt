package io.github.dvegasa.volsuapplicationalpha.repos

import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectRating

/**
 * Created by Ed Khalturin @DVegasa
 */
class RatingRepo {

    fun getTestDataset(): MutableLiveData<ArrayList<SubjectRating>> {
        val zz = "Зачёт с оценкой"
        val z = "Зачёт"
        val e = "Экзамен"

        val r0 = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val r1 = arrayListOf(44, 42, 37, 43, 32, 33, 55, 33, 42, 42, 23, 21, 55, 20, 55)
        val r2 = arrayListOf(66, 56, 52, 52, 77, 57, 56, 44, 0, 75, 87, 44, 45, 75, 86)
        val r3 = arrayListOf(76, 45, 66, 75, 85, 33, 120, 76, 44, 77, 98, 68, 57, 77, 66)
        val r4 = arrayListOf(82, 21, 45, 85, 38, 21, 44, 75, 56, 88, 10, 35, 45, 88, 43)
        val r5 = arrayListOf(56, 66, 53, 44, 55, 85, 45, 56, 56, 62, 65, 55, 33, 65, 59)
        val r6 = arrayListOf(14, 21, 21, 14, 14, 14, 14, 14, 7, 7, 7, 7, 7, 0, 0)
        val r7 = arrayListOf(75, 55, 77, 74, 78, 96, 56, 46, 67, 67, 75, 44, 0, 0, 0)

        return MutableLiveData<ArrayList<SubjectRating>>().default(
            arrayListOf(
                SubjectRating("Алгебра и теория чисел", r1, r1[0], zz),
                SubjectRating("Геометрия и топология", r2, r2[0], e),
                SubjectRating("Иностранный язык", r3, r3[0], z),
                SubjectRating("Информатика и программирование", r4, r4[0], e),
                SubjectRating("Математический анализ", r5, r5[0], zz),
                SubjectRating("Прикладая физическая культура", r6, r6[0], z),
                SubjectRating("История", r0, r0[0], z),
                SubjectRating("Русский язык и культура речи", r7, r7[0], z)
            )
        )
    }
}