package com.miko.managetask.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

//Set up for Dark and Light theme.
@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            background = background_background_dark,
            primary = primary_dark,
            secondary = secondary_dark,
            tertiary = tertiary_dark,
            onBackground = background_secondary_dark,

        )
    } else {
        lightColorScheme(
            background = background_background_light,
            primary = primary_light,
            secondary = secondary_light,
            tertiary = tertiary_light,
            onBackground = background_secondary_light
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
