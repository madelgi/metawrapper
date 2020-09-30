package com.docone.metawrapper.model.umls;

public class RXData {
    private String code;
    private String name;
    private String sab;
    private String rela;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSab() {
        return sab;
    }

    public void setSab(String sab) {
        this.sab = sab;
    }

    public String getRela() {
        return rela;
    }

    public void setRela(String rela) {
        this.rela = rela;
    }

    @Override
    public String toString() {
        return "RXData{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sab='" + sab + '\'' +
                ", rela='" + rela + '\'' +
                '}';
    }
}
