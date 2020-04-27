package wordguesser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordGuesserTests {
  private WordGuesser wordGuesser;
  private SpellCheckerService spellCheckerService;

  @BeforeEach
  void init() {
    spellCheckerService = mock(SpellCheckerService.class);
    when(spellCheckerService.checkSpelling(anyString())).thenReturn(true);

    wordGuesser = new WordGuesser(spellCheckerService);
  }

  @Test
  void canary() {
    assertTrue(true);
  }

  @Test
  void getScoreForEmptyString() {
    assertEquals(0, wordGuesser.getScore("", ""));
  }

  @Test
  void getScoreForOneCorrectVowel() {
    assertEquals(1, wordGuesser.getScore("egg", "e"));
  }

  @Test
  void getScoreForOneCorrectConsonant() {
    assertEquals(2, wordGuesser.getScore("monkey", "m"));
  }

  @Test
  void getScoreForCorrectlySpelledUserGuess() {
    assertEquals(3, wordGuesser.getScore("monkey", "mop"));
  }

  @Test
  void getScoreForIncorrectlySpelledUserGuess() {
    when(spellCheckerService.checkSpelling(anyString())).thenReturn(false);

    assertEquals(0, wordGuesser.getScore("monkey", "kom"));
  }

  @Test
  void getScoreForDuplicateWordInUserGuess() {
    assertEquals(4, wordGuesser.getScore("monkey", "mooter"));
  }

  @Test
  void scrambleWordDoesNotEqualOriginal() {
    assertNotEquals("monkey", wordGuesser.shuffle("monkey"));
  }

  @Test
  void getScoreWhenNetworkError() {
    when(spellCheckerService.checkSpelling(anyString())).thenThrow(new RuntimeException("Unable to check spelling"));

    var exception = assertThrows(RuntimeException.class,
            () -> wordGuesser.getScore("monkey", "moo"));

    assertEquals("Unable to check spelling", exception.getMessage());
  }
}
