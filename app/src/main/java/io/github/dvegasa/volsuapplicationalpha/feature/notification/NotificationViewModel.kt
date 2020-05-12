package io.github.dvegasa.volsuapplicationalpha.feature.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.feature.notification.data_processing.NotificationsRepo
import kotlin.random.Random

class NotificationViewModel : ViewModel() {
    private val notifRepo = NotificationsRepo()

    val shortNotifications = notifRepo.fakeNotifications()
}
