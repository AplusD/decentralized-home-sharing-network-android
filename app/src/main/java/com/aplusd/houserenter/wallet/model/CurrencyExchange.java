package com.aplusd.houserenter.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Azamat Dzhonov
 * @date 16.05.2018
 */
public class CurrencyExchange {

    @SerializedName("BTC")
    @Expose
    private Double bTC;
    @SerializedName("USD")
    @Expose
    private Double uSD;
    @SerializedName("EUR")
    @Expose
    private Double eUR;

    public Double getBTC() {
        return bTC;
    }

    public void setBTC(Double bTC) {
        this.bTC = bTC;
    }

    public Double getUSD() {
        return uSD;
    }

    public void setUSD(Double uSD) {
        this.uSD = uSD;
    }

    public Double getEUR() {
        return eUR;
    }

    public void setEUR(Double eUR) {
        this.eUR = eUR;
    }

    public String getEther(int dollar)
    {
        return String.valueOf(dollar / getUSD());
    }

    public String getDollar(double ether)
    {
        return String.valueOf(ether * getUSD() / 1);
    }

    public String getString()
    {
        return "USD: " + uSD  + "\n" + "EUR: " + eUR + "\n" + "BTC: " + bTC;
    }

}