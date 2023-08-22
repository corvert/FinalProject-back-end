package com.example.FinalProject.model;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class AlphaVantageAPI {
    private String apiKey = "BVBZ6P8VKTUD16IE";

    public void AlphaVantageClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public double getStockPrice(String symbol) {
        HttpResponse<JsonNode> response = Unirest.get("https://www.alphavantage.co/query")
                .queryString("function", "GLOBAL_QUOTE")
                .queryString("symbol", symbol)
                .queryString("apikey", apiKey)
                .asJson();

        if (response.isSuccess()) {
            JsonNode json = response.getBody();
            return json.getObject().getJSONObject("Global Quote").getDouble("05. price");
        } else {
            throw new RuntimeException("Request failed with status code: " + response.getStatus());
        }
    }
}
