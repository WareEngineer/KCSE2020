package org . apogames ; 
public class ApoThread implements Runnable { 
private final long FPSUpdater = 1000000000 ; 
private ApoTimerInterface game ; 
private boolean bRunning ; 
private boolean bThink ; 
private boolean bRender ; 
private long waitThink ; 
private long waitRender ; 
private long delta ; 
private long last ; 
private long fps ; 
private long deltaThink ; 
private long deltaRender ; 
private long deltaSecond ; 
private long fpsCounter ; 
public ApoThread ( ApoTimerInterface game , long wait ) { 
this ( game , wait , wait ) ; 
} 
 
public ApoThread ( ApoTimerInterface game , long waitThink , long waitRender ) { 
this ( game , waitThink , waitRender , true , true ) ; 
} 
 
public ApoThread ( ApoTimerInterface game , long waitThink , long waitRender , boolean bThink , boolean bRender ) { 
super ( ) ; 
this . game = game ; 
this . waitThink = waitThink * 1000000 ; 
this . waitRender = waitRender * 1000000 ; 
this . bRunning = false ; 
this . setBThink ( bThink ) ; 
this . setBRender ( bRender ) ; 
} 
 
public boolean isBThink ( ) { 
return this . bThink ; 
} 
 
public void setBThink ( boolean bThink ) { 
this . bThink = bThink ; 
} 
 
public boolean isBRender ( ) { 
return bRender ; 
} 
 
public void setBRender ( boolean bRender ) { 
this . bRender = bRender ; 
} 
 
public long getFps ( ) { 
return this . fps ; 
} 
 
public long getWait ( ) { 
return this . waitThink ; 
} 
 
public void setWait ( long wait ) { 
this . waitThink = wait ; 
} 
 
public long getWaitRender ( ) { 
return this . waitRender ; 
} 
 
public void setWaitRender ( long wait ) { 
this . waitRender = wait ; 
this . deltaRender = 0 ; 
} 
 
public void start ( ) { 
if ( ! this . isBRunning ( ) ) { 
this . setBRunning ( true ) ; 
this . last = System . nanoTime ( ) ; 
this . deltaThink = 0 ; 
this . deltaRender = 0 ; 
this . deltaSecond = 0 ; 
this . fpsCounter = 0 ; 
Thread t = new Thread ( this ) ; 
t . start ( ) ; 
} 
 
} 
 
public void stop ( ) { 
if ( this . isBRunning ( ) ) { 
this . setBRunning ( false ) ; 
} 
 
} 
 
public boolean isBRunning ( ) { 
return this . bRunning ; 
} 
 
public void setBRunning ( boolean bRunning ) { 
this . bRunning = bRunning ; 
} 
 
public void run ( ) { 
while ( this . isBRunning ( ) ) { 
this . computeDelta ( ) ; 
this . think ( ) ; 
this . render ( ) ; 
try { 
Thread . sleep ( 10 ) ; 
} 
 catch ( InterruptedException e ) { 
} 
 
} 
 
} 
 
private boolean computeDelta ( ) { 
long now = System . nanoTime ( ) ; 
this . delta = now - this . last ; 
this . last = now ; 
this . deltaThink += this . delta ; 
this . deltaRender += this . delta ; 
this . deltaSecond += this . delta ; 
while ( this . deltaSecond > this . FPSUpdater ) { 
this . deltaSecond -= this . FPSUpdater ; 
this . fps = this . fpsCounter ; 
this . fpsCounter = 0 ; 
} 
 
return true ; 
} 
 
public boolean setRestart ( ) { 
this . last = System . nanoTime ( ) ; 
this . deltaThink = 0 ; 
this . deltaRender = 0 ; 
this . deltaSecond = 0 ; 
this . fpsCounter = 0 ; 
return true ; 
} 
 
private boolean think ( ) { 
while ( this . deltaThink >= this . getWait ( ) ) { 
this . deltaThink -= this . getWait ( ) ; 
if ( ( this . game . isBThink ( ) ) && ( this . isBThink ( ) ) ) { 
this . game . think ( ( int ) this . getWait ( ) / 1000000 ) ; 
} 
 
} 
 
return true ; 
} 
 
private boolean render ( ) { 
int count = 0 ; 
while ( ( count < 1 ) && ( this . deltaRender >= this . getWaitRender ( ) ) ) { 
this . deltaRender -= this . getWaitRender ( ) ; 
count ++ ; 
if ( ( this . game . isBRepaint ( ) ) && ( this . isBRender ( ) ) ) { 
this . game . render ( ) ; 
} 
 
this . fpsCounter ++ ; 
} 
 
return true ; 
} 
 
} 
 
