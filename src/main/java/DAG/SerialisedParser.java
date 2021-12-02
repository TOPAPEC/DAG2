package DAG;

import java.util.ArrayList;
import java.util.List;

public class SerialisedParser {

    /**
     * Strips string from braces and splits by commas where not enclosed in brackets.
     *
     * @param str initial string.
     * @return split string array.
     */
    public static String[] cutString(String str) {
        str = bracesStrip(str);
        List<String> list = new ArrayList<String>();
        int bracketCounter = 0;
        int lastCut = -1;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '{') {
                ++bracketCounter;
            } else if (str.charAt(i) == '}') {
                --bracketCounter;
            } else if (str.charAt(i) == ',' && bracketCounter == 0) {
                list.add(str.substring(lastCut + 1, i));
                lastCut = i;
            }
        }
        list.add(str.substring(lastCut + 1));
        return (String[]) list.toArray(new String[0]);
    }

    /**
     * Strips string from first and last symbol.
     *
     * @param str initial string.
     * @return stripped string.
     */
    public static String bracesStrip(String str) {
        return str.substring(1, str.length() - 1);
    }

    /**
     * Splits by sharps where not enclosed in brackets.
     *
     * @param str initial string.
     * @return split string array.
     */
    public static String[] splitOuter(String str) {
        List<String> list = new ArrayList<String>();
        int bracketCounter = 0;
        int lastCut = -1;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '{') {
                ++bracketCounter;
            } else if (str.charAt(i) == '}') {
                --bracketCounter;
            } else if (str.charAt(i) == '#' && bracketCounter == 0) {
                list.add(str.substring(lastCut + 1, i));
                lastCut = i;
            }
        }
        list.add(str.substring(lastCut + 1));
        return list.toArray(new String[0]);
    }
}