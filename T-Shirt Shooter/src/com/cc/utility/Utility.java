package com.cc.utility;

import com.sun.squawk.util.MathUtils;

/**
 * This class is used for all math that limits a number between to numbers.
 * The most common instance of this is when you are limiting a number between
 * 1 and -1.
 */
public class Utility
{
    /**
     * Limits a number between 1.0 and -1.0.
     * 
     * @param val The value you wish to limit between 1.0 and -1.0.
     * 
     * @return Returns the limited number.
     */
    public static double limitRange( double val )
    {
        if( val > 1.0 )//If number is greater than 1.0, it becomes 1.0.
        {
            val = 1.0;  
        }
        else if( val < -1.0 )//Else if number is less than -1.0, it becomes -1.0.
        {
            val = -1.0;
        }
        
        //Returns the limited number
        return val;
    }
    
    /**
     * Limits a number between a given maximum and a given minimum.
     * 
     * @param val The value you wish to limit between a max and min.
     * @param max The max to limit the value on.
     * @param min The min to limit the value on.
     * 
     * @return Returns the limited number.
     */
    public static double limitRange( double val , double max , double min )
    {
        //If number is greater than maximum, it becomes the maximum.
        if( val > max )
        {
            val = max;  
        }
        else if( val < min )//Else if the number is less than minimum, it becomes the minimum.
        {
            val = min;
        }
        
        //Returns the limited number.
        return val;
    }
    
    /**
     * Returns a number that is equal to the given value to the power of the given exponent.
     * If the number is negative going into the method, it will still be negative coming 
     * out of the method.
     * 
     * @param value The value that will be put to the power of the given exponent.
     * @param expo The exponent that the value is to the power to.
     * 
     * @return Returns the value to the power of the given exponent.
     */
    public static double expo( double value, double expo )
    {
        //A boolean which represents whether the original value was negative or not.
        boolean negative = value < 0.0;
        
        //Does the math for finding the new value.
        value = MathUtils.pow( value, expo );
        
        //If the value was originally negative and the value is now positive...
        if( negative && value > 0.0 )
        {
            //Make the value negative.
            value *= -1;
        }
        
        //Return the new value.
        return value;
    }
}