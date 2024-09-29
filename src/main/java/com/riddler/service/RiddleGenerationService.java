package com.riddler.service;

import org.springframework.stereotype.Service;
import java.io
.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject
;



@Service
public class RiddleGenerationService {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent"; // Replace with the actual API endpoint
    private static final String API_KEY = "NAIzaSyD9WxQizmSKgfUlcS_X8RSt7ANdTyY4-_oN"; // Replace with your Gemini API key
    public static void main1(String[] args) throws IOException {
        // Create request body
        String prompt = "Provide a riddle and its answer in JSON format with the following structure:\n\n{ \"riddle\": \"THE RIDDLE\", \"answer\": \"THE ANSWER\" }";
        
JsonObject requestBody = new JsonObject();
        requestBody.addProperty("prompt", prompt);
        requestBody.addProperty("max_tokens", 100); // Adjust as needed
        requestBody.addProperty("temperature", 0.7); // Adjust as needed
        requestBody.addProperty("top_p", 1);
        requestBody.addProperty("n", 1);
        requestBody.add("stop", null);
        // Create HTTP connection
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);
        // Send request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        // Read response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try
 (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                // Parse JSON response
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                String riddleJson = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
                JsonObject riddleObject = gson.fromJson(riddleJson, JsonObject.class);
                System.out.println("Riddle: " + riddleObject.get("riddle").getAsString());
                System.out.println("Answer: " + riddleObject.get("answer").getAsString());
            }
        } else {
            System.out.println("Request failed with status code: " + responseCode);
        }
    }
}
