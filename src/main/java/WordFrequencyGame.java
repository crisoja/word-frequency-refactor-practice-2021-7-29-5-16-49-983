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

                List<Input> inputList = calculateWordFrequency(sentence);

                inputList.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());

                StringJoiner joiner = new StringJoiner("\n");
                for (Input w : inputList) {
                    String s = w.getValue() + " " +w.getWordCount();
                    joiner.add(s);
                }
                return joiner.toString();
            } catch (Exception e) {


                return "Calculate Error";
            }
        }
    }



    private List<Input> calculateWordFrequency(String sentence) {
        List<String> words = Arrays.asList(sentence.split(BLANK_SPACE));

        wordsCounts = words.parallelStream().
                collect(Collectors.toConcurrentMap(
                        w -> w, w -> 1, Integer::sum));

            return wordsCounts.entrySet()
                         .stream()
                         .map(input -> new Input(input.getKey(),input.getValue()))
                         .collect(Collectors.toList());
        }



}
