package com.mycompany.mavenproject1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculation {
    public static ArrayList<ArrayList<Double>> calculate(int tauMin, int tauMax, int deltaTau,
                                int Tmin, int Tmax, int deltaT, String material) throws SQLException{
        
        double b0, b1, b2, b3, b4, b5, b6, b7, b8, Hr;
        ArrayList<ArrayList<Double>> resultList = new ArrayList<>();
        ArrayList<Double> HrMinTau = new ArrayList<>();
        ArrayList<Double> HrAverageTau = new ArrayList<>();
        ArrayList<Double> HrMaxTau = new ArrayList<>();
        ArrayList<Double> HrMinT = new ArrayList<>();
        ArrayList<Double> HrAverageT = new ArrayList<>();
        ArrayList<Double> HrMaxT = new ArrayList<>();
        
        int T, tau, averageT, averageTau;
        averageT = (Tmin + Tmax) / 2;
        averageTau = (tauMin + tauMax) / 2;
        
        Coefficients coeff = DBManager.selectByMaterial(material);
        if (coeff == null) return null;
        b0 = coeff.getB0();
        b1 = coeff.getB1();
        b2 = coeff.getB2();
        b3 = coeff.getB3();
        b4 = coeff.getB4();
        b5 = coeff.getB5();
        b6 = coeff.getB6();
        b7 = coeff.getB7();
        b8 = coeff.getB8();
        
        
        for (T = Tmin; T <= Tmax; T += deltaT){
            Hr = b0 + b1 * tauMin + b2 * T + b3 * tauMin * T + b4 * Math.pow(tauMin, 2) + b5 * Math.pow(T, 2)
            + b6 * Math.pow(tauMin, 2) * T + b7 * tauMin * Math.pow(T, 2) + b8 * Math.pow(tauMin, 2) * Math.pow(T, 2);
            HrMinTau.add(roundToTwoDecimalPlaces(Hr));
        }
        for (T = Tmin; T <= Tmax; T += deltaT){
            Hr = b0 + b1 * averageTau + b2 * T + b3 * averageTau * T + b4 * Math.pow(averageTau, 2) + b5 * Math.pow(T, 2)
            + b6 * Math.pow(averageTau, 2) * T + b7 * averageTau * Math.pow(T, 2) + b8 * Math.pow(averageTau, 2) * Math.pow(T, 2);
            HrAverageTau.add(roundToTwoDecimalPlaces(Hr));
        }
        for (T = Tmin; T <= Tmax; T += deltaT){
            Hr = b0 + b1 * tauMax + b2 * T + b3 * tauMax * T + b4 * Math.pow(tauMax, 2) + b5 * Math.pow(T, 2)
            + b6 * Math.pow(tauMax, 2) * T + b7 * tauMax * Math.pow(T, 2) + b8 * Math.pow(tauMax, 2) * Math.pow(T, 2);
            HrMaxTau.add(roundToTwoDecimalPlaces(Hr));
        }
        
        for (tau = tauMin; tau <= tauMax; tau += deltaTau){
            Hr = b0 + b1 * tau + b2 * Tmin + b3 * tau * Tmin + b4 * Math.pow(tau, 2) + b5 * Math.pow(Tmin, 2)
            + b6 * Math.pow(tau, 2) * Tmin + b7 * tau * Math.pow(Tmin, 2) + b8 * Math.pow(tau, 2) * Math.pow(Tmin, 2);
            HrMinT.add(roundToTwoDecimalPlaces(Hr));
        }
        for (tau = tauMin; tau <= tauMax; tau += deltaTau){
            Hr = b0 + b1 * tau + b2 * averageT + b3 * tau * averageT + b4 * Math.pow(tau, 2) + b5 * Math.pow(averageT, 2)
            + b6 * Math.pow(tau, 2) * averageT + b7 * tau * Math.pow(averageT, 2) + b8 * Math.pow(tau, 2) * Math.pow(averageT, 2);
            HrAverageT.add(roundToTwoDecimalPlaces(Hr));
        }
        for (tau = tauMin; tau <= tauMax; tau += deltaTau){
            Hr = b0 + b1 * tau + b2 * Tmax + b3 * tau * Tmax + b4 * Math.pow(tau, 2) + b5 * Math.pow(Tmax, 2)
            + b6 * Math.pow(tau, 2) * Tmax + b7 * tau * Math.pow(Tmax, 2) + b8 * Math.pow(tau, 2) * Math.pow(Tmax, 2);
            HrMaxT.add(roundToTwoDecimalPlaces(Hr));
        }
        
        resultList.add(HrMinTau);
        resultList.add(HrAverageTau);
        resultList.add(HrMaxTau);
        resultList.add(HrMinT);
        resultList.add(HrAverageT);
        resultList.add(HrMaxT);
        
     return resultList;
    }
    
    private static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}