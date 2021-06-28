package com.jrodriguezva.mifilmo.utils

import android.text.TextUtils
import android.util.Patterns
import com.jrodriguezva.mifilmo.R
import javax.inject.Inject

class ValidatorUtil @Inject constructor(private val resourceProvider: ResourceProvider) {

    companion object {
        private const val PASSWORD_MIN_LENGTH = 6
        private const val MIN_LENGTH_NAME = 2
        private const val MAX_LENGTH_NAME = 15
    }

    fun validateEmail(email: String): String? {
        return when {
            TextUtils.isEmpty(email) -> resourceProvider.getString(R.string.required)
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> resourceProvider.getString(R.string.error_email_format)
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            TextUtils.isEmpty(password) -> resourceProvider.getString(R.string.required)
            password.length < PASSWORD_MIN_LENGTH ->
                resourceProvider.getString(R.string.error_password_length)
            else -> null
        }
    }

    fun validateName(name: String): String? {
        return when {
            TextUtils.isEmpty(name) -> resourceProvider.getString(R.string.required)
            name.length < MIN_LENGTH_NAME -> resourceProvider.getString(R.string.error_min_name_length)
            name.length > MAX_LENGTH_NAME -> resourceProvider.getString(R.string.error_max_name_length)
            else -> null
        }
    }

}