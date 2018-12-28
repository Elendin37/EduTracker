package com.example.tomr.edutracker_1;

public class Fach {
    private int id;
    private String title;
    private Float zielzeit;
    private Float istzeit;

    public Fach(){};
    public Fach(String thetitle, Float thezielzeit, Float theistzeit)
    {  super();
        title=thetitle;
        zielzeit=thezielzeit;
        istzeit=theistzeit;
    }
    public String toString(){
/*        return "'\n'["+id+", "+title+", "+zielzeit+", "+istzeit+"]";
      Von Christoph geändert, damit nach dem Namen gesucht werden kann, sollte jemand von euch
      den kompletten String brauchen, dann bitte bei mir melden und ich überlege mir was.
*/
        return title;
    }
    public String getTitle(){
        return title;
    }
    public Float getZielzeit(){
        return zielzeit;
    }
    public Float getIstzeit(){
        return istzeit;
    }
    public int getId(){
        return id;
    }
    public void setId(int theid){
        id=theid;
    }
    public void setTitle(String thetitle){
        title=thetitle;
    }
    public void setZielzeit(Float thezielzeit){
        zielzeit=thezielzeit;
    }
    public void setIstzeit(Float theistzeit){
        istzeit=theistzeit;
    }
}
