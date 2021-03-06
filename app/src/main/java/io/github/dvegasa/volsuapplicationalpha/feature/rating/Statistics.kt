package io.github.dvegasa.volsuapplicationalpha.feature.rating

import com.github.mikephil.charting.data.BarEntry
import io.github.dvegasa.volsuapplicationalpha.utils.Emoji

/**
 * Created by Ed Khalturin @DVegasa
 */
class Statistics {
    companion object {
        fun userSum(dataset: ArrayList<SubjectRating>): Int {
            var sum = 0
            for (subjectRich in dataset) {
                sum += subjectRich.userRate
            }
            return sum
        }

        fun userRating(dataset: ArrayList<SubjectRating>): Int {
            val userSum =
                userSum(
                    dataset
                )
            val count = dataset[0].rates.size
            val othersSum = Array(count) { 0 }

            for (subj in dataset) {
                for (i in subj.rates.indices) {
                    othersSum[i] += subj.rates[i]
                }
            }
            othersSum.sortDescending()
            for (i in othersSum.indices) {
                if (othersSum[i] == userSum) {
                    return i + 1
                }
            }
            return -1
        }

        fun getEmojiForSubject(subj: SubjectRating): String {
            if (subj.userRate == 0) {
                return ""
            }

            val (iFirst, iSecond) = getTercelBordersIndicies(
                subj
            )
            if (subj.userRate >= getSufficientRate(
                    subj
                )
            ) {
                return Emoji.check
            }
            val userRating =
                getSubjRating(
                    subj
                )
            return when (userRating) {
                in iFirst..999 -> Emoji.exclamation
                in iSecond..iFirst -> Emoji.novice
                in 0..iSecond -> Emoji.thumbUp
                else -> Emoji.question
            }
        }

        fun getMedian(subj: SubjectRating): Int {
            if (subj.rates.isEmpty()) {
                return 0
            }
            val sorted = subj.rates.sorted()
            return if (sorted.size % 2 != 0) {
                sorted[sorted.size / 2]
            } else {
                (sorted[sorted.size / 2] + sorted[sorted.size / (2 + 1)]) / 2
            }
        }

        private fun getTercelBordersIndicies(subj: SubjectRating): Array<Int> {
            val iFirst = subj.rates.size - (subj.rates.size / 3)
            val iSecond = subj.rates.size - (subj.rates.size / 3 * 2)
            return arrayOf(iFirst, iSecond)
        }

        private fun getSufficientRate(subj: SubjectRating): Int {
            return when {
                subj.ekzamen.contains("Экзамен") -> 91
                subj.ekzamen.contains("Зачет с оценкой") -> 91
                subj.ekzamen.contains("Зачет") -> 60
                else -> 999
            }
        }

        fun getChartData(subj: SubjectRating): ArrayList<BarEntry> {
            val result = ArrayList<BarEntry>()
            val temp = Array(20) { 0 }

            for (rate in subj.rates) {
                temp[getColumnByRate(
                    rate
                )]++
            }

            for (i in temp.indices) {
                result.add(BarEntry(i.toFloat(), temp[i].toFloat()))
            }
            return result
        }

        fun getColumnByRate(rate: Int) = when (rate) {
            in Integer.MIN_VALUE..5 -> 0
            in 6..10 -> 1
            in 11..15 -> 2
            in 16..20 -> 3
            in 21..25 -> 4
            in 26..30 -> 5
            in 31..35 -> 6
            in 36..40 -> 7
            in 41..45 -> 8
            in 46..50 -> 9
            in 51..55 -> 10
            in 56..59 -> 11
            in 60..65 -> 12
            in 66..70 -> 13
            in 71..75 -> 14
            in 76..80 -> 15
            in 81..85 -> 16
            in 86..90 -> 17
            in 91..95 -> 18
            in 96..Integer.MAX_VALUE -> 19
            else -> 0
        }

        fun getSubjRating(subj: SubjectRating): Int {
            val sorted = subj.rates.sortedDescending()
            for (i in sorted.indices) {
                if (sorted[i] == subj.userRate) {
                    return i + 1
                }
            }
            return -1
        }
    }
}