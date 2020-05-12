package io.github.dvegasa.volsuapplicationalpha.feature.notification.pojos

import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Time

/**
 * Created by Ed Khalturin @DVegasa
 */
data class ShortNotification(
    val from: String,
    val briefContent: String,
    val imgId: Long, /* TODO: Поле нужно заменить на ссылку изображения, во время настоящей разработки */
    val time: String, /* TODO: Поле нужно заменить на millis время, во время настоящей разработки */
    val smallCircleId: SmallCircle = SmallCircle.NONE,
    val isMarked: Boolean = true
)

enum class SmallCircle {
    NONE, MONEY, REPLY
}