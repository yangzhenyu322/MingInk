package com.mingink.article.domain.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gorse.gorse4j.Gorse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Gorse Client 类
 * @Author: ZenSheep
 * @Date: 2024/2/29 21:17
 */
public class GorseClient extends Gorse {
    private final String endpoint;
    private final String apiKey;

    public GorseClient(String endpoint, String apiKey) {
        super(endpoint, apiKey);
        this.endpoint = endpoint;
        this.apiKey = apiKey;
    }

    public List<JSONObject> getLatest() throws IOException {
        return List.of((JSONObject[])this.request("GET", this.endpoint + "/api/latest", (Object)null, JSONObject[].class));
    }

    public List<JSONObject> getLatest(String category) throws IOException {
        return List.of((JSONObject[])this.request("GET", this.endpoint + "/api/latest/" + category, (Object)null, JSONObject[].class));
    }

    public List<JSONObject> getPopular() throws IOException {
        return List.of((JSONObject[])this.request("GET", this.endpoint + "/api/popular", (Object)null, JSONObject[].class));
    }

    public List<JSONObject> getPopular(String category) throws IOException {
        return List.of((JSONObject[])this.request("GET", this.endpoint + "/api/popular/" + category, (Object)null, JSONObject[].class));
    }

    public List<String> getRecommend(String userId, String category) throws IOException {
        return List.of((String[])this.request("GET", this.endpoint + "/api/recommend/" + userId + "/" + category, (Object)null, String[].class));
    }

    public List<JSONObject> getSimilar(String itemId) throws IOException {
        return List.of((JSONObject[])this.request("GET", this.endpoint + "/api/item/" + itemId + "/neighbors", (Object)null, JSONObject[].class));
    }

    public List<JSONObject> getSimilar(String itemId, String category) throws IOException {
        return List.of((JSONObject[])this.request("GET", this.endpoint + "/api/item/" + itemId + "/neighbors/" + category, (Object)null, JSONObject[].class));
    }

    private <Request, Response> Response request(String method, String url, Request request, Class<Response> responseClass) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("X-API-Key", this.apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        ObjectMapper mapper = new ObjectMapper();
        if (request != null) {
            connection.setDoOutput(true);
            String requestBody = mapper.writeValueAsString(request);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(requestBody.getBytes());
            outputStream.close();
        }

        InputStream inputStream = connection.getInputStream();
        return mapper.readValue(inputStream, responseClass);
    }
}
