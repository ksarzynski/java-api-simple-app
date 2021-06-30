package api;

import com.google.gson.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Retriever {

    ArrayList<String> countries = new ArrayList<>();
    ArrayList<City> cities = new ArrayList<>();
    int amount;

    public JsonObject getResponse(String category, String params) throws UnirestException {

        String host = "https://wft-geo-db.p.rapidapi.com/v1/geo/" + category + "/" + params;
        String x_rapidapi_host = "wft-geo-db.p.rapidapi.com";
        String x_rapidapi_key = "54d5e48c33msh3aab8e6e9724cdep1b08e4jsn93d1536ce8b1";

        HttpResponse<JsonNode> response = Unirest.get(host)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();

        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());

        return je.getAsJsonObject();
    }


    public void getCitiesWithPopulation(String countryId, int population) throws UnirestException, InterruptedException {

        cities.clear();

        JsonObject jo;

        JsonArray ja;

        if(population >= 0){

            jo = getResponse("cities", "?countryIds=" + countryId + "&minPopulation=" +
                    population);
            ja = jo.getAsJsonArray("data");
            System.out.println("cities" + "?countryIds=" + countryId + "&minPopulation=" +
                    population);
        }
        else{

            population *= -1;

            jo = getResponse("cities", "?countryIds=" + countryId + "&maxPopulation=" +
                    (population));
            ja = jo.getAsJsonArray("data");
            System.out.println("cities" + "?countryIds=" + countryId + "&maxPopulation=" +
                    population);
        }

        for(JsonElement element : ja){

            cities.add(new City(element.getAsJsonObject().get("city").getAsString(),
                    element.getAsJsonObject().get("id").getAsString(), countryId));
        }

        amount = jo.getAsJsonObject("metadata").get("totalCount").getAsInt();

        TimeUnit.SECONDS.sleep(2);
    }

    public void getCountriesWithCurrency(String currency) throws UnirestException, InterruptedException {

        countries.clear();

        JsonObject jo;

        jo = getResponse("countries", "?currencyCode=" + currency);

        amount = Integer.parseInt(jo.getAsJsonObject("metadata").get("totalCount").toString());

        TimeUnit.SECONDS.sleep(2);
    }

    public int getAmount() {
        return amount;
    }
}
