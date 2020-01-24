package org . apogames ; 
import java . awt . Canvas ; 
import java . awt . Graphics ; 
import java . awt . Graphics2D ; 
import java . awt . GraphicsConfiguration ; 
import java . awt . GraphicsEnvironment ; 
import java . awt . event . FocusEvent ; 
import java . awt . event . FocusListener ; 
import java . awt . event . KeyEvent ; 
import java . awt . event . KeyListener ; 
import java . awt . event . MouseEvent ; 
import java . awt . event . MouseListener ; 
import java . awt . event . MouseMotionListener ; 
import java . awt . event . MouseWheelEvent ; 
import java . awt . event . MouseWheelListener ; 
import java . awt . image . BufferStrategy ; 
import java . awt . image . VolatileImage ; 
import org . apogames . entity . ApoButton ; 
public abstract class ApoComponentBufferedStrategy extends Canvas implements ApoTimerInterface , KeyListener , MouseListener , MouseMotionListener , MouseWheelListener , FocusListener { 
private static final long serialVersionUID = 1L ; 
private boolean [ ] bDirections ; 
private boolean bThink ; 
private boolean bRepaint ; 
private boolean bBack ; 
private boolean bWin ; 
private boolean bHelp ; 
private boolean bLoose ; 
private boolean bStrategy ; 
private ApoThread thread ; 
private boolean bMouse ; 
private boolean bKey ; 
private boolean bTimer ; 
private int WAIT_TIME_THINK , WAIT_TIME_RENDER ; 
private ApoButton [ ] buttons ; 
private VolatileImage backbuffer ; 
private GraphicsEnvironment ge ; 
private GraphicsConfiguration gc ; 
private BufferStrategy strategy ; 
public ApoComponentBufferedStrategy ( boolean bMouse , boolean bKey , int wait_time ) { 
this ( bMouse , bKey , wait_time , wait_time , true ) ; 
} 
 
public ApoComponentBufferedStrategy ( boolean bMouse , boolean bKey , int wait_time_think , int wait_time_render ) { 
this ( bMouse , bKey , wait_time_think , wait_time_render , true ) ; 
} 
 
public ApoComponentBufferedStrategy ( boolean bMouse , boolean bKey , int wait_time_think , int wait_time_render , boolean bTimer ) { 
super ( ) ; 
this . ge = GraphicsEnvironment . getLocalGraphicsEnvironment ( ) ; 
this . gc = this . ge . getDefaultScreenDevice ( ) . getDefaultConfiguration ( ) ; 
this . setFocusable ( true ) ; 
super . setIgnoreRepaint ( true ) ; 
this . setBRepaint ( true ) ; 
this . setBThink ( true ) ; 
this . bMouse = bMouse ; 
this . bKey = bKey ; 
this . bTimer = bTimer ; 
this . WAIT_TIME_THINK = wait_time_think ; 
this . WAIT_TIME_RENDER = wait_time_render ; 
this . bBack = false ; 
this . bWin = false ; 
this . bLoose = false ; 
this . bStrategy = false ; 
this . bDirections = new boolean [ 4 ] ; 
} 
 
public int getWAIT_TIME_RENDER ( ) { 
return this . WAIT_TIME_RENDER ; 
} 
 
public void setWAIT_TIME_RENDER ( int wait_time_render ) { 
this . WAIT_TIME_RENDER = wait_time_render ; 
if ( this . thread != null ) { 
this . thread . setWaitRender ( wait_time_render * 1000000 ) ; 
} 
 
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
 
public abstract void setButtonFunction ( String function ) ; 
public abstract void think ( int delta ) ; 
public abstract void render ( Graphics2D g ) ; 
private void createBackbuffer ( ) { 
if ( this . backbuffer != null ) { 
this . backbuffer . flush ( ) ; 
this . backbuffer = null ; 
} 
 
this . ge = GraphicsEnvironment . getLocalGraphicsEnvironment ( ) ; 
this . gc = this . ge . getDefaultScreenDevice ( ) . getDefaultConfiguration ( ) ; 
this . backbuffer = this . gc . createCompatibleVolatileImage ( getWidth ( ) , getHeight ( ) ) ; 
} 
 
private void checkBackbuffer ( ) { 
if ( this . backbuffer == null ) { 
this . createBackbuffer ( ) ; 
} 
 
if ( this . backbuffer . validate ( this . gc ) == VolatileImage . IMAGE_INCOMPATIBLE ) { 
this . createBackbuffer ( ) ; 
} 
 
} 
 
public void render ( ) { 
if ( this . strategy == null ) { 
return ; 
} 
 
if ( this . bStrategy ) { 
checkBackbuffer ( ) ; 
Graphics2D g = ( Graphics2D ) this . backbuffer . getGraphics ( ) ; 
render ( g ) ; 
g . dispose ( ) ; 
Graphics g2 = this . strategy . getDrawGraphics ( ) ; 
g2 . drawImage ( this . backbuffer , 0 , 0 , this ) ; 
g2 . dispose ( ) ; 
if ( ! this . strategy . contentsLost ( ) ) { 
this . strategy . show ( ) ; 
} 
 else { 
this . strategy = getBufferStrategy ( ) ; 
} 
 
} 
 else { 
Graphics2D g = ( Graphics2D ) this . strategy . getDrawGraphics ( ) ; 
g . clearRect ( 0 , 0 , getWidth ( ) , getHeight ( ) ) ; 
this . render ( g ) ; 
g . dispose ( ) ; 
if ( ! this . strategy . contentsLost ( ) ) { 
this . strategy . show ( ) ; 
} 
 else { 
this . strategy = getBufferStrategy ( ) ; 
} 
 
} 
 
} 
 
public boolean setRestartThreadValues ( ) { 
if ( this . thread == null ) { 
return true ; 
} 
 
return this . thread . setRestart ( ) ; 
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
 
public void start ( ) { 
if ( ( this . thread == null ) && ( this . bTimer ) ) { 
super . createBufferStrategy ( 3 ) ; 
this . strategy = getBufferStrategy ( ) ; 
this . setBThink ( true ) ; 
this . thread = new ApoThread ( this , this . WAIT_TIME_THINK , this . WAIT_TIME_RENDER ) ; 
this . thread . start ( ) ; 
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
if ( ( this . thread != null ) && ( this . bTimer ) ) { 
this . setBThink ( false ) ; 
this . thread . stop ( ) ; 
this . thread = null ; 
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
 
public int getFPS ( ) { 
if ( ( this . thread != null ) && ( this . bTimer ) ) { 
return ( int ) this . thread . getFps ( ) ; 
} 
 
return - 1 ; 
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
if ( ( ! this . bTimer ) || ( ! this . bRepaint ) ) { 
this . render ( ) ; 
} 
 
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
if ( ( ! this . bTimer ) || ( ! this . bRepaint ) ) { 
this . render ( ) ; 
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
 
if ( ! this . buttons [ i ] . intersects ( x , y ) ) { 
this . buttons [ i ] . setBOver ( false ) ; 
} 
 
this . buttons [ i ] . setBPressed ( false ) ; 
} 
 
} 
 
if ( ( ! this . bTimer ) || ( ! this . bRepaint ) ) { 
this . render ( ) ; 
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
 
public void focusGained ( FocusEvent e ) { 
} 
 
public void focusLost ( FocusEvent e ) { 
} 
 
} 
 
