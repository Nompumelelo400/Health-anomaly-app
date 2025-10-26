package com.example.healthalert;

public class patient {
    private String name;
    private int heartRate;
    private int oxygen;
    private double temperature;
    public String bloodPressure;

    public patient(String name, int heartRate, int oxygen, double temperature, String bloodPressure) {
        this.name = name;
        this.heartRate = heartRate;
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
    }

    public String getName() { return name; }
    public int getHeartRate() { return heartRate; }
    public int getOxygen() { return oxygen; }
    public double getTemperature() {
        return temperature;
    }

    public String getbloodPressure() {
        return bloodPressure;
    }
}
