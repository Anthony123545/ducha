package com.supervision.huaruApi;

public class GetZbzbList {

    private String dwmc;

    private Integer isBase;

    private Integer isBz;

    private String data;

    @Override
    public String toString() {
        return "GetZbzbList{" +
                "dwmc='" + dwmc + '\'' +
                ", isBase=" + isBase +
                ", isBz=" + isBz +
                ", data='" + data + '\'' +
                '}';
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public Integer getIsBase() {
        return isBase;
    }

    public void setIsBase(Integer isBase) {
        this.isBase = isBase;
    }

    public Integer getIsBz() {
        return isBz;
    }

    public void setIsBz(Integer isBz) {
        this.isBz = isBz;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
