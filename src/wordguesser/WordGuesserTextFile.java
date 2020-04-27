package wordguesser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class WordGuesserTextFile {
  public static void main(String[] args) throws IOException {
    var spellChecker = new SpellCheckerAgileServices();
    var wordGuesser = new WordGuesser(spellChecker);
    Scanner scanner = new Scanner(System.in);

    try {
      List<String> wordsFromTextFile = Files.lines(Paths.get("src/wordguesser/sample.txt")).collect(toList());
      Collections.shuffle(wordsFromTextFile);

      String wordToGuess = wordsFromTextFile.get(0);
      String scrambledWordToGuess = wordGuesser.shuffle(wordToGuess);
      String userInput;

      while (true) {
        System.out.println("Guess the scrambled word, hint: " + scrambledWordToGuess);
        System.out.printf("Enter your guess: ");
        userInput = scanner.nextLine();

        if (userInput.equals(wordToGuess)) {
          System.out.println("Congratulations! Your score is " + wordGuesser.getScore(wordToGuess, userInput));
          break;
        }

        System.out.println("Your score is " + wordGuesser.getScore(wordToGuess, userInput) + ". Try again!");
      }
    } catch (Exception e) {
      System.out.println("Unable to run program.");
    }
  }
}
