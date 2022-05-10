package com.example.sqlite_demo;

public class Mountain {
    @Override
    public String toString() {
        return "Mountain{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", meter=" + meter +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }

    public Mountain(String name, String location, int meter) {
        this.name = name;
        this.location = location;
        this.meter = meter;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getMeter() {
        return meter;
    }

    private String name;
    private String location;
    private int meter;
}
