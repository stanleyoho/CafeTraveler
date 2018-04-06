package com.app.jlin.cafetraveler.Utils;

/**
 * 根據站代碼比對，回傳boolean
 */

/**
 * 1~24                         文湖線(Brown)
 * 3.25~36.43.71.91             新店線(Green)
 * 4.42.62~80                   板南線(Blue)
 * 5.35.41~61                   淡水線(Red)
 * 34.37~40.45.73.81~96         中和新蘆線(Orange)
 */

public class CheckLineUtils {
    private static boolean isRedLine, isGreenLine, isBlueLine, isOrangeLine, isBrownLine;

    public static void whichLine(int mrtNumber) {
        if (mrtNumber <= 24) isBrownLine = true;
        if (mrtNumber == 3 || (mrtNumber >= 25 && mrtNumber <= 36) || mrtNumber == 43 || mrtNumber == 71 || mrtNumber == 91)
            isGreenLine = true;
        if (mrtNumber == 4 || mrtNumber == 42 || (mrtNumber >= 62 && mrtNumber <= 80))
            isBlueLine = true;
        if (mrtNumber == 5 || mrtNumber == 35 || (mrtNumber >= 41 && mrtNumber <= 61))
            isRedLine = true;
        if (mrtNumber == 34 || (mrtNumber >= 37 && mrtNumber <= 40) || mrtNumber == 45 || mrtNumber == 73 || (mrtNumber >= 81 && mrtNumber <= 96))
            isOrangeLine = true;
    }

    public static boolean isRedLine() {
        return isRedLine;
    }

    public static boolean isGreenLine() {
        return isGreenLine;
    }

    public static boolean isBlueLine() {
        return isBlueLine;
    }

    public static boolean isOrangeLine() {
        return isOrangeLine;
    }

    public static boolean isBrownLine() {
        return isBrownLine;
    }

    public static void setLineFalse() {
        isRedLine = false;
        isBlueLine = false;
        isGreenLine = false;
        isBrownLine = false;
        isOrangeLine = false;
    }
}
