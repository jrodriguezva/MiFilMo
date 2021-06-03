package com.jrodriguezva.mifilmo.framework.network

import com.google.firebase.auth.FirebaseUser
import com.jrodriguezva.mifilmo.domain.model.User

fun FirebaseUser.toDomain(): User = User(
    photoUrl = photoUrl.toString(),
    name = displayName,
    email = email,
    providerId = providerId,
    uid = uid,
)