import processing.core.*;
 
public class DecayApp extends PApplet {
        // define a buffer to draw the rectangles on
		public static float PROJECTOR_RATIO = 1080f/1920.0f;
		public float top, bottom;
        PGraphics buffer;
        
        public void settings() {
    		createWindow(true, false, .5f);
    	}
        
        public void setup() {
             // create the buffer to be the same size as the applet
                buffer = createGraphics(width, height, P2D); 
        }
 
        
        public void draw() {  
        		
                // Fade everything on the applet
                noStroke();
                fill( 255, 255, 255, 100);
                rect(0, 0, width, height);

                // change color of circle paint depending on mouse button
                if (mousePressed)  {
                        stroke( 255, 255, 255 );
                        fill( 0,0,0 );
                }
                else { 
                        stroke( 0, 0, 0 );
                        fill( 255, 255, 255 );
                        
                        
                }
                

                // draw a rectangle on the buffer, offset by 100 pixels right and down
                // from the mouse cursor.               
                buffer.beginDraw();
                buffer.rectMode( CENTER );
                buffer.fill(255, 255, 255, 100);
                buffer.rect(0, 0, width, height);
                buffer.fill(100);
                buffer.rect( mouseX + 100 , mouseY + 100, 50, 50 );
                buffer.endDraw();
                // overlay the buffer on the applet
                image(buffer, 0, 0);
                
             // draw a circle where the mouse is located
                ellipse( mouseX, mouseY, 50, 50 );
                
            
        }
        
        public static void main(String[] args) {

    		PApplet.main(DecayApp.class.getName());
    	}
        
        public void createWindow(boolean useP2D, boolean isFullscreen, float windowsScale) {
    		if (useP2D) {
    			if(isFullscreen) {
    				fullScreen(P2D);  			
    			} else {
    				size((int)(1920 * windowsScale), (int)(1080 * windowsScale), P2D);
    			}
    		} else {
    			if(isFullscreen) {
    				fullScreen();  			
    			} else {
    				size((int)(1920 * windowsScale), (int)(1080 * windowsScale));
    			}
    		}		
    	}

    	// use lower numbers to zoom out (show more of the world)
    	// zoom of 1 means that the window is 2 meters wide and appox 1 meter tall.
    	public void setScale(float zoom) {
    		scale(zoom* width/2.0f, zoom * -width/2.0f);
    		translate(1f/zoom , -PROJECTOR_RATIO/zoom );
    		top = 1f/zoom;
    		bottom = -PROJECTOR_RATIO/zoom;
    		
    	}
}
