package com.evgenii.bugsgame.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.domain.model.Difficulty
import com.evgenii.bugsgame.domain.model.Gender
import com.evgenii.bugsgame.domain.model.Zodiac

@Composable
fun genderLabel(gender: Gender): String = when (gender) {
    Gender.MAN -> stringResource(R.string.gender_man)
    Gender.WOMAN -> stringResource(R.string.gender_woman)
}

@Composable
fun difficultyLabel(difficulty: Difficulty): String = when (difficulty) {
    Difficulty.EASY -> stringResource(R.string.difficulty_easy)
    Difficulty.MEDIUM -> stringResource(R.string.difficulty_medium)
    Difficulty.HARD -> stringResource(R.string.difficulty_hard)
}

@Composable
fun zodiacLabel(zodiac: Zodiac): String = when (zodiac) {
    Zodiac.CAPRICORN -> stringResource(R.string.zodiac_capricorn)
    Zodiac.AQUARIUS -> stringResource(R.string.zodiac_aquarius)
    Zodiac.PISCES -> stringResource(R.string.zodiac_pisces)
    Zodiac.ARIES -> stringResource(R.string.zodiac_aries)
    Zodiac.TAURUS -> stringResource(R.string.zodiac_taurus)
    Zodiac.GEMINI -> stringResource(R.string.zodiac_gemini)
    Zodiac.CANCER -> stringResource(R.string.zodiac_cancer)
    Zodiac.LEO -> stringResource(R.string.zodiac_leo)
    Zodiac.VIRGO -> stringResource(R.string.zodiac_virgo)
    Zodiac.LIBRA -> stringResource(R.string.zodiac_libra)
    Zodiac.SCORPIO -> stringResource(R.string.zodiac_scorpio)
    Zodiac.SAGITTARIUS -> stringResource(R.string.zodiac_sagittarius)
}

@DrawableRes
fun zodiacRes(zodiac: Zodiac): Int = when (zodiac) {
    Zodiac.CAPRICORN -> R.drawable.zodiac_capricorn
    Zodiac.AQUARIUS -> R.drawable.zodiac_aquarius
    Zodiac.PISCES -> R.drawable.zodiac_pisces
    Zodiac.ARIES -> R.drawable.zodiac_aries
    Zodiac.TAURUS -> R.drawable.zodiac_taurus
    Zodiac.GEMINI -> R.drawable.zodiac_gemini
    Zodiac.CANCER -> R.drawable.zodiac_cancer
    Zodiac.LEO -> R.drawable.zodiac_leo
    Zodiac.VIRGO -> R.drawable.zodiac_virgo
    Zodiac.LIBRA -> R.drawable.zodiac_libra
    Zodiac.SCORPIO -> R.drawable.zodiac_scorpio
    Zodiac.SAGITTARIUS -> R.drawable.zodiac_sagittarius
}