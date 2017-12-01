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
		ribsv = body.getJoint(Body.SPINE_MID);
	}	
	
	public void drawHead() {
		if(headv != null) {
			app.imageMode(PApplet.CENTER);
			app.noStroke();
			app.ellipse(headv.x, headv.y, .5f, .5f);
			app.image(head, headv.x, headv.y,
					  head.width/10, head.height/10);
		}
		
	}
	
	public PVector getHead() {
		PVector head = body.getJoint(Body.HEAD);
		if(head != null)
			return head;
		return null;
	}
}