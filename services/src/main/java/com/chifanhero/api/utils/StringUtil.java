package com.chifanhero.api.utils;

/**
 * Created by shiyan on 7/17/17.
 */
public class StringUtil {

    public static boolean containsHanScript(String s) {
        return s.codePoints().anyMatch(
                codepoint ->
                        Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);

    }

    public static String removeSpaceBetweenChineseCharacters(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        int[] toRemove = new int[name.length()];
        for (int i = 0; i < name.length() - 1; i++) {
            if (isHanCharacter(name, i)) {
                if (name.charAt(i + 1) == ' ') {
                    toRemove[i + 1] = 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder(name);
        int delta = 0;
        for (int i = 0; i < toRemove.length; i++) {
            if (toRemove[i] == 1) {
                int index = i - delta;
                sb.deleteCharAt(index);
                delta++;
            }
        }
        return sb.toString();
    }

    private static boolean isHanCharacter(String s, int pos) {
        return Character.UnicodeScript.of(s.codePointAt(pos)) == Character.UnicodeScript.HAN;
    }
}
