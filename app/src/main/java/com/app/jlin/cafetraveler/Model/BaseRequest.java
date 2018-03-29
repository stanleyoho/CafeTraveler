package com.app.jlin.cafetraveler.Model;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by wayne on 2018/1/31.
 */

public class BaseRequest implements Serializable {

    private RequestHeader requestHeader;
    private JsonObject requestBody;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(JsonObject requestBody) {
        this.requestBody = requestBody;
    }

    public class RequestHeader implements Serializable {
        private int userID;
        private int currencyID;
        private String usercode;

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public int getCurrencyID() {
            return currencyID;
        }

        public void setCurrencyID(int currencyID) {
            this.currencyID = currencyID;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }
    }
}
