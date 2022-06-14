package ru.kpfu.itis.voice_assistans_skill.utility;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Locale;

public class StringDistance {
    public static double findSimilarity(String x, String y) {
        LevenshteinDistance distance = new LevenshteinDistance();
        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            return (maxLength - distance.apply(x.toLowerCase(Locale.ROOT), y.toLowerCase(Locale.ROOT))) / maxLength;
        }
        return 1.0;
    }
}
