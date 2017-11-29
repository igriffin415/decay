import java.io.IOException;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class DecayApp extends PApplet{
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	
	float xoff = 0.0f;
	float yoff = 0.0f;
	float increment = 0.005f; 
	String recordingFile = "test.kinect";
	KinectBodyDataProvider kinectReader;
	PImage head;
	
	public void settings() {
		createWindow(false, false, .5f);
	}

	public void setup() {
		if(recordingFile != null)
			try {
				kinectReader = new KinectBodyDataProvider("test.kinect", 10);
			} catch (IOException e) {
				System.out.println("Unable to create kinect producer");
			}
		else {
			kinectReader = new KinectBodyDataProvider(8008);
		}
		
		head = loadImage("skull.png");;  // Declare variable "a" of type PImage
	}

	public void draw() {
	  // Create an alpha blended background
	  fill(0, 10);
	  rect(0,0,width,height);
	  noStroke();
	  
	  //float n = random(0,width);  // Try this line instead of noise
	  
	  // Get a noise value based on xoff and scale it according to the window's width
	  float n = noise(xoff)*width;
	  float ny = noise(yoff)*height;
	  
	  // With each cycle, increment xoff
	  xoff += increment;
	  yoff += increment;
	  
	  
	  // Draw the ellipse at the value produced by perlin noise
	  fill(200);
	  image(head, n, ny, head.width/10, head.height/10);
	  //ellipse(n,ny, 64, 64);
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
	}
	
	public static void main(String[] args) {
		PApplet.main(DecayApp.class.getName());
	}
}
