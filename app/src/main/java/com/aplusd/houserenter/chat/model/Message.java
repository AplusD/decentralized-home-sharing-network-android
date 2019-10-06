package com.aplusd.houserenter.chat.model;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */

import android.content.Context;

import com.aplusd.houserenter.model.Constants;
import com.aplusd.houserenter.user.SPHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.intentservice.chatui.models.ChatMessage;

import static co.intentservice.chatui.models.ChatMessage.Type.RECEIVED;
import static co.intentservice.chatui.models.ChatMessage.Type.SENT;

public class Message {

    @SerializedName("msgId")
    @Expose
    private Integer msgId;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("fromId")
    @Expose
    private Integer fromId;
    @SerializedName("msgDate")
    @Expose
    private String msgDate;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public ChatMessage getMessage(Context context)
    {
        try {

            if(getFromId() == SPHelper.getUserId(context))
                return new ChatMessage(getMsg(), Constants.parseDateFromServer.parse(getMsgDate()).getTime(), SENT );
            else
                return new ChatMessage(getMsg(), Constants.parseDateFromServer.parse(getMsgDate()).getTime(), RECEIVED );
        }
        catch (Exception ex)
        {
            return  null;
        }
    }
}