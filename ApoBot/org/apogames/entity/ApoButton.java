package org . apogames . entity ; 
import java . awt . Graphics2D ; 
import java . awt . image . BufferedImage ; 
public class ApoButton extends ApoEntity { 
private int WAIT_DELAY = 80 ; 
private int wait ; 
private boolean bWait , bFirstWait ; 
private String function ; 
private boolean bOver , bPressed ; 
public ApoButton ( BufferedImage iBackground , int x , int y , int width , int height , String function ) { 
super ( iBackground , x , y , width , height ) ; 
this . function = function ; 
this . bOver = false ; 
this . bPressed = false ; 
this . wait = 0 ; 
this . bWait = false ; 
this . bFirstWait = true ; 
} 
 
public boolean isBWait ( ) { 
return this . bWait ; 
} 
 
public void setBWait ( boolean bWait ) { 
this . bWait = bWait ; 
} 
 
public int getWAIT_DELAY ( ) { 
return this . WAIT_DELAY ; 
} 
 
public void setWAIT_DELAY ( int wait_delay ) { 
this . WAIT_DELAY = wait_delay ; 
} 
 
public boolean isBOver ( ) { 
return this . bOver ; 
} 
 
public void setBOver ( boolean bOver ) { 
this . bOver = bOver ; 
} 
 
public boolean isBPressed ( ) { 
return this . bPressed ; 
} 
 
public void setBPressed ( boolean bPressed ) { 
this . bPressed = bPressed ; 
} 
 
public String getFunction ( ) { 
return this . function ; 
} 
 
public void setFunction ( String function ) { 
this . function = function ; 
} 
 
public boolean getMove ( int x , int y ) { 
if ( ( ! this . isBOver ( ) ) && ( this . intersects ( x , y ) ) && ( this . isBVisible ( ) ) ) { 
this . setBOver ( true ) ; 
return true ; 
} 
 else if ( ( this . isBOver ( ) ) && ( ! this . intersects ( x , y ) ) ) { 
this . bOver = false ; 
this . bPressed = false ; 
this . wait = 0 ; 
this . bFirstWait = true ; 
return true ; 
} 
 
return false ; 
} 
 
public boolean getPressed ( int x , int y ) { 
if ( ( this . isBOver ( ) ) && ( this . intersects ( x , y ) ) && ( this . isBVisible ( ) ) ) { 
this . setBPressed ( true ) ; 
return true ; 
} 
 
return false ; 
} 
 
public boolean getReleased ( int x , int y ) { 
if ( ( this . isBPressed ( ) ) && ( this . intersects ( x , y ) ) && ( this . isBVisible ( ) ) ) { 
this . setBPressed ( false ) ; 
this . wait = 0 ; 
this . bFirstWait = true ; 
return true ; 
} 
 
return false ; 
} 
 
public void think ( int delay ) { 
if ( ! this . isBWait ( ) ) { 
return ; 
} 
 
if ( this . isBPressed ( ) ) { 
this . wait += delay ; 
if ( this . bFirstWait ) { 
if ( this . wait > 7 * this . WAIT_DELAY ) { 
this . wait -= 7 * this . WAIT_DELAY ; 
this . bFirstWait = false ; 
return ; 
} 
 
} 
 else if ( this . wait > this . WAIT_DELAY ) { 
this . wait -= this . WAIT_DELAY ; 
return ; 
} 
 
} 
 
} 
 
public void render ( Graphics2D g , int changeX , int changeY ) { 
if ( this . isBVisible ( ) ) { 
if ( this . isBPressed ( ) ) g . drawImage ( this . getIBackground ( ) , ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) ( this . getX ( ) + changeX + this . getWidth ( ) ) , ( int ) ( this . getY ( ) + changeY + this . getHeight ( ) ) , ( int ) ( 2 * this . getWidth ( ) ) , 0 , ( int ) ( 3 * this . getWidth ( ) ) , ( int ) this . getHeight ( ) , null ) ; else if ( this . isBOver ( ) ) g . drawImage ( this . getIBackground ( ) , ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) ( this . getX ( ) + changeX + this . getWidth ( ) ) , ( int ) ( this . getY ( ) + changeY + this . getHeight ( ) ) , ( int ) ( 1 * this . getWidth ( ) ) , 0 , ( int ) ( 2 * this . getWidth ( ) ) , ( int ) this . getHeight ( ) , null ) ; else if ( this . isBUse ( ) ) g . drawImage ( this . getIBackground ( ) , ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) ( this . getX ( ) + changeX + this . getWidth ( ) ) , ( int ) ( this . getY ( ) + changeY + this . getHeight ( ) ) , ( int ) ( 3 * this . getWidth ( ) ) , 0 , ( int ) ( 4 * this . getWidth ( ) ) , ( int ) this . getHeight ( ) , null ) ; else g . drawImage ( this . getIBackground ( ) , ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) ( this . getX ( ) + changeX + this . getWidth ( ) ) , ( int ) ( this . getY ( ) + changeY + this . getHeight ( ) ) , ( int ) ( 0 * this . getWidth ( ) ) , 0 , ( int ) ( 1 * this . getWidth ( ) ) , ( int ) this . getHeight ( ) , null ) ; 
} 
 
} 
 
} 
 
