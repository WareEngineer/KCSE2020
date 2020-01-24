package org . apogames . help ; 
import java . io . File ; 
import java . io . FileInputStream ; 
import java . io . FileOutputStream ; 
import java . io . IOException ; 
public class ApoCopy { 
public ApoCopy ( ) { 
} 
 
public void copyDirectory ( String pSrcDirectory , String pCopyDirectory ) { 
File srcDirectory = new File ( pSrcDirectory ) ; 
if ( ! srcDirectory . exists ( ) ) { 
return ; 
} 
 
File copyDirectory = new File ( pCopyDirectory ) ; 
if ( ! copyDirectory . exists ( ) ) { 
copyDirectory . mkdirs ( ) ; 
} 
 
File [ ] files = srcDirectory . listFiles ( ) ; 
for ( int i = 0 ; i < files . length ; i ++ ) { 
if ( ! files [ i ] . isDirectory ( ) ) { 
String fileName = files [ i ] . getPath ( ) . substring ( files [ i ] . getPath ( ) . lastIndexOf ( File . separator ) ) ; 
if ( ! new File ( pCopyDirectory + File . separator + fileName ) . exists ( ) ) { 
this . copyFile ( files [ i ] . getPath ( ) , pCopyDirectory + File . separator + fileName ) ; 
} 
 
} 
 
} 
 
} 
 
public void copyFile ( String pSrcFile , String pCopyFile ) { 
FileInputStream fis = null ; 
FileOutputStream fos = null ; 
try { 
fis = new FileInputStream ( pSrcFile ) ; 
fos = new FileOutputStream ( pCopyFile ) ; 
byte [ ] buffer = new byte [ 0xFFFF ] ; 
for ( int len ; ( len = fis . read ( buffer ) ) != - 1 ; ) { 
fos . write ( buffer , 0 , len ) ; 
} 
 
} 
 catch ( IOException e ) { 
e . printStackTrace ( ) ; 
} 
 
finally { 
if ( fis != null ) { 
try { 
fis . close ( ) ; 
} 
 catch ( IOException e ) { 
e . printStackTrace ( ) ; 
} 
 
} 
 
if ( fos != null ) { 
try { 
fos . close ( ) ; 
} 
 catch ( IOException e ) { 
e . printStackTrace ( ) ; 
} 
 
} 
 
} 
 
} 
 
} 
 
