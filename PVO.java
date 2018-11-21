import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

class enterFrame extends AnimationTimer{
	PVO osn;
	double pulya_x;
	double pulya_y;
	double cel_x;
	double cel_y;
	Sprite tekCel;
	int upavshie_bombi;
	boolean sbros[]; //Ispolzuetsya dlyakontrolya sbrosa bomb
	
	enterFrame(PVO osn){
		this.osn=osn;
		upavshie_bombi=0;
		sbros=new boolean[4];
		for (int i=1;i<=3;i++) {
			sbros[i]=true;
		}
	}
	@Override
	public void handle(long now) {
		sbros_bomb(osn.cel, 1);
		sbros_bomb(osn.cel2, 2);
		sbros_bomb(osn.cel3, 3);
		if((tekCel=colise(osn.cel))!=null||(tekCel=colise(osn.cel2))!=null||(tekCel=colise(osn.cel3))!=null) {
			osn.pulya.tt.stop();
			osn.anim_puli=false;
			osn.pulya.setVisible(false);
			osn.pulya.setTranslateX(0);
			osn.pulya.setTranslateY(0);
			tekCel.setVisible(false);
			upavshie_bombi+=1;
			tekCel.anim_playing=false;
			tekCel.setX(osn.randomX());
			tekCel.setTranslateY(0);
			tekCel.tt.stop();
			tekCel=null;
		}
		zaverAnimCel(osn.cel);
		zaverAnimCel(osn.cel2);
		zaverAnimCel(osn.cel3);
		anim_vzriv();
		if (osn.pulya.tt.getStatus().toString()== "STOPPED" && osn.anim_puli) {
			osn.pulya.setVisible(false);
			osn.pulya.setTranslateX(-1*osn.pulya.tt.getByX()+osn.pulya.getTranslateX());
			osn.pulya.setTranslateY(-1*osn.pulya.tt.getByY()+osn.pulya.getTranslateY());
			osn.anim_puli=false;
		}
	}
	public Sprite colise(Sprite cel) {
		pulya_x=osn.myScene.getWidth()/2+osn.pulya.getTranslateX();
		pulya_y=osn.myScene.getHeight()+osn.pulya.getTranslateY();
		cel_x=cel.getX();
		cel_y=cel.getTranslateY();
		/*
		 ((((pulya_x-cel_x)<15 && (pulya_x-cel_x)>0) && ((pulya_y-cel_y)<15 && (pulya_y-cel_y)>0)) 
				|| (((osn.vzriv.getTranslateX()-cel_x)<60 && (osn.vzriv.getTranslateX()-cel_x)>0) 
						&& ((osn.vzriv.getTranslateY()-cel_y)<60 && (osn.vzriv.getTranslateY()-cel_y)>0)))
		 
		 (((Math.abs(pulya_x-cel_x+7.5)<10) && Math.abs(pulya_y-cel_y+7.5)<10) 
				|| (Math.abs(osn.vzriv.getTranslateX()-cel_x+7.5)<25 && Math.abs(osn.vzriv.getTranslateY()-cel_y+7.5)<25))
		*/
		if((((pulya_x-cel_x)<15 && (pulya_x-cel_x)>0) && ((pulya_y-cel_y)<15 && (pulya_y-cel_y)>0)) 
				|| (((osn.vzriv.getTranslateX()-cel_x)<60 && (osn.vzriv.getTranslateX()-cel_x)>0) 
						&& ((osn.vzriv.getTranslateY()-cel_y)<60 && (osn.vzriv.getTranslateY()-cel_y)>0))) {
			osn.vzriv.setTranslateX(cel_x+7);
			osn.vzriv.setTranslateY(cel_y);
			osn.vzriv.play();
			return cel;
		}else {
			return null;
		}
	}
	public void zaverAnimCel (Sprite cel) {
		if(cel.tt.getStatus().toString()== "STOPPED" && cel.anim_playing) {
			cel.setX(osn.randomX());
			cel.setTranslateY(0);
			cel.setVisible(false);
			upavshie_bombi+=1;
			cel.anim_playing=false;
		}
	}
	public void sbros_bomb(Sprite cel, int nomer_celi) {
		if(cel.tt.getStatus().toString()== "STOPPED" && osn.samolet.getTranslateX()-cel.getX()>10 && sbros[nomer_celi]) {
			cel.tt.play();
			cel.anim_playing=true;
			cel.setVisible(true);
			sbros[nomer_celi]=false;
		}
		if(osn.samolet.getTranslateX()>=osn.samolet.tt.getByX()) {
			osn.samolet.setVisible(false);
			osn.samolet.setTranslateX(0);
		}
		if(osn.samolet.getTranslateX()==0 && upavshie_bombi==3) {
			osn.samolet.setVisible(true);
			osn.samolet.tt.play();
			upavshie_bombi=0;
			for (int i=1;i<=3;i++) {
				sbros[i]=true;
			}
		}
	}
	public void anim_vzriv() {
		if(osn.vzriv.chastica[0].tt.getStatus().toString()== "STOPPED" && osn.vzriv.animaciya) {
			osn.vzriv.stop();
			//System.out.println("Animaciya vzriva");
		}
	}
}
public class PVO extends Application {
	Sprite samolet;
	Sprite pushka;
	Sprite pulya;
	Sprite cel;
	Sprite cel2;
	Sprite cel3;
	Pane rootNode=new Pane();
	Scene myScene=new Scene(rootNode, 400, 400);
	Vzriv vzriv;
	enterFrame en;
	double sin;
	double cos;
	Random posX_cel;
	boolean anim_puli;
	int ugolPovorota;
	double prozrachnost;
	
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage myStage) {
		prozrachnost=1;
		ugolPovorota=0;
		samolet=new Sprite(50, 50, true);
		pushka=new Sprite(50, 50, true);
		pulya=new Sprite(50, 50, true);
		cel=new Sprite(50,50, true);
		cel2=new Sprite(50,50, true);
		cel3=new Sprite(50,50, true);
		cel.tt.setDuration(Duration.millis((int)((myScene.getHeight()*5500))/200));
		cel2.tt.setDuration(Duration.millis((int)((myScene.getHeight()*5500))/200));
		cel3.tt.setDuration(Duration.millis((int)((myScene.getHeight()*5500))/200));
		pulya.tt.setDuration(Duration.millis((int)((myScene.getHeight()*1000))/200));
		posX_cel=new Random();
		myStage.setTitle("PVO");
		myStage.setScene(myScene);
		samolet.gr.setFill(Color.web("00FF00",prozrachnost));
		samolet.gr.fillRect(0, 0, 35, 15);
		samolet.tt.setByX(myScene.getWidth());
		samolet.tt.setDuration(Duration.millis((int)((myScene.getHeight()*4000))/200));
		samolet.tt.play();
		pushka.gr.setFill(Color.web("00FF00",prozrachnost));
		pushka.gr.fillRect(0, 0, 15, 25);
		pushka.setX(myScene.getWidth()/2-7.5);
		pushka.setY(myScene.getHeight()-30);
		pushka.setPivotX(7.5);
		pushka.setPivotY(25);
		pushka.tt.setCycleCount(2);
		pushka.tt.setAutoReverse(true);
		pushka.tt.setDuration(Duration.millis(200));
		pulya.gr.setFill(Color.web("FF0000",prozrachnost));
		pulya.gr.fillRect(0, 0, 5, 15);
		pulya.setX(myScene.getWidth()/2-2.5);
		pulya.setY(myScene.getHeight()-20);
		pulya.setPivotX(5);
		pulya.setPivotY(15);
		sozdCeli(cel);
		sozdCeli(cel2);
		sozdCeli(cel3);
		vzriv=new Vzriv(myScene);
		myScene.setOnKeyPressed((ke)->obrabotchik(ke));
		rootNode.getChildren().addAll(samolet, pushka, pulya, cel, cel2, cel3, vzriv);
		myStage.show();
		anim_puli=false;
		en=new enterFrame(this);
		pulya.setVisible(false);
		en.start();
		vzriv.setTranslateX(200);
		vzriv.setTranslateY(200);
	}
	public void obrabotchik(KeyEvent ke) {
		if (ke.getCode().toString() == "LEFT"){
			ugolPovorota-=10;
			pushka.rotation(ugolPovorota);
		}
		if (ke.getCode().toString() == "RIGHT"){
			ugolPovorota+=10;
			pushka.rotation(ugolPovorota);
		}
		if (ke.getCode().toString() == "UP"){
		}
		if (ke.getCode().toString() == "DOWN"){
		}
		if (ke.getCode().toString() == "A"){
		}
		if (ke.getCode().toString() == "Z"){
		}
		if (ke.getCode().toString() == "SPACE"){
			if(pulya.tt.getStatus().toString()== "STOPPED") {
				peremPuli();
			}
		}
	}
	public void peremPuli() {
		sin = -1*(Math.sin(ugolPovorota* Math.PI / 180));
		cos = Math.cos(ugolPovorota * Math.PI / 180);
		pulya.rotation(ugolPovorota);
		pulya.tt.setByY(-(myScene.getHeight() * cos));
		pulya.tt.setByX(-(myScene.getHeight() * sin));
		pushka.tt.setByY(10 * cos);
		pushka.tt.setByX(10 * sin);
		pulya.setVisible(true);
		pulya.tt.play();
		pushka.tt.play();
		anim_puli=true;
	}
	public void sozdCeli(Sprite cel) {
		cel.gr.setFill(Color.web("0000FF",prozrachnost));
		cel.gr.fillOval(0, 0, 15, 15);
		cel.setY(0);
		cel.setPivotX(7.5);
		cel.setPivotY(7.5);
		cel.tt.setByY(myScene.getHeight());
		cel.setX(randomX());
		cel.setVisible(false);
	}
	public double randomX() {
		return posX_cel.nextInt((int) myScene.getWidth()-30)+15;
	}
}
