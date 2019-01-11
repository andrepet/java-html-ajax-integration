package se.academy.javahtmlajaxintegration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesApiMessage {
    private String date;
    Map<String, Double> rates;
    private String base;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return "RatesApiMessage{" +
                "date='" + date + '\'' +
                ", rates=" + rates +
                ", base='" + base + '\'' +
                '}';
    }
}
