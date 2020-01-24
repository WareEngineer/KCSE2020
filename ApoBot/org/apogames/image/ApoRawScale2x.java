package org . apogames . image ; 
public class ApoRawScale2x { 
private int [ ] srcImage ; 
private int [ ] dstImage ; 
private int width ; 
private int height ; 
public ApoRawScale2x ( int [ ] imageData , int dataWidth , int dataHeight ) { 
this . width = dataWidth ; 
this . height = dataHeight ; 
this . srcImage = imageData ; 
this . dstImage = new int [ imageData . length * 4 ] ; 
} 
 
private boolean different ( int a , int b ) { 
return a != b ; 
} 
 
private void setDestPixel ( int x , int y , int p ) { 
this . dstImage [ x + ( y * this . width * 2 ) ] = p ; 
} 
 
private int getSourcePixel ( int x , int y ) { 
x = Math . max ( 0 , x ) ; 
x = Math . min ( this . width - 1 , x ) ; 
y = Math . max ( 0 , y ) ; 
y = Math . min ( this . height - 1 , y ) ; 
return srcImage [ x + ( y * this . width ) ] ; 
} 
 
private void process ( int x , int y ) { 
@ SuppressWarnings ( "unused" ) int A = this . getSourcePixel ( x - 1 , y - 1 ) ; 
int B = this . getSourcePixel ( x , y - 1 ) ; 
@ SuppressWarnings ( "unused" ) int C = this . getSourcePixel ( x + 1 , y - 1 ) ; 
int D = this . getSourcePixel ( x - 1 , y ) ; 
int E = this . getSourcePixel ( x , y ) ; 
int F = this . getSourcePixel ( x + 1 , y ) ; 
@ SuppressWarnings ( "unused" ) int G = this . getSourcePixel ( x - 1 , y + 1 ) ; 
int H = this . getSourcePixel ( x , y + 1 ) ; 
@ SuppressWarnings ( "unused" ) int I = this . getSourcePixel ( x + 1 , y + 1 ) ; 
int E0 = E ; 
int E1 = E ; 
int E2 = E ; 
int E3 = E ; 
if ( this . different ( B , H ) && this . different ( D , F ) ) { 
E0 = ! this . different ( D , B ) ? D : E ; 
E1 = ! this . different ( B , F ) ? F : E ; 
E2 = ! this . different ( D , H ) ? D : E ; 
E3 = ! this . different ( H , F ) ? F : E ; 
} 
 
this . setDestPixel ( x * 2 , y * 2 , E0 ) ; 
this . setDestPixel ( ( x * 2 ) + 1 , y * 2 , E1 ) ; 
this . setDestPixel ( ( x * 2 ) , ( y * 2 ) + 1 , E2 ) ; 
this . setDestPixel ( ( x * 2 ) + 1 , ( y * 2 ) + 1 , E3 ) ; 
} 
 
public int [ ] getScaledData ( ) { 
for ( int x = 0 ; x < this . width ; x ++ ) { 
for ( int y = 0 ; y < this . height ; y ++ ) { 
process ( x , y ) ; 
} 
 
} 
 
return this . dstImage ; 
} 
 
} 
 
