import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	private static int TRAIL = 80;
	
	private Body body;
	private PApplet app;
	private PVector headv, ribsv, lefthandv, righthandv;
	private PImage head, ribs;
	private Bouquet flowers;
	private boolean disappear = false; //true if flowers should be disappearing
	PImage headFlower, smoke;
	private boolean noDecay = true;
	
	float[] prevPosHead;
	float[] prevPosRibs;

	PVector[] lefthandarray;
	PVector[] righthandarray;

	PVector prevPosLeft;
	PVector prevPosRight;

	int counter;
	
	PGraphics buffer;
	
	public Person(PApplet app) {
		this.app = app;		
		head = app.loadImage("assets/goathead.png");
		ribs = app.loadImage("assets/ribcase.png");
		smoke = app.loadImage("assets/smoke.png");
		flowers = new Bouquet(app);
		headFlower = app.loadImage("assets/pinkflower.png");
		prevPosHead = new float[] {0.0f, 0.0f};
		prevPosRibs = new float[] {0.0f, 0.0f};

		lefthandarray = new PVector[TRAIL];
		righthandarray = new PVector[TRAIL];

		prevPosLeft = new PVector(0,0,0);
		prevPosRight = new PVector(0,0,0);
		
		buffer = app.createGraphics(app.width, app.height, app.P2D);

		counter = 0;
	}

	public void update(Body body) {
		headv = body.getJoint(Body.HEAD);
		ribsv = body.getJoint(Body.SPINE_BASE);
		
		//track hands
		lefthandv = body.getJoint(Body.HAND_LEFT);
		righthandv = body.getJoint(Body.HAND_RIGHT);
		
		if(lefthandv != null){
			prevPosLeft = lefthandv;
			lefthandarray[counter] = lefthandv;

		} else{
			lefthandarray[counter] = prevPosLeft;
		}

		if(righthandv != null){
			prevPosRight = righthandv;
			righthandarray[counter] = righthandv;

		} else {
			righthandarray[counter] = prevPosRight;

		}
	}	
	
	public void draw() {
		this.drawHead();
		this.drawRibs();
		this.drawHandTrails();
	}
	
	
	public PGraphics drawHandTrails(){
		buffer.beginDraw();
       // buffer.imageMode(app.CENTER );
//        if(righthandv != null)
//        	buffer.image(smoke, righthandv.x, righthandv.y, .1f, .1f);
//        else
//        	buffer.image(smoke, prevPosRight.x, prevPosRight.y, .1f, .1f);
//        if(lefthandv != null)
//        	buffer.image(smoke, lefthandv.x, lefthandv.y, .1f, .1f);
//        else
//        	buffer.image(smoke, prevPosLeft.x, prevPosLeft.y, .1f, .1f);
        buffer.noStroke();
        buffer.fill(125);
        buffer.ellipse(0, 0, 0.1f, 0.1f);
//        if(righthandv != null) 
//        	buffer.ellipse(righthandv.x, righthandv.y, .1f, .1f);
//        else
//        	buffer.ellipse(prevPosRight.x, prevPosRight.y, .1f, .1f);
//        if(lefthandv != null)
//        	buffer.ellipse(lefthandv.x, lefthandv.y, .1f, .1f);
//        else
//        	buffer.ellipse(prevPosLeft.x, prevPosLeft.y, .1f, .1f);
        
        buffer.endDraw();
        app.image(buffer, 0, 0);
        
//        app.fill(125);
//        app.noStroke();
//        
//        if(lefthandv != null)
//        	app.ellipse(lefthandv.x, lefthandv.y, .1f, .1f);
//        else
//        	app.ellipse(prevPosLeft.x, prevPosLeft.y, .1f, .1f);
        
        return buffer;
        //app.image(buffer, 0, 0);
		
//		PVector leftH;
//		PVector rightH;
//		
//		//start at counter-1 and go backwards to counter
//		//drawing here vvv
//		
//		int start = counter - 1;
//		if(start < 0) {
//			start = TRAIL - (start * -1);  
//		}
//
//		for(int i = 0; i < TRAIL; i++){
//			if(counter == 0 && i ==0 ){
//				 leftH = lefthandarray[0];
//				 rightH = righthandarray[0];
//			} else {
//				leftH = lefthandarray[(start+i)%TRAIL];
//				rightH = righthandarray[(start+i)%TRAIL];
//			}
//
//			if(leftH != null && rightH != null){
//				//app.fill(192, 255-((255/TRAIL) * i));
//				//app.noStroke();
//				app.tint(255-((255/TRAIL) * i));
//				app.image(smoke, leftH.x, leftH.y,
//							.1f, .1f);
//				app.image(smoke, rightH.x, rightH.y,
//						.1f, .1f);
//				
////				app.ellipse(leftH.x, leftH.y, .1f, .1f);
////				app.ellipse(rightH.x, rightH.y, .1f, .1f);
//			}
//		}
//		
//		counter++;
//		if(counter >= TRAIL){
//			counter = 0;
//		}
	}
	
	/**
	 * Display the background appropriate to the current state
	 */
	public void drawBackground() {
		if(noDecay) {
			//nice background
		} else {
			//vein thing
		}
	}
	
	public void drawHead() {
		if(headv != null) {
			prevPosHead[0] = headv.x;
			prevPosHead[1] = headv.y;
			
			app.image(head, headv.x, headv.y+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			app.image(headFlower, headv.x, headv.y+.2f, .2f, .2f);
//			if(disappear) {
//				//make it black
//				app.tint(0);
//			} else {
//				//color
//				app.tint(255);
//			}
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
		}
		else {
			app.image(ribs, prevPosRibs[0], prevPosRibs[1] - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(prevPosRibs[0], prevPosRibs[1], disappear);
//			if(stateChange && disappear) {
//				noDecay = false;
//			}
//			if(stateChange && !disappear) {
//				noDecay = true;
//			}
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
	}
}