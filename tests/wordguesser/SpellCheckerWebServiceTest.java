package wordguesser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

public class SpellCheckerWebServiceTest {
  private SpellCheckerService spellCheckerWebServices;

  @BeforeEach
  void init() { spellCheckerWebServices = new SpellCheckerAgileServices(); }

  @Test
  void checkSpellingForCorrectWord() {
    assertTrue(spellCheckerWebServices.checkSpelling("monkey"));
  }

  @Test
  void checkSpellingForIncorrectWord() {
    assertFalse(spellCheckerWebServices.checkSpelling("oenmky"));
  }

  @Test
  void checkSpellingWithNetworkError() throws Exception {
    var spy = Mockito.spy(new SpellCheckerAgileServices());

    doThrow(new IOException("Unable to check spelling")).when(spy).getDataFromURL(anyString());

    var exception = assertThrows(RuntimeException.class, () -> spy.checkSpelling("monkey"));

    assertEquals("Unable to check spelling", exception.getMessage());
  }
}
