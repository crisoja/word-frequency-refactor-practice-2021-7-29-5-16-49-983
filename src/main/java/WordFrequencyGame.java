import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String BLANK_SPACE = "\\s+";
    private static final String ADD_SPACE_AND_ONE = " 1";
    private Map<String, Integer> wordsCounts;

    public String getResult(String sentence) {
        try {
            List<WordInfo> wordInfoList = calculateWordFrequency(sentence);
            sortWords(wordInfoList);
            return getWordWithCount(wordInfoList).toString();
        } catch (Exception e) {
            return "Calculate Error";
        }
    }

    private StringJoiner getWordWithCount(List<WordInfo> wordInfoList) {
        StringJoiner allWordsWithCount = new StringJoiner("\n");
        for (WordInfo word : wordInfoList) {
            String wordWithCount = word.getValue() + " " + word.getWordCount();
            allWordsWithCount.add(wordWithCount);
        }
        return allWordsWithCount;
    }

    private void sortWords(List<WordInfo> wordInfoList) {
        wordInfoList.sort((word1, word2) -> word2.getWordCount() - word1.getWordCount());
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
