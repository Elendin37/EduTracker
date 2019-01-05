package com.example.tomr.edutracker_1;

public class Status {

    private int id_status, grundstein, nachteule,fruehervogel, wochenendlerner, marathonlerner;
    private int perfektewoche;

    public Status(){};
    public Status(int theIDStatus, int thegrundstein, int thenachteule,int thefruehervogel, int thewochenendlerner, int themarathonlerner, int theperfektewoche){
        super();
        id_status=theIDStatus;
        grundstein=thegrundstein;
        nachteule=thenachteule;
        fruehervogel=thefruehervogel;
        wochenendlerner=thewochenendlerner;
        marathonlerner=themarathonlerner;
        perfektewoche=theperfektewoche;
    }

    public Integer getGrundstein(){
        return grundstein;
    }
    public Integer getNachteule(){
        return nachteule;
    }
    public Integer getFrVogel(){
        return fruehervogel;
    }
    public Integer getWELerner(){
        return wochenendlerner;
    }
    public Integer getMaLerner(){
        return marathonlerner;
    }
    public Integer getPWoche(){
        return perfektewoche;
    }
    public Integer getID_Status(){
        return id_status;
    }
    public void setGrundstein(Integer thegrundstein){
        grundstein=thegrundstein;
    }
    public void setNachteule(Integer thenachteule){
        nachteule=thenachteule;
    }
    public void setFrVogel(Integer theFrVogel){
        fruehervogel=theFrVogel;
    }
    public void setWELerner(Integer theWELerner){
        wochenendlerner=theWELerner;
    }
    public void setMaLerner(Integer theMALerner){
        marathonlerner=theMALerner;
    }
    public void setPWoche(Integer thePWoche){
        perfektewoche=thePWoche;
    }

}
