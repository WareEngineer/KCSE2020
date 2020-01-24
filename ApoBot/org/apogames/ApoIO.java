package org . apogames ; 
import java . io . BufferedInputStream ; 
import java . io . BufferedOutputStream ; 
import java . io . DataInputStream ; 
import java . io . DataOutputStream ; 
import java . io . EOFException ; 
import java . io . FileInputStream ; 
import java . io . FileOutputStream ; 
import java . io . IOException ; 
import java . net . HttpURLConnection ; 
import java . net . URL ; 
public abstract class ApoIO { 
private int maxLevel ; 
private int currentLevel ; 
private String levelName ; 
private String fileEnding ; 
public ApoIO ( ) { 
super ( ) ; 
this . setCurrentLevel ( - 1 ) ; 
this . setMaxLevel ( 0 ) ; 
} 
 
public ApoIO ( String levelName , String fileEnding ) { 
super ( ) ; 
this . setCurrentLevel ( - 1 ) ; 
this . setMaxLevel ( 0 ) ; 
this . setLevelName ( levelName ) ; 
this . setFileEnding ( fileEnding ) ; 
} 
 
public String getLevelName ( ) { 
return this . levelName ; 
} 
 
public void setLevelName ( String levelName ) { 
this . levelName = levelName ; 
} 
 
public String getFileEnding ( ) { 
return this . fileEnding ; 
} 
 
public void setFileEnding ( String fileEnding ) { 
this . fileEnding = fileEnding ; 
} 
 
public int getCurrentLevel ( ) { 
return this . currentLevel ; 
} 
 
public void setCurrentLevel ( int currentLevel ) { 
this . currentLevel = currentLevel ; 
} 
 
public int getMaxLevel ( ) { 
return this . maxLevel ; 
} 
 
public void setMaxLevel ( int maxLevel ) { 
this . maxLevel = maxLevel ; 
} 
 
public boolean hasNextLevel ( ) { 
if ( ( this . currentLevel + 1 <= 0 ) || ( this . currentLevel + 1 >= this . maxLevel ) ) return false ; else return true ; 
} 
 
public boolean hasNextLevel ( boolean bURL ) { 
try { 
DataInputStream data ; 
if ( ! bURL ) { 
FileInputStream file = new FileInputStream ( this . getLevelName ( ) + Integer . toString ( this . getCurrentLevel ( ) ) + this . getFileEnding ( ) ) ; 
BufferedInputStream buff = new BufferedInputStream ( file ) ; 
data = new DataInputStream ( buff ) ; 
} 
 else { 
URL connection = new URL ( this . getLevelName ( ) + Integer . toString ( this . getCurrentLevel ( ) ) + this . getFileEnding ( ) ) ; 
data = new DataInputStream ( connection . openStream ( ) ) ; 
} 
 
data . close ( ) ; 
return true ; 
} 
 catch ( IOException e ) { 
return false ; 
} 
 
} 
 
public void writeLevel ( String fileName ) { 
try { 
DataOutputStream data = null ; 
if ( ! ApoConstants . B_APPLET ) { 
FileOutputStream file = new FileOutputStream ( fileName ) ; 
BufferedOutputStream buff = new BufferedOutputStream ( file ) ; 
data = new DataOutputStream ( buff ) ; 
} 
 else { 
URL connectionURL = new URL ( fileName ) ; 
HttpURLConnection connection = ( HttpURLConnection ) connectionURL . openConnection ( ) ; 
connection . setDoOutput ( true ) ; 
data = new DataOutputStream ( connection . getOutputStream ( ) ) ; 
} 
 
if ( data != null ) { 
this . writeLevel ( data ) ; 
data . close ( ) ; 
} 
 
} 
 catch ( IOException e ) { 
System . out . println ( "Error: " + e ) ; 
} 
 
} 
 
public abstract boolean writeLevel ( DataOutputStream data ) throws IOException ; 
public boolean readLevel ( String fileName ) { 
return this . readLevel ( ApoConstants . B_APPLET , fileName ) ; 
} 
 
public boolean readLevel ( boolean bURL , String fileName ) { 
return this . readLevel ( bURL , fileName , false ) ; 
} 
 
public boolean readLevel ( boolean bURL , String fileName , boolean bAll ) { 
this . maxLevel = 0 ; 
this . currentLevel = 0 ; 
try { 
DataInputStream data ; 
if ( ! bURL ) { 
FileInputStream file = new FileInputStream ( fileName ) ; 
BufferedInputStream buff = new BufferedInputStream ( file ) ; 
data = new DataInputStream ( buff ) ; 
} 
 else { 
URL connection = new URL ( fileName ) ; 
data = new DataInputStream ( connection . openStream ( ) ) ; 
} 
 
try { 
this . readLevel ( data ) ; 
} 
 catch ( EOFException e ) { 
} 
 
data . close ( ) ; 
} 
 catch ( IOException e ) { 
System . out . println ( "Error: " + e ) ; 
return false ; 
} 
 
return true ; 
} 
 
public abstract boolean readLevel ( DataInputStream data ) throws EOFException , IOException ; 
} 
 
