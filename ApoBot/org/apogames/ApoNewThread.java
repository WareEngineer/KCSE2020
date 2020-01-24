package org . apogames ; 
public class ApoNewThread implements Runnable { 
private ApoTimerInterface game ; 
private boolean bRunning ; 
private boolean bThink ; 
private boolean bRender ; 
private long waitThink ; 
private long waitRender ; 
private float frameAverage = 0 ; 
private long lastFrame = System . currentTimeMillis ( ) ; 
private float yield = 10000f ; 
private float damping = 0.1f ; 
private long lastSecond = this . lastFrame ; 
private int fps ; 
private int draws ; 
public ApoNewThread ( ApoTimerInterface game , long wait ) { 
this ( game , wait , wait ) ; 
} 
 
public ApoNewThread ( ApoTimerInterface game , long waitThink , long waitRender ) { 
this ( game , waitThink , waitRender , true , true ) ; 
} 
 
public ApoNewThread ( ApoTimerInterface game , long waitThink , long waitRender , boolean bThink , boolean bRender ) { 
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
} 
 
public void start ( ) { 
if ( ! this . isBRunning ( ) ) { 
this . setBRunning ( true ) ; 
this . frameAverage = this . waitRender ; 
this . lastFrame = System . currentTimeMillis ( ) ; 
this . yield = 10000f ; 
this . damping = 0.1f ; 
this . lastSecond = lastFrame ; 
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
} 
 
} 
 
private boolean computeDelta ( ) { 
long timeNow = System . currentTimeMillis ( ) ; 
this . frameAverage = ( this . frameAverage * 10 + ( timeNow - this . lastFrame ) ) / 11 ; 
this . lastFrame = timeNow ; 
this . yield += this . yield * ( ( this . waitRender / 1000000 / this . frameAverage ) - 1 ) * this . damping + 0.05f ; 
for ( int i = 0 ; i < this . yield ; i ++ ) { 
Thread . yield ( ) ; 
} 
 
if ( timeNow - this . lastSecond >= 1000 ) { 
this . fps = this . draws ; 
this . draws = 0 ; 
this . lastSecond = timeNow ; 
} 
 
return true ; 
} 
 
public boolean setRestart ( ) { 
this . frameAverage = this . waitRender ; 
this . lastFrame = System . currentTimeMillis ( ) ; 
this . yield = 10000f ; 
this . damping = 0.1f ; 
this . lastSecond = lastFrame ; 
return true ; 
} 
 
private boolean think ( ) { 
if ( ( this . game . isBThink ( ) ) && ( this . isBThink ( ) ) ) { 
this . game . think ( ( int ) this . getWait ( ) / 1000000 ) ; 
} 
 
return true ; 
} 
 
private boolean render ( ) { 
if ( ( this . game . isBRepaint ( ) ) && ( this . isBRender ( ) ) ) { 
this . game . render ( ) ; 
} 
 
this . draws ++ ; 
return true ; 
} 
 
} 
 
