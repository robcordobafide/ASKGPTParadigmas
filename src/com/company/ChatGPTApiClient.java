package com.company;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTApiClient {
    private static final String API_KEY = "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";
    private static final String MODEL_NAME = "text-davinci-002";

    public String askQuestion(String question) throws IOException {
        URL url = new URL("https://api.openai.com/v1/engines/" + MODEL_NAME + "/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);

        JsonObject prompt = new JsonObject();
        prompt.addProperty("prompt", question);
        prompt.addProperty("max_tokens", 50);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = prompt.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JsonObject responseJson = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
            JsonArray choicesArray = responseJson.getAsJsonArray("choices");
            JsonObject firstChoice = choicesArray.get(0).getAsJsonObject();
            return firstChoice.get("text").getAsString();
        } else {
            System.out.println("Error: " + responseCode);
            return "Error en la API";
        }
    }
}
