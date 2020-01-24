package org . apogames ; 
import java . awt . Graphics ; 
import java . awt . Graphics2D ; 
import java . awt . event . FocusEvent ; 
import java . awt . event . FocusListener ; 
import java . awt . event . KeyEvent ; 
import java . awt . event . KeyListener ; 
import java . awt . event . MouseEvent ; 
import java . awt . event . MouseListener ; 
import java . awt . event . MouseMotionListener ; 
import java . awt . event . MouseWheelEvent ; 
import java . awt . event . MouseWheelListener ; 
import javax . swing . JComponent ; 
import org . apogames . entity . ApoButton ; 
public abstract class ApoComponent extends JComponent implements ApoTimerInterface , KeyListener , MouseListener , MouseMotionListener , MouseWheelListener , FocusListener { 
private static final long serialVersionUID = 1L ; 
private boolean [ ] bDirections ; 
private boolean bThink ; 
private boolean bRepaint ; 
private boolean bBack ; 
private boolean bWin ; 
private boolean bHelp ; 
private boolean bLoose ; 
private ApoThread threadThinkAndRender ; 
private ApoThread threadThink ; 
private ApoThread threadRender ; 
private boolean bMouse ; 
private boolean bKey ; 
private int timerThread ; 
private int WAIT_TIME ; 
private ApoButton [ ] buttons ; 
public ApoComponent ( boolean bMouse , boolean bKey , int wait_time ) { 
this ( bMouse , bKey , wait_time , ApoConstants . ONE_THREADS ) ; 
} 
 
public ApoComponent ( boolean bMouse , boolean bKey , int wait_time , int timerThread ) { 
super ( ) ; 
this . setFocusable ( true ) ; 
this . setBRepaint ( true ) ; 
this . setBThink ( true ) ; 
this . bMouse = bMouse ; 
this . bKey = bKey ; 
this . timerThread = timerThread ; 
this . WAIT_TIME = wait_time ; 
this . bBack = false ; 
this . bWin = false ; 
this . bLoose = false ; 
this . bDirections = new boolean [ 4 ] ; 
} 
 
public ApoButton [ ] getButtons ( ) { 
return this . buttons ; 
} 
 
public void setButtons ( ApoButton [ ] buttons ) { 
this . buttons = buttons ; 
} 
 
public abstract void init ( ) ; 
public void keyTyped ( KeyEvent e ) { 
} 
 
public void keyPressed ( KeyEvent e ) { 
int keyCode = e . getKeyCode ( ) ; 
if ( keyCode == KeyEvent . VK_UP ) { 
this . bDirections [ ApoConstants . UP ] = true ; 
} 
 
if ( keyCode == KeyEvent . VK_DOWN ) { 
this . bDirections [ ApoConstants . DOWN ] = true ; 
} 
 
if ( keyCode == KeyEvent . VK_LEFT ) { 
this . bDirections [ ApoConstants . LEFT ] = true ; 
} 
 
if ( keyCode == KeyEvent . VK_RIGHT ) { 
this . bDirections [ ApoConstants . RIGHT ] = true ; 
} 
 
if ( keyCode == KeyEvent . VK_U ) { 
this . setBBack ( true ) ; 
} 
 
} 
 
public void keyReleased ( KeyEvent e ) { 
int keyCode = e . getKeyCode ( ) ; 
if ( keyCode == KeyEvent . VK_UP ) this . bDirections [ ApoConstants . UP ] = false ; 
if ( keyCode == KeyEvent . VK_DOWN ) this . bDirections [ ApoConstants . DOWN ] = false ; 
if ( keyCode == KeyEvent . VK_LEFT ) this . bDirections [ ApoConstants . LEFT ] = false ; 
if ( keyCode == KeyEvent . VK_RIGHT ) this . bDirections [ ApoConstants . RIGHT ] = false ; 
if ( keyCode == KeyEvent . VK_U ) this . setBBack ( false ) ; 
if ( keyCode == KeyEvent . VK_H ) this . setBHelp ( ! this . isBHelp ( ) ) ; 
} 
 
public abstract void think ( int delta ) ; 
public void render ( ) { 
if ( this . isBRepaint ( ) ) { 
this . repaint ( ) ; 
} 
 
} 
 
public void paintComponent ( Graphics g ) { 
try { 
this . render ( ( Graphics2D ) g ) ; 
} 
 catch ( NullPointerException ex ) { 
return ; 
} 
 catch ( Exception ex ) { 
return ; 
} 
 
} 
 
public abstract void render ( Graphics2D g ) ; 
public void start ( ) { 
if ( ( this . threadThinkAndRender == null ) && ( this . timerThread == ApoConstants . ONE_THREADS ) ) { 
this . setBThink ( true ) ; 
this . threadThinkAndRender = new ApoThread ( this , WAIT_TIME ) ; 
this . threadThinkAndRender . start ( ) ; 
} 
 else if ( ( this . threadThink == null ) && ( this . threadRender == null ) && ( this . timerThread == ApoConstants . TWO_THREADS ) ) { 
this . setBThink ( true ) ; 
this . threadThink = new ApoThread ( this , WAIT_TIME ) ; 
this . threadThink . setBRender ( false ) ; 
this . threadThink . start ( ) ; 
this . threadRender = new ApoThread ( this , WAIT_TIME ) ; 
this . threadRender . setBThink ( false ) ; 
this . threadRender . start ( ) ; 
} 
 
if ( this . bMouse ) { 
this . addMouseListener ( this ) ; 
this . addMouseMotionListener ( this ) ; 
this . addMouseWheelListener ( this ) ; 
} 
 
if ( this . bKey ) { 
this . addFocusListener ( this ) ; 
if ( ! ApoConstants . B_APPLET ) { 
this . addKeyListener ( this ) ; 
} 
 else { 
this . addKeyListener ( this ) ; 
} 
 
} 
 
} 
 
public void stop ( ) { 
if ( ( this . threadThinkAndRender != null ) && ( this . timerThread == ApoConstants . ONE_THREADS ) ) { 
this . setBThink ( false ) ; 
this . threadThinkAndRender . stop ( ) ; 
this . threadThinkAndRender = null ; 
} 
 else if ( ( this . threadThink != null ) && ( this . threadRender != null ) && ( this . timerThread == ApoConstants . TWO_THREADS ) ) { 
this . setBThink ( false ) ; 
this . threadThink . stop ( ) ; 
this . threadThink = null ; 
this . threadRender . stop ( ) ; 
this . threadRender = null ; 
} 
 
if ( this . bMouse ) { 
this . removeMouseListener ( this ) ; 
this . removeMouseMotionListener ( this ) ; 
this . removeMouseWheelListener ( this ) ; 
} 
 
if ( this . bKey ) { 
if ( ! ApoConstants . B_APPLET ) { 
this . removeKeyListener ( this ) ; 
} 
 else { 
this . removeKeyListener ( this ) ; 
} 
 
} 
 
} 
 
public boolean setRestartThreadValues ( ) { 
if ( this . timerThread == ApoConstants . ONE_THREADS ) { 
this . threadThinkAndRender . setRestart ( ) ; 
} 
 else if ( this . timerThread == ApoConstants . TWO_THREADS ) { 
this . threadThink . setRestart ( ) ; 
this . threadRender . setRestart ( ) ; 
} 
 
return true ; 
} 
 
public int getFPS ( ) { 
if ( this . timerThread == ApoConstants . ONE_THREADS ) { 
if ( this . threadThinkAndRender != null ) { 
return ( int ) this . threadThinkAndRender . getFps ( ) ; 
} 
 
} 
 else if ( this . timerThread == ApoConstants . TWO_THREADS ) { 
if ( this . threadRender != null ) { 
return ( int ) this . threadRender . getFps ( ) ; 
} 
 
} 
 
return - 1 ; 
} 
 
public void renderFPS ( Graphics2D g , int x , int y ) { 
g . drawString ( "FPS: " + this . getFPS ( ) , x , y ) ; 
} 
 
public void renderButtons ( Graphics2D g ) { 
if ( this . getButtons ( ) != null ) { 
for ( int i = 0 ; i < this . getButtons ( ) . length ; i ++ ) { 
this . getButtons ( ) [ i ] . render ( g , 0 , 0 ) ; 
} 
 
} 
 
} 
 
public boolean isBThink ( ) { 
return this . bThink ; 
} 
 
public void setBThink ( boolean bThink ) { 
this . bThink = bThink ; 
} 
 
public boolean isBRepaint ( ) { 
return this . bRepaint ; 
} 
 
public void setBRepaint ( boolean bRepaint ) { 
this . bRepaint = bRepaint ; 
} 
 
public boolean [ ] getBDirections ( ) { 
return this . bDirections ; 
} 
 
public void setBDirections ( boolean [ ] bDirections ) { 
this . bDirections = bDirections ; 
} 
 
public boolean isBBack ( ) { 
return this . bBack ; 
} 
 
public void setBBack ( boolean bBack ) { 
this . bBack = bBack ; 
} 
 
public boolean isBLoose ( ) { 
return this . bLoose ; 
} 
 
public void setBLoose ( boolean bLoose ) { 
this . bLoose = bLoose ; 
} 
 
public boolean isBWin ( ) { 
return this . bWin ; 
} 
 
public void setBWin ( boolean bWin ) { 
this . bWin = bWin ; 
} 
 
public boolean isBHelp ( ) { 
return this . bHelp ; 
} 
 
public void setBHelp ( boolean bHelp ) { 
this . bHelp = bHelp ; 
} 
 
public void mouseMoved ( MouseEvent e ) { 
int x = e . getX ( ) ; 
int y = e . getY ( ) ; 
if ( this . buttons != null ) { 
for ( int i = 0 ; i < this . buttons . length ; i ++ ) { 
if ( this . buttons [ i ] . getMove ( x , y ) ) { 
if ( ( this . timerThread == ApoConstants . NO_THREAD ) || ( ! this . bRepaint ) ) this . repaint ( ) ; 
break ; 
} 
 
} 
 
} 
 
} 
 
public void mousePressed ( MouseEvent e ) { 
int x = e . getX ( ) ; 
int y = e . getY ( ) ; 
if ( this . buttons != null ) { 
for ( int i = 0 ; i < this . buttons . length ; i ++ ) { 
if ( this . buttons [ i ] . getPressed ( x , y ) ) { 
if ( ( this . timerThread == ApoConstants . NO_THREAD ) || ( ! this . bRepaint ) ) { 
this . repaint ( ) ; 
} 
 
break ; 
} 
 
} 
 
} 
 
} 
 
public void mouseReleased ( MouseEvent e ) { 
int x = e . getX ( ) ; 
int y = e . getY ( ) ; 
if ( this . buttons != null ) { 
for ( int i = 0 ; i < this . buttons . length ; i ++ ) { 
if ( this . buttons [ i ] . getReleased ( x , y ) ) { 
String function = this . buttons [ i ] . getFunction ( ) ; 
this . setButtonFunction ( function ) ; 
} 
 
} 
 
} 
 
if ( ( this . timerThread == ApoConstants . NO_THREAD ) || ( ! this . bRepaint ) ) { 
this . repaint ( ) ; 
} 
 
} 
 
public void mouseDragged ( MouseEvent e ) { 
} 
 
public void mouseClicked ( MouseEvent e ) { 
} 
 
public void mouseEntered ( MouseEvent e ) { 
} 
 
public void mouseExited ( MouseEvent e ) { 
} 
 
public void mouseWheelMoved ( MouseWheelEvent e ) { 
} 
 
public abstract void setButtonFunction ( String function ) ; 
public void focusGained ( FocusEvent e ) { 
} 
 
public void focusLost ( FocusEvent e ) { 
} 
 
} 
 
