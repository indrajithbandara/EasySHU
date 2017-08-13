package com.hzastudio.easyshu.support.data_bean;

import com.google.gson.annotations.SerializedName;

public class TimeYearAndSeason {

    @SerializedName("year")
    private String TermYear;
    @SerializedName("season")
    private String TermSeason;

    public String getTermYear() {
        return TermYear;
    }

    public void setTermYear(String termYear) {
        TermYear = termYear;
    }

    public String getTermSeason() {
        return TermSeason;
    }

    public void setTermSeason(String termSeason) {
        TermSeason = termSeason;
    }
}
