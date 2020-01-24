package org . apogames . entity ; 
import java . awt . Color ; 
import java . awt . Graphics ; 
import java . awt . Graphics2D ; 
import java . awt . geom . Rectangle2D ; 
import java . awt . image . BufferedImage ; 
public class ApoEntity { 
private float x , y , startX , startY , vecX , vecY ; 
private float width , height ; 
private BufferedImage iBackground ; 
private boolean bSelect , bVisible , bClose , bUse ; 
public ApoEntity ( BufferedImage iBackground , float x , float y , float width , float height ) { 
this . iBackground = iBackground ; 
this . startX = x ; 
this . startY = y ; 
this . width = width ; 
this . height = height ; 
this . init ( ) ; 
} 
 
public void init ( ) { 
this . x = this . startX ; 
this . y = this . startY ; 
this . bSelect = false ; 
this . bVisible = true ; 
this . vecX = 0.0F ; 
this . vecY = 0.0F ; 
this . setBUse ( false ) ; 
} 
 
public float getStartX ( ) { 
return this . startX ; 
} 
 
public void setStartX ( float startX ) { 
this . startX = startX ; 
} 
 
public float getStartY ( ) { 
return this . startY ; 
} 
 
public void setStartY ( float startY ) { 
this . startY = startY ; 
} 
 
public boolean isBVisible ( ) { 
return this . bVisible ; 
} 
 
public void setBVisible ( boolean bVisible ) { 
this . bVisible = bVisible ; 
} 
 
public boolean isBSelect ( ) { 
return this . bSelect ; 
} 
 
public void setBSelect ( boolean bSelect ) { 
this . bSelect = bSelect ; 
} 
 
public boolean isBClose ( ) { 
return this . bClose ; 
} 
 
public void setBClose ( boolean bClose ) { 
this . bClose = bClose ; 
} 
 
public boolean isBUse ( ) { 
return this . bUse ; 
} 
 
public void setBUse ( boolean bUse ) { 
this . bUse = bUse ; 
} 
 
public float getVecY ( ) { 
return this . vecY ; 
} 
 
public void setVecY ( float vecY ) { 
this . vecY = vecY ; 
} 
 
public float getVecX ( ) { 
return this . vecX ; 
} 
 
public void setVecX ( float vecX ) { 
this . vecX = vecX ; 
} 
 
public BufferedImage getIBackground ( ) { 
return this . iBackground ; 
} 
 
public void setIBackground ( BufferedImage background ) { 
iBackground = background ; 
} 
 
public float getWidth ( ) { 
return this . width ; 
} 
 
public void setWidth ( float width ) { 
this . width = width ; 
} 
 
public float getHeight ( ) { 
return this . height ; 
} 
 
public void setHeight ( float height ) { 
this . height = height ; 
} 
 
public float getX ( ) { 
return this . x ; 
} 
 
public float getXMiddle ( ) { 
return this . x + this . width / 2 ; 
} 
 
public void setX ( float x ) { 
this . x = x ; 
} 
 
public float getY ( ) { 
return this . y ; 
} 
 
public void setY ( float y ) { 
this . y = y ; 
} 
 
public boolean intersects ( float x , float y ) { 
return this . intersects ( x , y , 1 , 1 ) ; 
} 
 
public boolean intersects ( float x , float y , float width , float height ) { 
return this . getRec ( ) . intersects ( x , y , width , height ) ; 
} 
 
public boolean intersects ( ApoEntity entity ) { 
if ( this . getRec ( ) . intersects ( entity . getRec ( ) ) ) { 
return true ; 
} 
 
return false ; 
} 
 
public boolean contains ( float x , float y , float width , float height ) { 
return this . getRec ( ) . contains ( x , y , width , height ) ; 
} 
 
public boolean contains ( ApoEntity entity ) { 
return this . getRec ( ) . contains ( entity . getRec ( ) ) ; 
} 
 
public Rectangle2D . Float getRec ( ) { 
return new Rectangle2D . Float ( this . getX ( ) , this . getY ( ) , this . getWidth ( ) , this . getHeight ( ) ) ; 
} 
 
public boolean checkOpaqueColorCollisions ( ApoEntity entity ) { 
Rectangle2D . Float cut = ( Rectangle2D . Float ) this . getRec ( ) . createIntersection ( entity . getRec ( ) ) ; 
if ( ( cut . width < 1 ) || ( cut . height < 1 ) ) { 
return false ; 
} 
 
Rectangle2D . Float sub_me = getSubRec ( this . getRec ( ) , cut ) ; 
Rectangle2D . Float sub_him = getSubRec ( entity . getRec ( ) , cut ) ; 
BufferedImage img_me = this . getIBackground ( ) . getSubimage ( ( int ) sub_me . x , ( int ) sub_me . y , ( int ) sub_me . width , ( int ) sub_me . height ) ; 
BufferedImage img_him = entity . getIBackground ( ) . getSubimage ( ( int ) sub_him . x , ( int ) sub_him . y , ( int ) sub_him . width , ( int ) sub_him . height ) ; 
for ( int i = 0 ; i < img_me . getWidth ( ) ; i ++ ) { 
for ( int n = 0 ; n < img_him . getHeight ( ) ; n ++ ) { 
int rgb1 = img_me . getRGB ( i , n ) ; 
int rgb2 = img_him . getRGB ( i , n ) ; 
if ( isOpaque ( rgb1 ) && isOpaque ( rgb2 ) ) { 
return true ; 
} 
 
} 
 
} 
 
return false ; 
} 
 
private Rectangle2D . Float getSubRec ( Rectangle2D . Float source , Rectangle2D . Float part ) { 
Rectangle2D . Float sub = new Rectangle2D . Float ( ) ; 
if ( source . x > part . x ) { 
sub . x = 0 ; 
} 
 else { 
sub . x = part . x - source . x ; 
} 
 
if ( source . y > part . y ) { 
sub . y = 0 ; 
} 
 else { 
sub . y = part . y - source . y ; 
} 
 
sub . width = part . width ; 
sub . height = part . height ; 
return sub ; 
} 
 
private boolean isOpaque ( int rgb ) { 
int alpha = ( rgb >> 24 ) & 0xff ; 
if ( alpha == 0 ) { 
return false ; 
} 
 
return true ; 
} 
 
public void think ( int delta ) { 
} 
 
public void render ( Graphics2D g , int x , int y ) { 
if ( ( this . getIBackground ( ) != null ) && ( this . isBVisible ( ) ) ) { 
g . drawImage ( this . iBackground , ( int ) ( this . getX ( ) + x ) , ( int ) ( this . getY ( ) + y ) , ( int ) ( this . getX ( ) + x + this . getWidth ( ) ) , ( int ) ( this . getY ( ) + y + this . getHeight ( ) ) , 0 , 0 , ( int ) this . getWidth ( ) , ( int ) this . getHeight ( ) , null ) ; 
if ( this . isBSelect ( ) ) { 
g . setColor ( Color . red ) ; 
g . drawRect ( ( int ) ( this . getX ( ) + x ) , ( int ) ( this . getY ( ) + y ) , ( int ) ( this . getWidth ( ) - 1 ) , ( int ) ( this . getHeight ( ) - 1 ) ) ; 
} 
 
} 
 
} 
 
public void render ( Graphics2D g ) { 
this . render ( g , 0 , 0 ) ; 
} 
 
public void render ( Graphics g ) { 
this . render ( ( Graphics2D ) g , 0 , 0 ) ; 
} 
 
} 
 
