package org . apogames . image ; 
import java . awt . Component ; 
import java . awt . GraphicsConfiguration ; 
import java . awt . GraphicsEnvironment ; 
import java . awt . MediaTracker ; 
import java . awt . Transparency ; 
import java . awt . image . BufferedImage ; 
import java . awt . image . ImageObserver ; 
import java . io . File ; 
import java . io . IOException ; 
import java . net . MalformedURLException ; 
import java . net . URL ; 
import javax . imageio . ImageIO ; 
import org . apogames . ApoConstants ; 
public class ApoImage extends Component implements ImageObserver { 
private static final long serialVersionUID = 1L ; 
private Component component ; 
private GraphicsConfiguration graphicsConfiguration ; 
public ApoImage ( ) { 
this . component = this ; 
this . graphicsConfiguration = GraphicsEnvironment . getLocalGraphicsEnvironment ( ) . getDefaultScreenDevice ( ) . getDefaultConfiguration ( ) ; 
} 
 
public BufferedImage getPic ( String pic , boolean bLoad ) { 
BufferedImage i ; 
MediaTracker mt ; 
mt = new MediaTracker ( this . component ) ; 
try { 
i = getImage ( pic , bLoad ) ; 
if ( i == null ) { 
return null ; 
} 
 
} 
 catch ( IllegalArgumentException ex ) { 
return null ; 
} 
 
mt . addImage ( i , 0 ) ; 
try { 
mt . waitForAll ( ) ; 
} 
 catch ( InterruptedException e ) { 
} 
 
return i ; 
} 
 
private BufferedImage getImage ( String pic , boolean bLoad ) { 
BufferedImage icon = null ; 
BufferedImage result = null ; 
BufferedImage img = null ; 
if ( ! bLoad ) { 
try { 
img = ImageIO . read ( this . getClass ( ) . getClassLoader ( ) . getResource ( pic ) ) ; 
result = this . graphicsConfiguration . createCompatibleImage ( img . getWidth ( ) , img . getHeight ( ) , Transparency . TRANSLUCENT ) ; 
result . createGraphics ( ) . drawImage ( img , 0 , 0 , null ) ; 
icon = result ; 
} 
 catch ( IOException e ) { 
e . printStackTrace ( ) ; 
return null ; 
} 
 catch ( NullPointerException ex ) { 
return null ; 
} 
 
} 
 else { 
try { 
if ( ApoConstants . B_APPLET ) { 
img = ImageIO . read ( new URL ( pic ) ) ; 
} 
 else { 
img = ImageIO . read ( new File ( pic ) ) ; 
} 
 
result = this . graphicsConfiguration . createCompatibleImage ( img . getWidth ( ) , img . getHeight ( ) , Transparency . TRANSLUCENT ) ; 
result . createGraphics ( ) . drawImage ( img , 0 , 0 , null ) ; 
icon = result ; 
} 
 catch ( MalformedURLException e ) { 
System . out . println ( "Konnte das Bild " + pic + " nicht laden" ) ; 
return null ; 
} 
 catch ( IOException e ) { 
System . out . println ( "Konnte das Bild " + pic + " nicht laden" ) ; 
return null ; 
} 
 
} 
 
return ( icon ) ; 
} 
 
} 
 
