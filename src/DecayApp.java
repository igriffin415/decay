import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class DecayApp extends PApplet{
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	//float width, heightE;
	
	PGraphics buffer;
	
	String recordingFile = "test.kinect";
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();
	private int r, g, b; //current color values
	private static int sr = 0, sg = 153, sb = 255; //final values for the sun state
	private static int mr = 150, mg = 0, mb = 50; //final values for the moon state
	private float sunY;
	private float moonY;
	private float setRate = .005f;
	Decay currentState = Decay.Sun;
	Person p;
	Iterator<Body> i;
	
	LinkedHashMap<Long, Person> people;
	
	PImage fadeImage, sunImage, moonImage;
	
	
	public void settings() {
		createWindow(true, false, .5f);
	}

	public void setup() {	
		try {
			kinectReader = new KinectBodyDataProvider("test2.kinect", 1);
		} catch (IOException e) {
			System.out.println("Unable to create kinect producer");
		}
				 
		//kinectReader = new KinectBodyDataProvider(8008);
		
		//set initial colors
		r = sr;
		g = sg;
		b = sb;
		
		//set planet coords
		sunY = .5f;
		moonY = -3.5f;
		
		people = new LinkedHashMap<Long, Person>();
		fadeImage = loadImage("assets/featuredbgfadeflipped.png");
		sunImage = loadImage("assets/sun.png");
		moonImage = loadImage("assets/moon.png");
		
		kinectReader.start();
	}

	public void draw() {
		setScale(0.5f);
		imageMode(CENTER);
		background(0, 0, 0);

		KinectBodyData bodyData = kinectReader.getMostRecentData();
		tracker.update(bodyData);
		
		drawBackground(currentState);
		
		i = tracker.getPeople().values().iterator();
		
		
		if (i.hasNext()) {
			Body b = i.next();
			p = people.get(b.getId());
			if (p == null) {
				p = new Person(this);
				people.put(b.getId(), p);
			}
			while (i.hasNext()) {
				Body b2 = i.next();
				if (!tracker.getPeople().keySet().contains(b2.getId()))
					i.remove();
			}

			currentState = p.getState();

			p.update(b);
			p.draw();
		}
	}

	private void drawBackground(Decay currentState) {
		if (currentState == Decay.Sun) {
			//move/draw the sun
			if(sunY < .5) 
				sunY += setRate;
			image(sunImage, 1, sunY, 1, 1);
			if(moonY > -3.5)
				moonY -= setRate;
			image(moonImage, -1, moonY, 1, 1);
			
			//de/increment colors
			if(r > sr)
				r--;
			if(g < sg)
				g++;
			if(b < sb)
				b++;
			//draw the background
			tint(r, g, b);
			image(fadeImage, 0, 0, this.displayWidth / 250, this.displayHeight / 250);
			tint(255, 255, 255);
		} else if (currentState == Decay.Moon) {
			//move/draw the moon
			if(moonY < .5)
				moonY += setRate;
			image(moonImage, -1, moonY, 1, 1);
			if(sunY > -3.5)
				sunY -= setRate;
			image(sunImage, 1, sunY, 1, 1);
			
			//de/increment colors
			if(r < mr)
				r++;
			if(g > mg)
				g--;
			if(b > mb)
				b--;
			//draw the background
			tint(r, g, b);
			image(fadeImage, 0, 0, this.displayWidth / 250, this.displayHeight / 250);
			tint(255,255,255);
		}
	}
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
		
		//height = 2f * (PROJECTOR_RATIO/zoom);
		//width = 2f * (1f/zoom);
		//System.out.println(this.height + " " + 2f * (PROJECTOR_RATIO/zoom));
		
	}
	
	public static void main(String[] args) {
		PApplet.main(DecayApp.class.getName());
	}
}
