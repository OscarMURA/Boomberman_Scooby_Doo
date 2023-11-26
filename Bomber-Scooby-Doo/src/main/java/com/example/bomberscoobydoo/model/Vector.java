package com.example.bomberscoobydoo.model;

public class Vector {
    public double x;
    public double y;

    public Vector(double x,double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector clone(){
        double m = this.getX();
        double n = this.getY();
        return new Vector(m, n);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Vector &&
                ((Vector) o).getX() == x &&
                ((Vector) o).getY() == y;
    }

    public int getTileX(){
        return (int) Math.floor(x/60.0);
    }
    public int getTileY(){
        return (int) Math.floor(y/60.0);
    }

    public Vector getTilePosition(){
        return new Vector(getTileX()*60, getTileY()*60);
    }

    public void setSpeed(int speed){
        x*=speed;
        y*=speed;
    }
    public double normalize(){
        double magnitude = Math.sqrt(x * x + y * y);
        return magnitude;
    }

}
