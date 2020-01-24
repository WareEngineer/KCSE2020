package org . apogames ; 
import java . awt . Frame ; 
import org . apogames . help . ApoSplashScreen ; 
public abstract class ApoMainBufferedStrategy extends Frame { 
private static final long serialVersionUID = 1L ; 
public static ApoMainBufferedStrategy main ; 
private ApoComponentBufferedStrategy component ; 
public ApoMainBufferedStrategy ( String splashUrl ) { 
this ( splashUrl , 0 , 0 ) ; 
} 
 
public ApoMainBufferedStrategy ( String splashUrl , int width , int height ) { 
super ( ) ; 
this . setResizable ( false ) ; 
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
if ( this . component != null ) { 
this . component . start ( ) ; 
} 
 
} 
 
private void initWindow ( ) { 
this . setIgnoreRepaint ( true ) ; 
this . setUndecorated ( false ) ; 
this . pack ( ) ; 
this . setLocationRelativeTo ( null ) ; 
this . setVisible ( true ) ; 
} 
 
public void setComponent ( ApoComponentBufferedStrategy component ) { 
if ( this . component != null ) { 
this . component . stop ( ) ; 
this . dispose ( ) ; 
this . remove ( this . component ) ; 
this . component = component ; 
this . add ( this . component ) ; 
super . validate ( ) ; 
this . initWindow ( ) ; 
this . component . init ( ) ; 
this . component . start ( ) ; 
} 
 else { 
this . component = component ; 
this . add ( this . component ) ; 
this . component . init ( ) ; 
} 
 
} 
 
public abstract void init ( ) ; 
} 
 
