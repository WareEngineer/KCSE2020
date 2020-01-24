package org . apogames . image ; 
public class ApoRawScale3x { 
private int [ ] srcImage ; 
private int [ ] dstImage ; 
private int width ; 
private int height ; 
public ApoRawScale3x ( int [ ] imageData , int dataWidth , int dataHeight ) { 
this . width = dataWidth ; 
this . height = dataHeight ; 
this . srcImage = imageData ; 
this . dstImage = new int [ imageData . length * 9 ] ; 
} 
 
private boolean different ( int a , int b ) { 
return a != b ; 
} 
 
private void setDestPixel ( int x , int y , int p ) { 
this . dstImage [ x + ( y * this . width * 3 ) ] = p ; 
} 
 
private int getSourcePixel ( int x , int y ) { 
x = Math . max ( 0 , x ) ; 
x = Math . min ( this . width - 1 , x ) ; 
y = Math . max ( 0 , y ) ; 
y = Math . min ( this . height - 1 , y ) ; 
return this . srcImage [ x + ( y * this . width ) ] ; 
} 
 
private void process ( int x , int y ) { 
int A = this . getSourcePixel ( x - 1 , y - 1 ) ; 
int B = this . getSourcePixel ( x , y - 1 ) ; 
int C = this . getSourcePixel ( x + 1 , y - 1 ) ; 
int D = this . getSourcePixel ( x - 1 , y ) ; 
int E = this . getSourcePixel ( x , y ) ; 
int F = this . getSourcePixel ( x + 1 , y ) ; 
int G = this . getSourcePixel ( x - 1 , y + 1 ) ; 
int H = this . getSourcePixel ( x , y + 1 ) ; 
int I = this . getSourcePixel ( x + 1 , y + 1 ) ; 
int E0 = E ; 
int E1 = E ; 
int E2 = E ; 
int E3 = E ; 
int E4 = E ; 
int E5 = E ; 
int E6 = E ; 
int E7 = E ; 
int E8 = E ; 
if ( different ( B , H ) && different ( D , F ) ) { 
E0 = ! different ( D , B ) ? D : E ; 
E1 = ( ( ! different ( D , B ) ) && different ( E , C ) ) || ( ( ! different ( B , F ) ) && different ( E , A ) ) ? B : E ; 
E2 = ! different ( B , F ) ? F : E ; 
E3 = ( ( ! different ( D , B ) ) && different ( E , G ) ) || ( ( ! different ( D , H ) ) && different ( E , A ) ) ? D : E ; 
E4 = E ; 
E5 = ( ( ! different ( B , F ) ) && different ( E , I ) ) || ( ( ! different ( H , F ) ) && different ( E , C ) ) ? F : E ; 
E6 = ! different ( D , H ) ? D : E ; 
E7 = ( ( ! different ( D , H ) ) && different ( E , I ) ) || ( ( ! different ( H , F ) ) && different ( E , G ) ) ? H : E ; 
E8 = ! different ( H , F ) ? F : E ; 
} 
 
setDestPixel ( ( x * 3 ) , ( y * 3 ) , E0 ) ; 
setDestPixel ( ( x * 3 ) + 1 , ( y * 3 ) , E1 ) ; 
setDestPixel ( ( x * 3 ) + 2 , ( y * 3 ) , E2 ) ; 
setDestPixel ( ( x * 3 ) , ( y * 3 ) + 1 , E3 ) ; 
setDestPixel ( ( x * 3 ) + 1 , ( y * 3 ) + 1 , E4 ) ; 
setDestPixel ( ( x * 3 ) + 2 , ( y * 3 ) + 1 , E5 ) ; 
setDestPixel ( ( x * 3 ) , ( y * 3 ) + 2 , E6 ) ; 
setDestPixel ( ( x * 3 ) + 1 , ( y * 3 ) + 2 , E7 ) ; 
setDestPixel ( ( x * 3 ) + 2 , ( y * 3 ) + 2 , E8 ) ; 
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
 
