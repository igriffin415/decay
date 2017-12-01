import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	Body body;
	PApplet app;
	PVector headv, ribsv;
	PImage head, ribs;
	
	public Person(PApplet app) {
		this.app = app;		
		head = app.loadImage("skull.png");
		ribs = app.loadImage("ribcase.png");
	}

	public void update(Body body) {
		headv = body.getJoint(Body.HEAD);
		ribsv = body.getJoint(Body.SPINE_BASE);
	}	
	
	public void drawHead() {
		if(headv != null) {
			app.image(head, headv.x, headv.y,
					  head.width/60, head.height/60);
		}
	}
	
	public void drawRibs() {
		if(ribsv != null) {
			app.image(ribs, ribsv.x, ribsv.y - .25f,
					  ribs.width/100, ribs.height/100);
		}
	}
	
	public PVector getHead() {
		PVector head = body.getJoint(Body.HEAD);
		if(head != null)
			return head;
		return null;
	}
}