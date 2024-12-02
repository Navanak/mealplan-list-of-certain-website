

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class WebPage {
    private String url;
    private String content;
    private int frequency;

    public WebPage(String url, String content) {
        this.url = url;
        this.content = content;
        this.frequency = 0;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}

class BoyerMooreAlgorithm {
    public int search(String text, String pattern) {
        if (pattern.isEmpty() || text.isEmpty()) {
            return 0;
        }

        int[] badCharTable = buildBadCharTable(pattern);
        int occurrences = 0;
        int m = pattern.length();
        int n = text.length();
        int shift = 0;

        while (shift <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                occurrences++;
                shift += (shift + m < n) ? m - badCharTable[text.charAt(shift + m)] : 1;
            } else {
                shift += Math.max(1, j - badCharTable[text.charAt(shift + j)]);
            }
        }

        return occurrences;
    }

    private int[] buildBadCharTable(String pattern) {
        final int ALPHABET_SIZE = 256;
        int[] badCharTable = new int[ALPHABET_SIZE];
        int m = pattern.length();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            badCharTable[i] = -1;
        }

        for (int i = 0; i < m; i++) {
            badCharTable[pattern.charAt(i)] = i;
        }

        return badCharTable;
    }
}

