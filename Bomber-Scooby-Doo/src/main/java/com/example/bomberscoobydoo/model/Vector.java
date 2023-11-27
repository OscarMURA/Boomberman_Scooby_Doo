package com.example.bomberscoobydoo.model;

/**
 * The Vector class represents a two-dimensional vector with x and y components.
 */
public class Vector {
    public double x;
    public double y;

    // The `public Vector(double x, double y)` is a constructor for the Vector class. It takes in two
    // parameters, `x` and `y`, which represent the x and y components of the vector.
    // This allows you to create a new Vector object with specified x and y values.
    public Vector(double x,double y){
        this.x = x;
        this.y = y;
    }

    /**
     * The getX() function returns the value of the variable x.
     * 
     * @return The method is returning the value of the variable "x" as a double.
     */
    public double getX() {
        return x;
    }

    /**
     * The function sets the value of the variable "x" to the given input.
     * 
     * @param x The parameter "x" is a double value.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * The function returns the value of the variable "y".
     * 
     * @return The method is returning the value of the variable "y" as a double.
     */
    public double getY() {
        return y;
    }

    /**
     * The function sets the value of the variable "y" to the given input.
     * 
     * @param y The parameter "y" is a double data type.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * The function returns a new Vector object with the same values as the current object.
     * 
     * @return The method is returning a new instance of the Vector class with the same values for the
     * x and y coordinates as the original vector.
     */
    public Vector clone(){
        double m = this.getX();
        double n = this.getY();
        return new Vector(m, n);
    }

    /**
     * The equals() function checks if two Vector objects have the same x and y values.
     * 
     * @param o The parameter "o" is of type Object, which means it can accept any object as an
     * argument. In this case, the method is checking if the object "o" is an instance of the Vector
     * class and if its x and y values are equal to the x and y values of the current
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean equals(Object o){
        return o instanceof Vector &&
                ((Vector) o).getX() == x &&
                ((Vector) o).getY() == y;
    }

    /**
     * The function returns the x-coordinate of a tile based on a given x-coordinate.
     * It makes easier the comparison of certain elements like explosions. It is 60
     * beacause that is the size of tiles in this game.
     * 
     * @return The method is returning the value of x divided by 60, rounded down to the nearest
     * integer.
     */
    public int getTileX(){
        return (int) Math.floor(x/60.0);
    }

        /**
     * The function returns the y-coordinate of a tile based on a given y-coordinate.
     * It makes easier the comparison of certain elements like explosions. It is 60
     * beacause that is the size of tiles in this game.
     * 
     * @return The method is returning the value of y divided by 60, rounded down to the nearest
     * integer.
     */
    public int getTileY(){
        return (int) Math.floor(y/60.0);
    }

    /**
     * The function returns a Vector object representing the position of a tile in a grid.
     * 
     * @return A Vector object that is the tile ubication.
     */
    public Vector getTilePosition(){
        return new Vector(getTileX()*60, getTileY()*60);
    }

    /**
     * The function sets the speed of an object by multiplying its x and y coordinates by the given
     * speed.
     * 
     * @param speed The "speed" parameter is an integer value that represents the desired speed at
     * which the x and y values should be multiplied.
     */
    public void setSpeed(int speed){
        x*=speed;
        y*=speed;
    }

/**
 * The function calculates the magnitude of a vector.
 * 
 * @return The method is returning the magnitude of a vector.
 */
    public double normalize(){
        double magnitude = Math.sqrt(x * x + y * y);
        return magnitude;
    }

}
