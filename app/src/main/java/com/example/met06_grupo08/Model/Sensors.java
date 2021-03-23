package com.example.met06_grupo08.Model;

/**
 * Model implements the 3 sensors available
 * Temperature sensor
 * Presence sensor
 * Servo motor
 */
public class Sensors {
    String temperature;
    String precense;
    int tapPosition;

    public Sensors(String temperature,String precense, int tapPosition){
        this.temperature = temperature;
        this.precense = precense;
        this.tapPosition = tapPosition;
    }
    public Sensors(){

    }

    public String getTemperature() {
        return temperature;
    }

    public String getPrecense() {
        return precense;
    }

    public int getTapPosition() {
        return tapPosition;
    }

    public void setTapPosition(int tapPosition) {
        this.tapPosition = tapPosition;
    }
}
