package org . apogames ; 
import java . util . Timer ; 
import java . util . TimerTask ; 
public class ApoTimer extends TimerTask { 
private ApoTimerInterface game ; 
private boolean bRunning ; 
private boolean bWait ; 
private boolean bRender ; 
private long wait ; 
private long delta ; 
private Timer timer ; 
public ApoTimer ( ApoTimerInterface game , long wait ) { 
super ( ) ; 
this . game = game ; 
this . wait = wait ; 
this . bRunning = true ; 
this . setBWait ( true ) ; 
this . setBRender ( true ) ; 
} 
 
public boolean isBWait ( ) { 
return this . bWait ; 
} 
 
public void setBWait ( boolean bWait ) { 
this . bWait = bWait ; 
} 
 
public boolean isBRender ( ) { 
return bRender ; 
} 
 
public void setBRender ( boolean bRender ) { 
this . bRender = bRender ; 
} 
 
public long getWait ( ) { 
return this . wait ; 
} 
 
public void setWait ( long wait ) { 
this . wait = wait ; 
} 
 
public void start ( ) { 
if ( this . timer == null ) { 
this . timer = new Timer ( ) ; 
this . timer . scheduleAtFixedRate ( this , this . wait , this . wait ) ; 
this . delta = System . nanoTime ( ) ; 
} 
 
} 
 
public void stop ( ) { 
if ( this . timer != null ) { 
this . setBRunning ( false ) ; 
this . timer . cancel ( ) ; 
this . timer = null ; 
} 
 
} 
 
public boolean isBRunning ( ) { 
return this . bRunning ; 
} 
 
public void setBRunning ( boolean bRunning ) { 
this . bRunning = bRunning ; 
} 
 
public int getFps ( ) { 
return ( int ) this . getWait ( ) ; 
} 
 
@ Override public void run ( ) { 
if ( this . isBRunning ( ) ) { 
long delta = System . nanoTime ( ) ; 
while ( delta - this . delta >= this . wait * 1000000 ) { 
this . delta += this . wait * 1000000 ; 
if ( ( this . game . isBThink ( ) ) && ( this . isBWait ( ) ) ) { 
this . game . think ( ( int ) this . wait ) ; 
} 
 
} 
 
if ( ( this . game . isBRepaint ( ) ) && ( this . isBRender ( ) ) ) { 
this . game . render ( ) ; 
} 
 
} 
 
} 
 
} 
 
