package org . apogames . entity ; 
import java . awt . Color ; 
import java . awt . Graphics2D ; 
public class ApoDragObject extends ApoButton { 
private int diffX , diffY ; 
public ApoDragObject ( int x , int y , int width , int height ) { 
super ( null , x , y , width , height , "" ) ; 
this . diffX = 0 ; 
this . diffY = 0 ; 
} 
 
public boolean getPressed ( int x , int y ) { 
boolean bPressed = super . getPressed ( x , y ) ; 
if ( bPressed ) { 
this . diffX = ( int ) ( x - this . getX ( ) ) ; 
this . diffY = ( int ) ( y - this . getY ( ) ) ; 
super . setBSelect ( true ) ; 
} 
 
return bPressed ; 
} 
 
public boolean getReleased ( int x , int y ) { 
boolean bReleased = super . getReleased ( x , y ) ; 
this . diffX = 0 ; 
this . diffY = 0 ; 
return bReleased ; 
} 
 
public boolean getIn ( int x , int y ) { 
if ( ( this . getX ( ) - this . getWidth ( ) / 2 < x ) && ( this . getX ( ) + this . getWidth ( ) / 2 + this . getWidth ( ) >= x ) && ( this . getY ( ) - this . getHeight ( ) / 2 < y ) && ( this . getY ( ) + this . getHeight ( ) / 2 + this . getHeight ( ) >= y ) ) { 
return true ; 
} 
 
return false ; 
} 
 
public void setDragX ( int x ) { 
this . setX ( x - this . diffX ) ; 
} 
 
public void setDragY ( int y ) { 
this . setY ( y - this . diffY ) ; 
} 
 
public int getNewX ( int x ) { 
return ( x - this . diffX ) ; 
} 
 
public int getNewY ( int y ) { 
return ( y - this . diffY ) ; 
} 
 
public int getDiffX ( ) { 
return this . diffX ; 
} 
 
public int getDiffY ( ) { 
return this . diffY ; 
} 
 
public void render ( Graphics2D g , int changeX , int changeY ) { 
if ( this . isBVisible ( ) ) { 
if ( super . isBSelect ( ) ) { 
g . setColor ( Color . WHITE ) ; 
g . drawRect ( ( int ) ( this . getX ( ) - this . getWidth ( ) / 2 ) , ( int ) ( this . getY ( ) - this . getHeight ( ) / 2 ) , ( int ) this . getWidth ( ) , ( int ) this . getHeight ( ) ) ; 
} 
 
g . setColor ( Color . BLACK ) ; 
g . fillRect ( ( int ) ( this . getX ( ) - 2 ) , ( int ) ( this . getY ( ) - 2 ) , 4 , 4 ) ; 
} 
 
} 
 
} 
 
