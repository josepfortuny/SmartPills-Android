package com.example.met06_grupo08.Model;

/**
 * User model
 */
public class User {
    private String name;
    private String email;
    private String id;
    private String hardware;

    public User () {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String id,String name, String email ,String hardware){
        this.email=email;
        this.name=name;
        this.id = id;
        this.hardware = hardware;

    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getId() { return id; }
    public String getHardware(){ return hardware;}
    public void setId(String id) { this.id = id;}
}
