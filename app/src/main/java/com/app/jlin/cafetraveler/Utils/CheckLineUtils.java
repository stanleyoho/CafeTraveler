package com.app.jlin.cafetraveler.Utils;

/**
 * 根據站代碼比對，回傳boolean
 */

/**
 * LineID
 * BR      文湖線(Brown)
 * G        新店線(Green)
 * BL      板南線(Blue)
 * R        淡水線(Red)
 * O        中和新蘆線(Orange)
 */

public class CheckLineUtils {
    private static boolean isRedLine, isGreenLine, isBlueLine, isOrangeLine, isBrownLine;

    public static void whichLine(String mrtLineId) {
        if (mrtLineId.equals("BR")) isBrownLine = true;
        if (mrtLineId.equals("G")) isGreenLine = true;
        if (mrtLineId.equals("BL")) isBlueLine = true;
        if (mrtLineId.equals("R")) isRedLine = true;
        if (mrtLineId.equals("O")) isOrangeLine = true;
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
