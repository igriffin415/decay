import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class DecayApp extends PApplet{
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	float top, bottom;
	
	PGraphics buffer;
	
	String recordingFile = "test.kinect";
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();
	
	LinkedHashMap<Long, Person> people;
	
	PImage fadeImage;
	
	
	public void settings() {
		createWindow(true, false, .5f);
	}

	public void setup() {	
		try {
			kinectReader = new KinectBodyDataProvider("test.kinect", 10);
		} catch (IOException e) {
			System.out.println("Unable to create kinect producer");
		}
				 
		//kinectReader = new KinectBodyDataProvider(8008);
		
		people = new LinkedHashMap<Long, Person>();
		
		kinectReader.start();
	}

	public void draw() {
		fadeImage = loadImage("assets/featuredBGFade.png");
		fadeImage.resize(this.displayWidth, this.displayHeight);
		setScale(0.5f);
		imageMode(CENTER);
		background(0,0,0);
		
		KinectBodyData bodyData = kinectReader.getData();
		
		tracker.update(bodyData);
		
		 Iterator<Body> i = tracker.getPeople().values().iterator();
		 if(i.hasNext()) {
			Body b = i.next();
			Person p;
			p = people.get(b.getId());
			if(p== null) {
				 p = new Person(this);
				 people.put(b.getId(), p);
			}
			for(Long id : people.keySet()) {
				if(! tracker.getPeople().keySet().contains(id)) {
					people.remove(id);
				}
			}

			p.update(b);
			p.draw();
			buffer = p.drawHandTrails();
			image(buffer, 0,0);
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
		top = 1f/zoom;
		bottom = -PROJECTOR_RATIO/zoom;
		
	}
	
	public static void main(String[] args) {
		PApplet.main(DecayApp.class.getName());
	}
}
