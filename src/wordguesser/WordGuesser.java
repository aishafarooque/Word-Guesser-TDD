package wordguesser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class WordGuesser {
  private SpellCheckerService spellCheckerService;
  List<String> vowels = List.of("a", "e", "i", "o", "u");
  
  public WordGuesser (SpellCheckerService aSpellCheckerService) { spellCheckerService = aSpellCheckerService; }

  public String shuffle(String unscrambledWord) {
    List<String> letters = Stream.of(unscrambledWord.split("")).collect(toList());
    Collections.shuffle(letters);
    return String.join("", letters);
  }

  public int getScore(String unscrambledWord, String userGuess) {
    List<String> unscrambledWordList = List.of(unscrambledWord.toLowerCase().split(""));
    List<String> userGuessList = List.of(userGuess.toLowerCase().split(""));

    if (userGuess.length() == 0 || unscrambledWord.length() == 0 || spellCheckerService.checkSpelling(unscrambledWord) == false) {
      return 0;
    }

    int listIndex = 0, score = 0;
    for (String userGuessedCharacters : userGuessList) {
      if (unscrambledWordList.get(listIndex++).equals(userGuessedCharacters)) {
        if (vowels.contains(userGuessedCharacters)) score += 1;
        else score += 2;
      }
    }

    return score;
  }
}
