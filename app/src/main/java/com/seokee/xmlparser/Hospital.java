package com.seokee.xmlparser;

public class Hospital {
    private String dutyName;
    private String dutyTime1c;
    private String dutyAddr;

    public Hospital() {
    }

    public Hospital(String dutyName, String dutyTime1c, String dutyAddr) {
        this.dutyName = dutyName;
        this.dutyTime1c = dutyTime1c;
        this.dutyAddr = dutyAddr;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getDutyTime1c() {
        return dutyTime1c;
    }

    public void setDutyTime1c(String dutyTime1c) {
        this.dutyTime1c = dutyTime1c;
    }

    public String getDutyAddr() {
        return dutyAddr;
    }

    public void setDutyAddr(String dutyAddr) {
        this.dutyAddr = dutyAddr;
    }
}
