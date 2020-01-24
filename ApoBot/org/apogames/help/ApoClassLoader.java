package org . apogames . help ; 
import java . io . File ; 
import java . net . MalformedURLException ; 
import java . net . URL ; 
import java . net . URLClassLoader ; 
import org . apogames . ApoConstants ; 
public class ApoClassLoader { 
private String path ; 
private String classname ; 
public ApoClassLoader ( String path , String classname ) { 
this . setPath ( path ) ; 
this . setClassname ( classname ) ; 
} 
 
public String getClassname ( ) { 
return this . classname ; 
} 
 
public void setClassname ( String classname ) { 
this . classname = classname ; 
} 
 
public String getPath ( ) { 
return this . path ; 
} 
 
public void setPath ( String path ) { 
this . path = path ; 
} 
 
public Object getMyClass ( ) { 
try { 
URL url ; 
if ( ! ApoConstants . B_APPLET ) url = new File ( this . getPath ( ) ) . toURI ( ) . toURL ( ) ; else url = new URL ( this . getPath ( ) ) ; 
URLClassLoader cl = new URLClassLoader ( new URL [ ] { 
url } 
 ) ; 
Class < ? > c ; 
try { 
c = cl . loadClass ( this . getClassname ( ) ) ; 
try { 
return c . newInstance ( ) ; 
} 
 catch ( InstantiationException e ) { 
e . printStackTrace ( ) ; 
} 
 catch ( IllegalAccessException e ) { 
e . printStackTrace ( ) ; 
} 
 
} 
 catch ( ClassNotFoundException e ) { 
e . printStackTrace ( ) ; 
} 
 
} 
 catch ( MalformedURLException e ) { 
e . printStackTrace ( ) ; 
} 
 
return null ; 
} 
 
} 
 
