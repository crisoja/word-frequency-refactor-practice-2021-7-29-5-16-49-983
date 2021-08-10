import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String WHITE_SPACES = "\\s+";
    public static final String CALCULATE_ERROR = "Calculate Error";
    public static final String NEW_LINE = "\n";
    public static final String BLANK_SPACE = " ";

    public String getResult(String sentence) {
        try {
            List<WordInfo> wordInfoList = calculateWordFrequency(sentence);
            sortWords(wordInfoList);
            return getWordWithCount(wordInfoList);
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
        List<String> words = Arrays.asList(sentence.split(WHITE_SPACES));
         Map<String, Integer> wordsCounts = words.parallelStream().
                collect(Collectors.toConcurrentMap(
                        word -> word, word -> 1, Integer::sum));
        return wordsCounts.entrySet()
                .stream()
                .map(wordInfo -> new WordInfo(wordInfo.getKey(), wordInfo.getValue()))
                .collect(Collectors.toList());
    }
}