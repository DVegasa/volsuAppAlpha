package io.github.dvegasa.volsuapplicationalpha.feature.notification.data_processing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.feature.notification.pojos.ShortNotification
import io.github.dvegasa.volsuapplicationalpha.feature.notification.pojos.SmallCircle

/**
 * Created by Ed Khalturin @DVegasa
 */
class NotificationsRepo {
    fun fakeNotifications(): LiveData<List<ShortNotification>> = MutableLiveData(
        listOf(
            ShortNotification(
                "Михалкова А.В.",
                "Вам назначена сверх государственная социальная стипендия в размере 4000 руб$",
                0L,
                "3 ч",
                SmallCircle.MONEY
            ),
            ShortNotification(
                "Деканат ИМИТ",
                "Вам оказана материальная поддержка в размере 1000 руб. Подробности можно$",
                1L,
                "5 ч",
                SmallCircle.MONEY
            ),
            ShortNotification(
                "Елохин Л.У.",
                "Отзыв о работе \"Средневековье\"",
                2L,
                "2 апр",
                SmallCircle.REPLY,
                isMarked = false
            ),
            ShortNotification(
                "Тех. поддержка",
                "Приложение обновилось! Нажми на уведомление и узнай подробности",
                3L,
                "14 мар",
                isMarked = false
            )
        )
    )
}