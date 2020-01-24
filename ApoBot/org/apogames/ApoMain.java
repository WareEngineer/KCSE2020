package org . apogames ; 
import java . awt . Dimension ; 
import javax . swing . JComponent ; 
import javax . swing . JFrame ; 
import org . apogames . help . ApoSplashScreen ; 
public abstract class ApoMain extends JFrame { 
private static final long serialVersionUID = 1L ; 
public static ApoMain main ; 
private JComponent currentComponent ; 
private ApoComponent component ; 
public ApoMain ( String splashUrl ) { 
this ( splashUrl , 0 , 0 ) ; 
} 
 
public ApoMain ( String splashUrl , int width , int height ) { 
super ( ) ; 
this . setDefaultCloseOperation ( EXIT_ON_CLOSE ) ; 
this . setResizable ( false ) ; 
this . currentComponent = ( JComponent ) this . getContentPane ( ) ; 
this . currentComponent . setPreferredSize ( new Dimension ( width , height ) ) ; 
this . currentComponent . setLayout ( null ) ; 
main = this ; 
ApoSplashScreen splash = null ; 
try { 
splash = new ApoSplashScreen ( splashUrl ) ; 
} 
 catch ( Exception e ) { 
e . printStackTrace ( ) ; 
} 
 
this . init ( ) ; 
splash . blendOut ( ) ; 
this . initWindow ( ) ; 
} 
 
private void initWindow ( ) { 
this . setUndecorated ( false ) ; 
this . pack ( ) ; 
this . setLocationRelativeTo ( null ) ; 
this . setVisible ( true ) ; 
} 
 
public void setComponent ( ApoComponent component ) { 
if ( this . component != null ) { 
this . component . stop ( ) ; 
this . dispose ( ) ; 
this . currentComponent = ( JComponent ) this . getContentPane ( ) ; 
this . currentComponent . setPreferredSize ( new Dimension ( component . getWidth ( ) , component . getHeight ( ) ) ) ; 
this . currentComponent . remove ( this . component ) ; 
this . component = component ; 
this . currentComponent . add ( this . component ) ; 
super . validate ( ) ; 
this . currentComponent . repaint ( ) ; 
this . initWindow ( ) ; 
this . component . init ( ) ; 
this . component . start ( ) ; 
} 
 else { 
this . currentComponent = ( JComponent ) this . getContentPane ( ) ; 
this . currentComponent . setPreferredSize ( new Dimension ( component . getWidth ( ) , component . getHeight ( ) ) ) ; 
this . component = component ; 
this . currentComponent . add ( this . component ) ; 
this . component . init ( ) ; 
this . component . start ( ) ; 
} 
 
} 
 
public abstract void init ( ) ; 
} 
 
