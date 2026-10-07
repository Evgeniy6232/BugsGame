package com.evgenii.bugsgame.ui.main

import androidx.annotation.StringRes
import com.evgenii.bugsgame.R

enum class MainTab(@StringRes val titleRes: Int) {
    FORM(R.string.tab_form),
    RULES(R.string.tab_rules),
    AUTHORS(R.string.tab_authors),
    SETTINGS(R.string.tab_settings)
}