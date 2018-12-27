package com.example.tomr.edutracker_1;

public class Lerneinheit {
    private int id;
    private String fach, wochentag, start, ende, pause, lerndauer, notizen, anhang;

    public Lerneinheit() {};

    public Lerneinheit(String i_fach, String i_wochentag, String i_start,
                       String i_ende, String i_pause, String i_lerndauer, String i_notizen, String i_anhang) {
        super();
        fach = i_fach; wochentag=i_wochentag; start=i_start;
        ende=i_ende; pause=i_pause; lerndauer=i_lerndauer; notizen=i_notizen; anhang=i_anhang;
    }

    public String toString(){
        return "'\n'Lerneinheit=[" + id + ", " + fach + ", " + wochentag + ", " + start + ", " +
                ende + ", " + pause + ", " + lerndauer + ", " + notizen + ", " + anhang +"]";
    }

    public int getId(){
        return id;
    }
    public String getFach(){
        return fach;
    }
    public String getWochentag(){
        return wochentag;
    }
    public String getStart(){
        return start;
    }
    public String getEnde(){
        return ende;
    }
    public String getPause(){
        return pause;
    }
    public String getLerndauer(){
        return lerndauer;
    }
    public String getNotizen(){
        return notizen;
    }
    public String getAnhang(){
        return anhang;
    }

    public void setId(int i_id){
        id = i_id;
    }
    public void setFach(String i_fach){
        fach = i_fach;
    }
    public void setWochentag(String i_wochentag){
        wochentag = i_wochentag;
    }
    public void setStart(String i_startzeit){
        start = i_startzeit;
    }
    public void setEnde(String i_endzeit){
        ende = i_endzeit;
    }
    public void setPause(String i_pausendauer){
        pause = i_pausendauer;
    }
    public void setLerndauer(String i_gesamtdauer){
        lerndauer = i_gesamtdauer;
    }
    public void setNotizen(String i_notizen){
        notizen = i_notizen;
    }
    public void setAnhang(String i_anhang){
        anhang = i_anhang;
    }
}