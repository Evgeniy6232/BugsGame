package com.evgenii.bugsgame.domain.usecase

import com.evgenii.bugsgame.domain.model.Zodiac
import java.time.LocalDate
import java.time.MonthDay

class GetZodiacSignUseCase {

    operator fun invoke(date: LocalDate): Zodiac {
        val monthDay = MonthDay.of(date.month, date.dayOfMonth)

        val boundaries = listOf(
            MonthDay.of(1, 20) to Zodiac.AQUARIUS,
            MonthDay.of(2, 19) to Zodiac.PISCES,
            MonthDay.of(3, 21) to Zodiac.ARIES,
            MonthDay.of(4, 20) to Zodiac.TAURUS,
            MonthDay.of(5, 21) to Zodiac.GEMINI,
            MonthDay.of(6, 21) to Zodiac.CANCER,
            MonthDay.of(7, 23) to Zodiac.LEO,
            MonthDay.of(8, 23) to Zodiac.VIRGO,
            MonthDay.of(9, 23) to Zodiac.LIBRA,
            MonthDay.of(10, 23) to Zodiac.SCORPIO,
            MonthDay.of(11, 22) to Zodiac.SAGITTARIUS,
            MonthDay.of(12, 22) to Zodiac.CAPRICORN
        )

        return boundaries.lastOrNull { (start, _) -> monthDay >= start } ?.second ?: Zodiac.CAPRICORN
    }
}