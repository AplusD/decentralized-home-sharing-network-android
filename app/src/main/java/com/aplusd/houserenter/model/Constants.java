package com.aplusd.houserenter.model;

import java.text.SimpleDateFormat;

/**
 * @author Azamat Dzhonov
 * @date 18.04.2018
 */

public interface Constants {

    String SIMPLE_DATE_FORMAT = "dd.MM.yyyy";
    String SIMPLE_DATA_FORMAT_FROM_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    String SIMPLE_DATE_FORMAT_TO_SERVER = "yyyy-MM-dd";


    SimpleDateFormat parseDateFromServer = new SimpleDateFormat(SIMPLE_DATA_FORMAT_FROM_SERVER);
    SimpleDateFormat parseDateToShow = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

}
