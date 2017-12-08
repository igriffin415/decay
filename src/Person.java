import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	private Body body;
	private PApplet app;
	private PVector headv, ribsv, lefthandv, righthandv;
	private PImage head, ribs;
	private Bouquet flowers;
	private boolean disappear = false; //true if flowers should be disappearing
	PImage headFlower;
	
	private Decay state;
	
	float[] prevPosHead;
	float[] prevPosRibs;
	
	public Person(PApplet app) {
		this.app = app;		
		head = app.loadImage("assets/goathead.png");
		ribs = app.loadImage("assets/ribcase.png");
		flowers = new Bouquet(app);
		headFlower = app.loadImage("assets/pinkflower.png");
		prevPosHead = new float[] {0.0f, 0.0f};
		prevPosRibs = new float[] {0.0f, 0.0f};
		state = Decay.Sun;
	}

	public void update(Body body) {
		headv = body.getJoint(Body.HEAD);
		ribsv = body.getJoint(Body.SPINE_BASE);
		
		//track hands
		lefthandv = body.getJoint(Body.HAND_LEFT);
		righthandv = body.getJoint(Body.HAND_RIGHT);
	}	
	
	/**
	 * Display the background appropriate to the current state
	 */
	public void drawBackground() {
		if(state == Decay.Sun) {
			//nice background
		} else if(state == Decay.Moon) {
			//vein thing
		}
	}
	
	public Decay getState() {
		return state;
	}
	
	public void drawHead() {
		if(headv != null) {
			prevPosHead[0] = headv.x;
			prevPosHead[1] = headv.y;
			
			app.image(head, headv.x, headv.y+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			if (disappear) {
				app.tint(230, 0, 150);
				app.image(headFlower, headv.x, headv.y + .2f, .2f, .2f);
				app.tint(255,255,255);
			} else {
				app.tint(255, 255, 255);
				app.image(headFlower, headv.x, headv.y + .2f, .2f, .2f);
			}
		}
		else {
			app.image(head, prevPosHead[0], prevPosHead[1]+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			app.image(headFlower, prevPosHead[0], prevPosHead[1]+.2f, .2f, .2f);
			if(disappear) {
				//make it black
				app.tint(0);
			} else {
				//color
				app.tint(255);
			}
		}
	}
	
	public void drawRibs() {
		if(ribsv != null) {
			prevPosRibs[0] = ribsv.x;
			prevPosRibs[1] = ribsv.y;
			
			//draw ribs themselves
			app.image(ribs, ribsv.x, ribsv.y - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(ribsv.x, ribsv.y, disappear);
			if (stateChange && disappear) {
				state = Decay.Moon;
			}
			if (stateChange && !disappear) {
				state = Decay.Sun;
			}
		}
		else {
			app.image(ribs, prevPosRibs[0], prevPosRibs[1] - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(prevPosRibs[0], prevPosRibs[1], disappear);
			if (stateChange && disappear) {
				state = Decay.Moon;
			}
			if (stateChange && !disappear) {
				state = Decay.Sun;
			}
		}
	}
	
	public PVector getHead() {
		return headv;
	}
	
	public PVector getSpine() {
		return ribsv;
	}
	
	private void flowerCheck() {
		if(ribsv != null) {
			if(ribsv.x < 0) {
				disappear = true;
			}
			else {
				disappear = false;
			}
		}
		
		//if the hands are in front of the face-ish
//		if(lefthandv != null && righthandv != null) {
//			if(Math.abs(lefthandv.x-headv.x) < 1 && Math.abs(righthandv.x-headv.x) < 1
//					&& Math.abs(lefthandv.y-headv.y) < 1 && Math.abs(righthandv.y-headv.y) < 1) {
//				if(lefthandv.z < headv.z && righthandv.z < headv.z) {
//					disappear = true;
//				} else {
//					disappear = false;
//				}
//			} else {
//				disappear = false;
//			}
//		}
	}
}