package com.mycompany.mavenproject1;

public class ResearchData {
    private final int tauMin;
    private final int tauMax;
    private final int deltaTau;
    private final int Tmin;
    private final int Tmax;
    private final int deltaT;
    private final String material;
    
    public ResearchData(int tauMin, int tauMax, int deltaTau, int Tmin, int Tmax, int deltaT, String material) {
        this.tauMin = tauMin;
        this.tauMax = tauMax;
        this.deltaTau = deltaTau;
        this.Tmin = Tmin;
        this.Tmax = Tmax;
        this.deltaT = deltaT;
        this.material = material;
    }

    public int getTauMin() { return tauMin; }
    public int getTauMax() { return tauMax; }
    public int getDeltaTau() { return deltaTau; }
    public int getTmin() { return Tmin; }
    public int getTmax() { return Tmax; }
    public int getDeltaT() { return deltaT; }
    public String getMaterial() { return material; }
}