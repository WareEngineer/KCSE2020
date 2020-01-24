package org . apogames . image ; 
import java . awt . image . BufferedImage ; 
import java . io . File ; 
import java . io . IOException ; 
import javax . imageio . ImageIO ; 
public class ApoImageScale { 
public static final int ORIGINAL_SCALE = 1 ; 
public static final int DOUBLE_SCALE = 2 ; 
public static final int TRIPPLE_SCALE = 3 ; 
private String srcFile ; 
private int scale = ORIGINAL_SCALE ; 
public ApoImageScale ( String srcFile ) { 
this ( srcFile , DOUBLE_SCALE ) ; 
} 
 
public ApoImageScale ( String srcFile , int scale ) { 
this . srcFile = srcFile ; 
this . scale = scale ; 
} 
 
public int getScale ( ) { 
return this . scale ; 
} 
 
public void setScale ( int scale ) { 
this . scale = scale ; 
} 
 
public boolean writeScaledImage ( ) { 
return this . writeScaledImage ( this . srcFile ) ; 
} 
 
public boolean writeScaledImage ( String writeFile ) { 
try { 
return writeScaledImage ( ImageIO . read ( new File ( this . srcFile ) ) , writeFile ) ; 
} 
 catch ( IOException e ) { 
e . printStackTrace ( ) ; 
} 
 
return true ; 
} 
 
public boolean writeScaledImage ( BufferedImage iOriginal , String writeFile ) { 
try { 
BufferedImage src = iOriginal ; 
BufferedImage out = null ; 
if ( this . getScale ( ) == DOUBLE_SCALE ) { 
out = this . getDoubleScaledImage ( src ) ; 
} 
 else if ( this . getScale ( ) == TRIPPLE_SCALE ) { 
} 
 else { 
out = src ; 
} 
 
String outFile = writeFile ; 
if ( writeFile . lastIndexOf ( "." ) != - 1 ) { 
outFile = writeFile . substring ( 0 , writeFile . lastIndexOf ( "." ) ) ; 
} 
 
if ( this . getScale ( ) == DOUBLE_SCALE ) { 
outFile += "2x.png" ; 
} 
 else if ( this . getScale ( ) == TRIPPLE_SCALE ) { 
outFile += "3x.png" ; 
} 
 else { 
outFile += ".png" ; 
} 
 
ImageIO . write ( out , "PNG" , new File ( outFile ) ) ; 
} 
 catch ( Exception e ) { 
e . printStackTrace ( ) ; 
} 
 
return true ; 
} 
 
public BufferedImage getDoubleScaledImage ( BufferedImage srcImage ) { 
int width = srcImage . getWidth ( ) ; 
int height = srcImage . getHeight ( ) ; 
int [ ] srcData = new int [ width * height ] ; 
srcImage . getRGB ( 0 , 0 , width , height , srcData , 0 , width ) ; 
ApoRawScale2x scaler = new ApoRawScale2x ( srcData , width , height ) ; 
BufferedImage image = new BufferedImage ( width * 2 , height * 2 , BufferedImage . TYPE_INT_ARGB ) ; 
image . setRGB ( 0 , 0 , width * 2 , height * 2 , scaler . getScaledData ( ) , 0 , width * 2 ) ; 
return image ; 
} 
 
public BufferedImage getTrippleScaledImage ( BufferedImage srcImage ) { 
int width = srcImage . getWidth ( ) ; 
int height = srcImage . getHeight ( ) ; 
int [ ] srcData = new int [ width * height ] ; 
srcImage . getRGB ( 0 , 0 , width , height , srcData , 0 , width ) ; 
ApoRawScale3x scaler = new ApoRawScale3x ( srcData , width , height ) ; 
BufferedImage image = new BufferedImage ( width * 3 , height * 3 , BufferedImage . TYPE_INT_ARGB ) ; 
image . setRGB ( 0 , 0 , width * 3 , height * 3 , scaler . getScaledData ( ) , 0 , width * 2 ) ; 
return image ; 
} 
 
} 
 
