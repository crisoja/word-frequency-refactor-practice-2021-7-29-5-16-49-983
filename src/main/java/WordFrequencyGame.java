import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String BLANK_SPACE = "\\s+";
    private Map<String, Integer> wordsCounts;

    public String getResult(String sentence) {
        try {
            List<WordInfo> wordInfoList = calculateWordFrequency(sentence);
            sortWords(wordInfoList);
            return getWordWithCount(wordInfoList);
        } catch (Exception e) {
            return "Calculate Error";
        }
    }

    private String getWordWithCount(List<WordInfo> wordInfos) {
        StringJoiner allWordsWithCount = new StringJoiner("\n");
        wordInfos.forEach(wordInfo -> {
            allWordsWithCount.add(wordInfo.getWord() + " " + wordInfo.getWordCount());
        });
        return allWordsWithCount.toString();
    }

    private void sortWords(List<WordInfo> wordInfo) {
        wordInfo.sort((wordInfo1, wordInfo2) -> wordInfo2.getWordCount() - wordInfo1.getWordCount());
    }

    private List<WordInfo> calculateWordFrequency(String sentence) {
        List<String> words = Arrays.asList(sentence.split(BLANK_SPACE));
        wordsCounts = words.parallelStream().
                collect(Collectors.toConcurrentMap(
                        word -> word, word -> 1, Integer::sum));
        return wordsCounts.entrySet()
                .stream()
                .map(wordInfo -> new WordInfo(wordInfo.getKey(), wordInfo.getValue()))
                .collect(Collectors.toList());
    }
}