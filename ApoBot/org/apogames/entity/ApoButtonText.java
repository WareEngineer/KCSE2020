package org . apogames . entity ; 
import java . awt . Color ; 
import java . awt . Font ; 
import java . awt . Graphics2D ; 
import java . awt . image . BufferedImage ; 
public class ApoButtonText extends ApoButton { 
private String text ; 
private Font font ; 
private Color colorPressed , colorReleased ; 
private int value ; 
public ApoButtonText ( BufferedImage iBackground , int x , int y , int width , int height , String function , String text ) { 
super ( iBackground , x , y , width , height , function ) ; 
this . value = - 1 ; 
this . text = text ; 
this . font = new Font ( "Dialog" , Font . BOLD , 14 ) ; 
this . colorPressed = new Color ( 80 , 80 , 0 ) ; 
this . colorReleased = Color . RED ; 
} 
 
public ApoButtonText ( BufferedImage iBackground , int x , int y , int width , int height , String function , int value ) { 
super ( iBackground , x , y , width , height , function ) ; 
this . value = value ; 
this . text = "" ; 
this . font = new Font ( "Dialog" , Font . BOLD , 14 ) ; 
this . colorPressed = new Color ( 80 , 80 , 0 ) ; 
this . colorReleased = Color . RED ; 
} 
 
public Color getColorPressed ( ) { 
return this . colorPressed ; 
} 
 
public void setColorPressed ( Color colorPressed ) { 
this . colorPressed = colorPressed ; 
} 
 
public Color getColorReleased ( ) { 
return this . colorReleased ; 
} 
 
public void setColorReleased ( Color colorReleased ) { 
this . colorReleased = colorReleased ; 
} 
 
public void setFontSize ( int size ) { 
this . font = new Font ( "Dialog" , Font . BOLD , size ) ; 
} 
 
public int getValue ( ) { 
return this . value ; 
} 
 
public void render ( Graphics2D g , int changeX , int changeY ) { 
if ( this . isBVisible ( ) ) { 
g . setFont ( this . font ) ; 
if ( this . getIBackground ( ) != null ) g . drawImage ( this . getIBackground ( ) , ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , null ) ; 
int w = g . getFontMetrics ( ) . stringWidth ( this . text ) ; 
int x = ( int ) ( this . getX ( ) + this . getWidth ( ) / 2 - w / 2 ) ; 
int y = ( int ) ( this . getY ( ) + this . getHeight ( ) / 2 + 5 ) ; 
if ( this . isBPressed ( ) ) { 
g . setColor ( this . colorReleased ) ; 
if ( this . text . equals ( "" ) ) g . drawRect ( ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) this . getWidth ( ) - 1 , ( int ) this . getHeight ( ) - 1 ) ; else g . drawString ( this . text , x + changeX , y + changeY ) ; 
} 
 else if ( this . isBOver ( ) ) { 
g . setColor ( this . colorPressed ) ; 
if ( this . text . equals ( "" ) ) g . drawRect ( ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) this . getWidth ( ) - 1 , ( int ) this . getHeight ( ) - 1 ) ; else g . drawString ( this . text , x + changeX , y + changeY ) ; 
} 
 else { 
g . setColor ( Color . BLACK ) ; 
if ( ! this . text . equals ( "" ) ) g . drawString ( this . text , x + changeX , y + changeY ) ; else if ( this . isBSelect ( ) ) { 
g . setColor ( this . colorReleased ) ; 
g . drawRect ( ( int ) this . getX ( ) + changeX , ( int ) this . getY ( ) + changeY , ( int ) this . getWidth ( ) - 1 , ( int ) this . getHeight ( ) - 1 ) ; 
} 
 
} 
 
} 
 
} 
 
} 
 
