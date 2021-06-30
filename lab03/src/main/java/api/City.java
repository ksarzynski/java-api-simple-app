package api;

public class City {

    private String name;
    private String cityId;
    private String country;
    private int population;

    City(String name, String cityId, String country){

        this.name = name;
        this.cityId = cityId;
        this.country = country;
        this.population = -1;
    }

    public String getName() {
        return name;
    }

    public String getCityId(){
        return cityId;
    }

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String toString(){

        return "city: " + this.name + " id: " + this.cityId;
    }
}
