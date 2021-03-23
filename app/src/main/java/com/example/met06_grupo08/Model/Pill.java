package com.example.met06_grupo08.Model;

/**
 * Pill model
 */
public class Pill {
    private boolean pillSelected;
    private boolean pillTaken;
    private String date;
    private String name;
    private int src ;
    public Pill(String name,int src,String date, boolean pillSelected, boolean pillTaken){
        this.pillSelected = pillSelected;
        this.pillTaken = pillTaken;
        this.src = src;
        this.date = date;
        this.name = name;

    }
    public Pill(){

    }

    public boolean getPillTaken(){
        return pillTaken;
    }
    public void setPillTaken(boolean taken){
        pillTaken = taken;
    }
    public boolean getPillSelection(){ return pillSelected; }
    public void setPillSelection(boolean pillSelected){
        this.pillSelected = pillSelected;
    }
    public int getSrc(){
        return src;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getName(){
        return name;
    }
}
