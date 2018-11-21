import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

class Sprite extends Canvas{
	GraphicsContext gr;
	TranslateTransition tt;
	double width;
	double height;
	private double perX;
	private double perY;
	private Rotate tekRotate;
	private Translate tekPer;
	boolean anim_playing;
	
	Sprite(int width, int height){
		super(width, height);
		perX=0;
		perY=0;
		gr=this.getGraphicsContext2D();
		this.width=width;
		this.height=height;
		tekRotate=new Rotate(0,width/2,height/2);
		tekPer=new Translate(0,0);
	}
	Sprite(int width, int height, boolean transTran){
		super(width, height);
		perX=0;
		perY=0;
		gr=this.getGraphicsContext2D();
		this.width=width;
		this.height=height;
		tekRotate=new Rotate(0,width/2,height/2);
		tekPer=new Translate(0,0);
		if (transTran) {
			this.tt=new TranslateTransition(Duration.millis(1000), this);
		}
	}
	public void rotation(int angle){
		this.getTransforms().removeAll(tekRotate);
		tekRotate.setAngle(angle);
		this.getTransforms().addAll(tekRotate);
	}
	public void setX(double X) {
		perX=X;
		tekPer.setX(X);
		this.getTransforms().removeAll(tekRotate, tekPer);
		this.getTransforms().addAll(tekPer, tekRotate);
	}
	public void setY(double Y) {
		perY=Y;
		tekPer.setY(Y);
		this.getTransforms().removeAll(tekRotate, tekPer);
		this.getTransforms().addAll(tekPer, tekRotate);
	}
	public void setPivotX(double povX) {
		tekRotate.setPivotX(povX);
	}
	public void setPivotY(double povY) {
		tekRotate.setPivotY(povY);
	}
	public double getX() {
		return perX;
	}
	public double getY() {
		return perY;
	}
}