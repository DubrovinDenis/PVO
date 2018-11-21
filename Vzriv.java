import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Vzriv extends Pane {
	Sprite[] chastica=new Sprite[8];
	double sin;
	double cos;
	int ugol;
	boolean animaciya;
	FadeTransition ft;
	
	Vzriv(Scene sc) {
		ugol=45;
		for(int i=0;i<8;i++) {
			chastica[i]=new Sprite(50,50,true);
			chastica[i].tt.setDuration(Duration.millis((int)((sc.getHeight()*200))/200));
			chastica[i].gr.setFill(Color.web("FF0000",1));
			chastica[i].gr.fillRect(0, 0, 5, 10);
			chastica[i].setPivotX(2.5);
			chastica[i].setPivotY(10);
			chastica[i].setVisible(false);
			this.getChildren().add(chastica[i]);
		}
		ft=new FadeTransition(Duration.millis((int)((sc.getHeight()*200))/200), this);
	}
	void play() {
		animaciya=true;
		for(int i=0;i<8;i++) {
			sin = -1*(Math.sin(ugol * i * Math.PI / 180));
			cos = Math.cos(ugol * i * Math.PI / 180);
			chastica[i].setVisible(true);
			chastica[i].rotation(ugol*i);
			chastica[i].tt.setByY(-(20 * cos));
			chastica[i].tt.setByX(-(20 * sin));
			chastica[i].tt.play();
		}
		ft.setFromValue(1.0);
		ft.setToValue(0.2);
		ft.play();
	}
	void stop() {
		animaciya=false;
		for(int i=0;i<8;i++) {
			chastica[i].tt.stop();
			chastica[i].setTranslateX(0);
			chastica[i].setTranslateY(0);
			chastica[i].setVisible(false);
		}
	}
}
