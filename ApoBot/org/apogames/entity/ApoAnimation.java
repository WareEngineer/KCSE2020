package org . apogames . entity ; 
import java . awt . Color ; 
import java . awt . Graphics2D ; 
import java . awt . image . BufferedImage ; 
public class ApoAnimation extends ApoEntity { 
private int frame ; 
private int tiles ; 
private long time ; 
private long curTime ; 
public ApoAnimation ( BufferedImage iAnimation , float x , float y , int tiles , long time ) { 
super ( iAnimation , x , y , iAnimation . getWidth ( ) / tiles , iAnimation . getHeight ( ) ) ; 
this . tiles = tiles ; 
this . time = time ; 
this . init ( ) ; 
} 
 
public ApoAnimation ( BufferedImage iAnimation , float x , float y , float width , float height , int tiles , long time ) { 
super ( iAnimation , x , y , width , height ) ; 
this . tiles = tiles ; 
this . time = time ; 
this . init ( ) ; 
} 
 
public void init ( ) { 
super . init ( ) ; 
this . frame = 0 ; 
this . curTime = 0 ; 
} 
 
public int getTiles ( ) { 
return this . tiles ; 
} 
 
public void setTiles ( int tiles ) { 
this . tiles = tiles ; 
} 
 
public int getFrame ( ) { 
return this . frame ; 
} 
 
public void setFrame ( int frame ) { 
this . frame = frame ; 
} 
 
public long getCurTime ( ) { 
return this . curTime ; 
} 
 
public void setCurTime ( long curTime ) { 
this . curTime = curTime ; 
} 
 
public long getTime ( ) { 
return time ; 
} 
 
public void setTime ( long time ) { 
this . time = time ; 
} 
 
public void think ( int time ) { 
this . curTime += time ; 
while ( this . getCurTime ( ) >= this . getTime ( ) ) { 
this . curTime -= this . time ; 
this . frame += 1 ; 
if ( this . getFrame ( ) >= this . getTiles ( ) ) { 
this . frame = 0 ; 
} 
 
} 
 
} 
 
public void render ( Graphics2D g ) { 
this . render ( g , 0 , 0 ) ; 
} 
 
public void render ( Graphics2D g , int x , int y ) { 
if ( super . isBVisible ( ) ) { 
if ( super . getIBackground ( ) != null ) { 
g . drawImage ( super . getIBackground ( ) . getSubimage ( ( int ) ( this . getFrame ( ) * this . getWidth ( ) ) , 0 , ( int ) this . getWidth ( ) , ( int ) this . getHeight ( ) ) , ( int ) this . getX ( ) + x , ( int ) this . getY ( ) + y , null ) ; 
} 
 
if ( super . isBSelect ( ) ) { 
g . setColor ( Color . red ) ; 
g . drawRect ( ( int ) ( this . getX ( ) - x ) , ( int ) ( this . getY ( ) - y ) , ( int ) ( this . getWidth ( ) - 1 ) , ( int ) ( this . getHeight ( ) - 1 ) ) ; 
} 
 
} 
 
} 
 
} 
 
