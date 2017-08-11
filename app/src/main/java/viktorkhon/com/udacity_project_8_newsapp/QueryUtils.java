package viktorkhon.com.udacity_project_8_newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static viktorkhon.com.udacity_project_8_newsapp.MainActivity.LOG_TAG;

/**
 * Created by Viktor Khon on 8/10/2017.
 */

public class QueryUtils {

    private QueryUtils() {
    }

    public static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Status code is " + urlConnection.getResponseCode());
            }
        } catch (IOException exception) {
            Log.e(LOG_TAG, "IOE exception " + exception + " is thrown");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        // This lets inputStreamReader know what we want the data to be translated to
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                Charset.forName("UTF-8"));
        // BufferedReader class makes it possible to take bytes of information from the inputStreamReader
        // and combine them in larger readable texts that we can use
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // Read 1 line of text at the time and store it in a String variable
        String line = bufferedReader.readLine();
        // While there are still lines, continue this loop
        while (line != null) {
            // Use StringBuilder class to take 1 line at a time and then add new line to the
            // existing text
            stringBuilder.append(line);
            // get the next line, until run out of lines
            bufferedReader.readLine();
        }
        // return a full JSON String
        return stringBuilder.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<News> parseJson(String json) {
        // If there is no JSON being passed, quit
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding books to
        List<News> news = new ArrayList<>();

        try {
            // Main JSON object from the response
            JSONObject jsonObject = new JSONObject(json);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String title = result.getString("webTitle");
                String sectionName = result.getString("sectionName");
                String webUrl = result.getString("webUrl");
                long date = result.getLong("webPublicationDate");

                news.add(new News(title, sectionName, date, webUrl));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing json " + e);
        }
        return news;
    }

    public static List<News> fetchData (String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return parseJson(jsonResponse);
    }
}
