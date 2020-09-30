package com.docone.metawrapper.model.umls;

public class MRConso {
    private String cui;
    private String lat;
    private String ts;
    private String lui;
    private String stt;
    private String sui;
    private String isPref;
    private String aui;
    private String saui;
    private String scui;
    private String sdui;
    private String sab;
    private String tty;
    private String code;
    private String str;
    private String srl;
    private String suppress;
    private String cvf;

    // Optional params
    private String rela;

    public MRConso() {};

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getLui() {
        return lui;
    }

    public void setLui(String lui) {
        this.lui = lui;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getSui() {
        return sui;
    }

    public void setSui(String sui) {
        this.sui = sui;
    }

    public String getIsPref() {
        return isPref;
    }

    public void setIsPref(String isPref) {
        this.isPref = isPref;
    }

    public String getAui() {
        return aui;
    }

    public void setAui(String aui) {
        this.aui = aui;
    }

    public String getSaui() {
        return saui;
    }

    public void setSaui(String saui) {
        this.saui = saui;
    }

    public String getScui() {
        return scui;
    }

    public void setScui(String scui) {
        this.scui = scui;
    }

    public String getSdui() {
        return sdui;
    }

    public void setSdui(String sdui) {
        this.sdui = sdui;
    }

    public String getSab() {
        return sab;
    }

    public void setSab(String sab) {
        this.sab = sab;
    }

    public String getTty() {
        return tty;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getSrl() {
        return srl;
    }

    public void setSrl(String srl) {
        this.srl = srl;
    }

    public String getSuppress() {
        return suppress;
    }

    public void setSuppress(String suppress) {
        this.suppress = suppress;
    }

    public String getCvf() {
        return cvf;
    }

    public void setCvf(String cvf) {
        this.cvf = cvf;
    }

    public String getRela() {
        return rela;
    }

    public void setRela(String rela) {
        this.rela = rela;
    }

    @Override
    public String toString() {
        return "MRConso{" +
                "cui='" + cui + '\'' +
                ", lat='" + lat + '\'' +
                ", ts='" + ts + '\'' +
                ", lui='" + lui + '\'' +
                ", stt='" + stt + '\'' +
                ", sui='" + sui + '\'' +
                ", isPref='" + isPref + '\'' +
                ", aui='" + aui + '\'' +
                ", saui='" + saui + '\'' +
                ", scui='" + scui + '\'' +
                ", sdui='" + sdui + '\'' +
                ", sab='" + sab + '\'' +
                ", tty='" + tty + '\'' +
                ", code='" + code + '\'' +
                ", str='" + str + '\'' +
                ", srl='" + srl + '\'' +
                ", suppress='" + suppress + '\'' +
                ", cvf='" + cvf + '\'' +
                ", rela='" + rela + '\'' +
                '}';
    }
}
