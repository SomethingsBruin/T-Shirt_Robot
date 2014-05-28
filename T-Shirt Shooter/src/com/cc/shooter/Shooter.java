/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cc.shooter;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 *The shooter Class that shoots a TShirt
 * @author Ursa
 */
public class Shooter
{
   
    //The talon that conrtols the shooting
      private Talon _shooter;
      
      //The shooter on the robot
      private static Shooter _instance;
      
      //Whether or not we are firing
      private boolean firing = false;
      
      //The Timer that controls when to fire
      private Timer _timer;
      
      /**
       * Private constructor for singleton that creates a new talon for the shooter
       * @param talonLocation The port of the talon 
       */
      private Shooter( int talonLocation )
      {
          //Creates a new talon that will control the shooter at the specified port
          _shooter = new Talon( talonLocation );
      }
      
      /**
       * Makes sure that we have one and only one shooter and returns it in place of a constructor
       * @return Returns the instance of the Shooter that is in use
       */
      public static Shooter getInstance( )
      {
          //Makes a new Shooter if there is none, then returns it
          if( _instance == null )
          {
              _instance = new Shooter( 10 );
          }
          return _instance;
      }
      /**
       * Shoots the tShirt
       * @param delay The time that the valve is open for the shot to occur
       */
      public void shoot( double delay )
      {
          //Checks to see if we are already firing, if so, don't fire
          if( !firing && ( _timer == null || _timer.get( ) > 0.5 ) )
          {
            //Creates a timer to make sure we aren't firing to quickly
            _timer = new Timer( );
            _timer.reset( );
            _timer.start( );
            
            //Starts the firing process and fires using the delay provided
            firing = true;
            _shooter.set( 1.0 );
            Timer.delay( delay );
            _shooter.set( 0.0 );
            firing = false;
          }
      }
      
}
