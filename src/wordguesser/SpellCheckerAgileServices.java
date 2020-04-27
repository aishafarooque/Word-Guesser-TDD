package wordguesser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpellCheckerAgileServices implements SpellCheckerService {
  @Override
  public boolean checkSpelling(String word) {
    try {
      return Boolean.parseBoolean(getDataFromURL(word));
    } catch (IOException e) {
      throw new RuntimeException("Unable to check spelling");
    }
  }

  protected String getDataFromURL(String word) throws IOException {
    URL url = new URL(String.format("http://agile.cs.uh.edu/spell?check=%s", word));
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
    BufferedReader resultReader = new BufferedReader(new InputStreamReader(dataInputStream));

    String result = resultReader.readLine();

    return result;
  }
}
