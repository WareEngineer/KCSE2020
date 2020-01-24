package org . apogames ; 
import javax . swing . JApplet ; 
import javax . swing . JComponent ; 
public class ApoAppletBufferedStrategy extends JApplet { 
private static final long serialVersionUID = 1L ; 
private JComponent currentComponent ; 
private ApoComponentBufferedStrategy component ; 
public static ApoAppletBufferedStrategy main ; 
private int width , height ; 
public ApoAppletBufferedStrategy ( int width , int height ) { 
super ( ) ; 
this . width = width ; 
this . height = height ; 
} 
 
public void init ( ) { 
main = this ; 
ApoConstants . B_APPLET = true ; 
this . setSize ( this . width , this . height ) ; 
} 
 
public void destroy ( ) { 
if ( this . component != null ) { 
this . component . stop ( ) ; 
} 
 
this . component = null ; 
this . currentComponent = null ; 
System . gc ( ) ; 
} 
 
public void setComponent ( ApoComponentBufferedStrategy component ) { 
if ( this . component != null ) { 
this . component . stop ( ) ; 
this . currentComponent = ( JComponent ) this . getContentPane ( ) ; 
this . currentComponent . remove ( this . component ) ; 
this . component = component ; 
this . currentComponent . add ( this . component ) ; 
super . validate ( ) ; 
this . currentComponent . repaint ( ) ; 
this . component . init ( ) ; 
this . component . start ( ) ; 
} 
 else { 
this . currentComponent = ( JComponent ) this . getContentPane ( ) ; 
this . component = component ; 
this . currentComponent . add ( this . component ) ; 
this . component . init ( ) ; 
this . component . start ( ) ; 
} 
 
} 
 
} 
 
