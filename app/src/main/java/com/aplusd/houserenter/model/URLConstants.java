package com.aplusd.houserenter.model;

/**
 * @author Azamat Dzhonov
 * @date 07.03.2018
 */

public class URLConstants {

    // MAIN SERVER

    public static String SERVER_URL_1 = "http://ec2-35-180-120-205.eu-west-3.compute.amazonaws.com:3000/";
    public static String SERVER_URL_2 = "http://game.springtale.ru:3000/";
    public static String SERVER_URL = SERVER_URL_2;

    public static String GET_AVAILABLE_LEASES = SERVER_URL + "getlodgings";
    public static String GET_LODGINGINFO = SERVER_URL + "gethouseInfo";
    public static String START_CONTRACT = SERVER_URL + "startcontract";

    public static String ADD_LEASE = SERVER_URL + "addlease";
    public static String SET_MAIN_IMG = SERVER_URL + "updatemainimg";
    public static String PUBLISH_LODGING = SERVER_URL + "publishlodging";
    public static String ADD_LODGING_IMG = SERVER_URL + "addlodgingimg";

    public static String GET_HOUSE_TYPES = SERVER_URL + "gethousetypes";
    public static String GET_LEASES_WITH_LOCATION = SERVER_URL + "getleaseswithlocation";


    public static String UPLOAD_IMG = SERVER_URL + "uploadimg";
    public static String GET_USER_INFO = SERVER_URL + "getuserinfo";

    public static String AUTH = SERVER_URL + "auth";
    public static String LOGIN = SERVER_URL + "login";
    public static String REGISTRATION = SERVER_URL + "registration";
    public static String CHECK_PUBLIC_KEY = SERVER_URL + "checkpublickey";


    public static String GET_YOUR_OFFERINGS = SERVER_URL +  "getofferings";
    public static String GET_YOUR_TRIPS = SERVER_URL + "gettrips";


    public static String CANCEL_TRIP = SERVER_URL + "canceltripone";
    public static String UPDATE_CONTRACT_STATUS = SERVER_URL + "updatecontractstatus";
    public static String FINISH_CONTRACT = SERVER_URL + "finishcontract";


    public static String GET_CHAT = SERVER_URL + "getchat";
    public static String SEND_MSG = SERVER_URL + "sendmsg";

    public static String CHECK_LODGING_AVAILABILITY = SERVER_URL + "checkishouseavailable";


    public static String URL_GET_ETHER_CURRENCY = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=BTC,USD,EUR";
    public static String URL_CREATE_WALLET = "https://www.myetherwallet.com/#generate-wallet";
    public static String URL_ROPSTEN_NETWORK_API = "https://api-ropsten.etherscan.io/api";

    public static void switchServer()
    {
        if(SERVER_URL.equals(SERVER_URL_1))
            SERVER_URL = SERVER_URL_2;
        else
            SERVER_URL = SERVER_URL_1;

        GET_AVAILABLE_LEASES = SERVER_URL + "getlodgings";
        GET_LODGINGINFO = SERVER_URL + "gethouseInfo";
        START_CONTRACT = SERVER_URL + "startcontract";
        ADD_LEASE = SERVER_URL + "addlease";
        SET_MAIN_IMG = SERVER_URL + "updatemainimg";
        PUBLISH_LODGING = SERVER_URL + "publishlodging";
        ADD_LODGING_IMG = SERVER_URL + "addlodgingimg";
        GET_HOUSE_TYPES = SERVER_URL + "gethousetypes";
        GET_LEASES_WITH_LOCATION = SERVER_URL + "getleaseswithlocation";
        UPLOAD_IMG = SERVER_URL + "uploadimg";
        GET_USER_INFO = SERVER_URL + "getuserinfo";
        AUTH = SERVER_URL + "auth";
        LOGIN = SERVER_URL + "login";
        REGISTRATION = SERVER_URL + "registration";
        CHECK_PUBLIC_KEY = SERVER_URL + "checkpublickey";
        GET_YOUR_OFFERINGS = SERVER_URL +  "getofferings";
        GET_YOUR_TRIPS = SERVER_URL + "gettrips";
        CANCEL_TRIP = SERVER_URL + "canceltripone";
        UPDATE_CONTRACT_STATUS = SERVER_URL + "updatecontractstatus";
        FINISH_CONTRACT = SERVER_URL + "finishcontract";
        GET_CHAT = SERVER_URL + "getchat";
        SEND_MSG = SERVER_URL + "sendmsg";
        CHECK_LODGING_AVAILABILITY = SERVER_URL + "checkishouseavailable";
        URL_GET_ETHER_CURRENCY = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=BTC,USD,EUR";
        URL_CREATE_WALLET = "https://www.myetherwallet.com/#generate-wallet";
        URL_ROPSTEN_NETWORK_API = "https://api-ropsten.etherscan.io/api";

    }
}
