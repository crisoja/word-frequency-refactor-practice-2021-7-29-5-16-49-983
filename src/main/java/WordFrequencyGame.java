import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String WHITE_SPACES = "\\s+";
    public static final String CALCULATE_ERROR = "Calculate Error";
    public static final String NEW_LINE = "\n";
    public static final String BLANK_SPACE = " ";

    public String getResult(String sentence) {
        try {
            List<WordInfo> wordInfos = calculateWordFrequency(sentence);
            sortWords(wordInfos);
            return getWordWithCount(wordInfos);
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private String getWordWithCount(List<WordInfo> wordInfos) {
        StringJoiner allWordsWithCount = new StringJoiner(NEW_LINE);
        wordInfos.forEach(wordInfo -> {
            allWordsWithCount.add(getWordAndCount(wordInfo));
        });
        return allWordsWithCount.toString();
    }

    private String getWordAndCount(WordInfo wordInfo) {
        return wordInfo.getWord() + BLANK_SPACE + wordInfo.getWordCount();
    }

    private void sortWords(List<WordInfo> wordInfo) {
        wordInfo.sort((wordInfo1, wordInfo2) -> wordInfo2.getWordCount() - wordInfo1.getWordCount());
    }

    private List<WordInfo> calculateWordFrequency(String sentence) {
        List<String> wordInfo = Arrays.asList(sentence.split(WHITE_SPACES));
        return wordInfo.stream()
                .distinct()
                .map(word -> new WordInfo(word, Collections.frequency(wordInfo, word)))
                .sorted((word1, word2) -> word2.getWordCount() - word1.getWordCount())
                .collect(Collectors.toList());
    }
}