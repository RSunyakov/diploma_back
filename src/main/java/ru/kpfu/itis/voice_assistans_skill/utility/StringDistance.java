package ru.kpfu.itis.voice_assistans_skill.utility;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class StringDistance {
    public static double findSimilarity(String x, String y) {
        LuceneMorphology morphology = null;
        try {
            morphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String resultX = x.replaceAll("\\p{Punct}", "").toLowerCase(Locale.ROOT);
        String resultY = y.replaceAll("\\p{Punct}", "").toLowerCase(Locale.ROOT);

        StringTokenizer tokenizer = new StringTokenizer(resultX);
        List<String> stringsX = new ArrayList<>();
        List<String> stringsY = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            stringsX.add(morphology.getNormalForms(tokenizer.nextToken()).get(0));
        }
        tokenizer = new StringTokenizer(resultY);
        while (tokenizer.hasMoreTokens()) {
            stringsY.add(morphology.getNormalForms(tokenizer.nextToken()).get(0));
        }
        x = String.join(" ", stringsX);
        y = String.join(" ", stringsY);

        double maxLength = Double.max(x.length(), y.length());

        if (maxLength > 0) {
            return (maxLength - new LevenshteinDistance().apply(x, y)) / maxLength;
        }
        return 1.0;
    }
}
