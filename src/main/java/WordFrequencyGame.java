import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String BLANK_SPACE = "\\s+";
    private static final String ADD_SPACE_AND_ONE = " 1";
    private Map<String, Integer> wordsCounts;

    public String getResult(String sentence){


        if (sentence.split(BLANK_SPACE).length==1) {
            return sentence + ADD_SPACE_AND_ONE;
        } else {

            
            try {

                List<WordInfo> wordInfoList = calculateWordFrequency(sentence);

                sortWords(wordInfoList);

                StringJoiner joiner = getStringJoiner(wordInfoList);
                return joiner.toString();
            } catch (Exception e) {


                return "Calculate Error";
            }
        }
    }

    private StringJoiner getStringJoiner(List<WordInfo> wordInfoList) {
        StringJoiner joiner = new StringJoiner("\n");
        for (WordInfo w : wordInfoList) {
            String s = w.getValue() + " " +w.getWordCount();
            joiner.add(s);
        }
        return joiner;
    }

    private void sortWords(List<WordInfo> wordInfoList) {
        wordInfoList.sort((word1, word2) -> word2.getWordCount() - word1.getWordCount());
    }


    private List<WordInfo> calculateWordFrequency(String sentence) {
        List<String> words = Arrays.asList(sentence.split(BLANK_SPACE));

        wordsCounts = words.parallelStream().
                collect(Collectors.toConcurrentMap(
                        w -> w, w -> 1, Integer::sum));

            return wordsCounts.entrySet()
                         .stream()
                         .map(input -> new WordInfo(input.getKey(),input.getValue()))
                         .collect(Collectors.toList());
        }



}
