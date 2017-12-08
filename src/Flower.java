import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a single flower to be rendered and manipulated on the screen
 * @author Sara, Isabelle, Lauren
 *
 */
public class Flower {
	
	private PImage img;
	private PApplet parent;
	private float relativeX; //the distance this flower is from the center - fixed upon construction
	private float relativeY; //the distance this flower is from the center - fixed upon construction
	private float width = .2f;
	private float growRate; //so flowers shrink and grow at different rates
	
	public Flower(PApplet parent, float relativeX, float relativeY) {
		//randomly pick which image this flower will be
		this.parent = parent;
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		growRate = parent.random(.00015f,.001f);
//		growRate = parent.random(.001f, .005f); //much faster grow rate for testing
		selectFlower();
	}
	
	public void draw(float newX, float newY) {
		parent.image(img, newX+relativeX, newY+relativeY, width, width);
	}
	
	/**
	 * @return true if flowers are fully grown
	 */
	public boolean grow() {
		if(width < .2f)
			width+=growRate;
		else {
			//change state to bright background with falling colors (or something) if it hasn't already been done
			return true;
		}
		return false;
	}
	
	/**
	 * @return true if flowers are completely gone
	 */
	public boolean vanish() {
		if(width > 0)
			width-=growRate;
		else {
			//change state to dark-veiny stuff if it hasn't already been done
			return true;
		}
		if(width < 0) //safety net
			width = 0;
		return false;
	}
	
	private void selectFlower() {
		double ranFlower = parent.random(0,1);
		if(ranFlower < .1) {
			img = parent.loadImage("assets/deepredflower.png");
		} else if(ranFlower < .2) {
			img = parent.loadImage("assets/yellowflower.png");
		} else if(ranFlower < .3) {
			img = parent.loadImage("assets/greenfiller.png");
		} else if(ranFlower < .4) {
			img = parent.loadImage("assets/greenflower.png");
		} else if(ranFlower < .5) {
			img = parent.loadImage("assets/lightblueflower.png");
		} else if(ranFlower < .6) {
			img = parent.loadImage("assets/pinkflower.png"); //may not be transparent
		} else if(ranFlower < .7) {
			img = parent.loadImage("assets/purpleflower.png");
		} else if(ranFlower < .8) {
			img = parent.loadImage("assets/sunsetflower.png");
		} else if(ranFlower < .9) {
			img = parent.loadImage("assets/whitegreenflower.png");
		} else {
			img = parent.loadImage("assets/whiteredflower.png");
		}
	}
	
	public void addGrow(float add) {
		growRate += add;
	}
	
}