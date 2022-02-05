package spring.jsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Component
public class ReadJson {
    public JSONObject readJsonFromUrl(String link) throws JSONException {

        try (InputStream input = new URL(link).openStream()) {
            BufferedReader re = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
            String text = Read(re);
            StringBuilder stringBuilder = new StringBuilder(text);
            stringBuilder.append("}");
            stringBuilder.insert(0,"{ \"data\":");
            JSONObject json = new JSONObject(stringBuilder.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String Read(Reader re) throws IOException {
        StringBuilder str = new StringBuilder();
        int temp;
        while (true) {
            temp = re.read();
            if (temp == -1)
                break;
            str.append((char) temp);
        }

        return str.toString();
    }
}
