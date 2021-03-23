package com.example.met06_grupo08.Model;
import com.example.met06_grupo08.R;

import java.util.ArrayList;

/**
 * Model of Day for the calendar
 */
public class Day {
    private String day;
    private ArrayList<Pill> pills;
    private static int src_redpill = R.drawable.redpill;
    private static int src_bluepill = R.drawable.bluepill;

    public Day(String day){
        this.day=day;
        pills = new ArrayList<Pill>();
    }

    public String getDay() {
        return day;
    }
    public ArrayList<Pill> getPills(){
        return pills;
    }
    public Pill getPill(int position){
        return pills.get(position);
    }
    public Pill getPillByName(String name){
        for (Pill p : pills){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public boolean isPillCreated(String name){
        for (Pill p : pills){
            if(p.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the status of pills buttom
     * @return buttom status
     */
    public boolean somePlanned(){
        if (pills.size() == 0){
            return false;
        }else if ( pills.size() == 2) {
            return (pills.get(0).getPillSelection() || pills.get(1).getPillSelection());
        }else{
            return pills.get(0).getPillSelection();
        }
    }

    /**
     * Create pill
     * @param name
     * @param date
     * @param pillSelected type of pill
     * @param pillTaken
     */
    public void addPill(String name,String date, boolean pillSelected, boolean pillTaken){
        if (name.equals("BluePill")){
            pills.add(new Pill(name,src_bluepill,date,pillSelected,pillTaken));

        }else if (name.equals("RedPill")){
            pills.add(new Pill(name,src_redpill,date,pillSelected,pillTaken));
        }
    }
    public void removePills(){
        this.pills = null;
    }
    public void removePill(String name){
        pills.remove(getPillByName(name));
    }
}