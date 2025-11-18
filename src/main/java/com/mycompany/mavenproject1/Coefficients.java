package com.mycompany.mavenproject1;

public class Coefficients {
    private int id;
    private String material;
    private double b0;
    private double b1;
    private double b2;
    private double b3;
    private double b4;
    private double b5;
    private double b6;
    private double b7;
    private double b8;
    
    public Coefficients(){}
    
    public Coefficients(int id, String material, double b0, double b1, double b2, double b3,
            double b4, double b5, double b6, double b7, double b8){
        this.material = material;
        this.id = id;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
        this.b5 = b5;
        this.b6 = b6;
        this.b7 = b7;
        this.b8 = b8;
    }
    
    public String getMaterial() { return material; }

   public void setMaterial(String material) { this.material = material; }
    
    public int getId() { return id; }

   public void setId(int id) { this.id = id; }
    
    public double getB0() { return b0; }

   public void setB0(double b0) { this.b0 = b0; }
   
   public double getB1() { return b1; }

   public void setB1(double b1) { this.b1 = b1; }
   
   public double getB2() { return b2; }

   public void setB2(double b2) { this.b2 = b2; }
   
   public double getB3() { return b3; }

   public void setB3(double b3) { this.b3 = b3; }
   
   public double getB4() { return b4; }

   public void setB4(double b4) { this.b4 = b4; }
   
   public double getB5() { return b5; }

   public void setB5(double b5) { this.b5 = b5; }
   
   public double getB6() { return b6; }

   public void setB6(double b6) { this.b6 = b6; }
   
   public double getB7() { return b7; }

   public void setB7(double b7) { this.b7 = b7; }
   
   public double getB8() {  return b8; }

   public void setB8(double b8) { this.b8 = b8; }
}
