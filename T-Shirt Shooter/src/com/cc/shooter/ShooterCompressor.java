/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cc.shooter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;

/**
 *The Compressor class
 * @author Ursa
 */
public class ShooterCompressor
{
    
    //The Compressor Instance
    private static ShooterCompressor _instance = null;
    
    //The FRC Compressor represented by this class
     private Compressor _compressor;
    
    //Whether the compressor is running or not
    private boolean _isRunning = false;
    
    /**
     * Constructs a new Compressor with specified pressureSwitchChannel and relayChannel
     */
    private ShooterCompressor( int pressureSwitchChannel, int relayChannel )
    {
        _compressor = new Compressor( pressureSwitchChannel, relayChannel );  
        _compressor.start();
    }
    
    /**
     * Singleton of Compressor
     * @return The Instance
     */
    public static ShooterCompressor getInstance( )
    {
        //Creates a new compressor if we already don't have one and returns the compressor either way.
        if( _instance == null )
        {
            _instance = new ShooterCompressor( 5, 8 );
        }
        return _instance;
    }
    
    /**
     * Runs the Compressor
     */
    public void runCompressor( )
    {
        //If the Compressor is running and it shouldn't be, turn it off
       if( !_compressor.getPressureSwitchValue() && _isRunning )
        {
            _compressor.setRelayValue( Relay.Value.kOff );
            _isRunning = false;
        }
        //Else if it should be running and it isn't, turn it on
        else if( _compressor.getPressureSwitchValue() && !_isRunning )
        {
            _compressor.setRelayValue( Relay.Value.kForward );
            _isRunning = true;
        }
    }
    
}
