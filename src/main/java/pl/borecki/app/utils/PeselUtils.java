package pl.borecki.app.utils;

import pl.borecki.app.constants.AppContstans;

import java.time.LocalDate;
import java.time.Period;

public class PeselUtils {

    public static boolean hasUserMinimalAge(String pesel) {
        LocalDate birthDate = getBirthDateFromPesel(pesel);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears() >= AppContstans.ACCOUNT_OWNER_MINIMAL_AGE;
    }

    private static LocalDate getBirthDateFromPesel(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month <= 12) {
            year += 1900;
        } else if (month <= 32) {
            year += 2000;
            month -= 20;
        } else if (month <= 52) {
            year += 2100;
            month -= 40;
        } else if (month <= 72) {
            year += 2200;
            month -= 60;
        } else if (month <= 92) {
            year += 1800;
            month -= 80;
        }

        return LocalDate.of(year, month, day);
    }

}
