package com.aplusd.houserenter.rentlodging.model;

import java.util.Date;

/**
 * @author Azamat Dzhonov
 * @date 28.05.2018
 */
public class BookedDates {

    private Date from = null;
    private Date to = null;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
