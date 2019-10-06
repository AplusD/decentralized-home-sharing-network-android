package com.aplusd.houserenter.rentlodging.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Azamat Dzhonov
 * @date 13.04.2018
 */

public class Contract implements Serializable {

    private int peopleCount = 0;
    private Date fromDate = Calendar.getInstance().getTime();
    private Date toDate = Calendar.getInstance().getTime();
    private String hostMsg = "";
    private double priceWei = 0;

    private List<Date> dates = new ArrayList<>();

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getHostMsg() {
        return hostMsg;
    }

    public void setHostMsg(String hostMsg) {
        this.hostMsg = hostMsg;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public double getPriceWei() {
        return priceWei;
    }

    public void setPriceWei(double priceWei) {
        this.priceWei = priceWei;
    }
}
