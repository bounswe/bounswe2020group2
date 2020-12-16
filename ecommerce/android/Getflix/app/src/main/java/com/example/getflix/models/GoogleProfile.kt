package com.example.getflix.models

import android.net.Uri

data class GoogleProfile(
        val personName: String?,
        val personGivenName: String?,
        val personFamilyName: String?,
        val personEmail: String?,
        val personId: String?,
        val personPhoto: Uri?
)

