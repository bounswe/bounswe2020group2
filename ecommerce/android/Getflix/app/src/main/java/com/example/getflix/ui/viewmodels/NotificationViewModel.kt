package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ListModel
import com.example.getflix.models.NotificationModel

class NotificationViewModel : ViewModel() {

    private val _notificationList = MutableLiveData<MutableList<NotificationModel>>()
    val notificationList: LiveData<MutableList<NotificationModel>>
        get() = _notificationList

    fun addList(notificationmodel: NotificationModel) {
        if (_notificationList.value != null) {
            val notifications = _notificationList.value
            notifications?.add(notificationmodel)
            _notificationList.value = notifications
        } else {
            val notifications = arrayListOf<NotificationModel>()
            notifications.add(notificationmodel)
            _notificationList.value = notifications
        }

    }
}