package com.model;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class RestResponse {
    private TSUser user;
    private int result;

    private List<RestResponse> otherResults;

    public RestResponse(TSUser user, int result, List<RestResponse> otherResults) {
        this.user = user;
        this.result = result;
        this.otherResults = otherResults;
    }

    public TSUser getUser() {
        return user;
    }

    public void setUser(TSUser user) {
        this.user = user;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<RestResponse> getOtherResults() {
        return otherResults;
    }

    public void setOtherResults(List<RestResponse> otherResults) {
        this.otherResults = otherResults;
    }
}
