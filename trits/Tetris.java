/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trits;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author AFC
 */
public class Tetris extends Application{
	// The variables
    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    private static int top = 0;
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    private static boolean game = true;
    
    private static Form object;
    private static Form nextObj = Controller.makeRect();
    
    private static Pane group = new Pane();
    private static Scene scene = new Scene(group, XMAX + 250, YMAX);
    private Text player;
    private Text lines;
    private String playerName;
    private Text moveScore;
    public static int move = 0;
    private static int linesNo = 0;

//    Button myButton = new Button("اضغط هنا");;
//    boolean control = true;
//    
    
    
    private void startGame(String playerName) {
        this.playerName = playerName;
    }

    public static void main(String[] args) {
            launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        gameStartSetting();
        
         group.getChildren().addAll(player,moveScore,lines,nextObj.a, nextObj.b, nextObj.c, nextObj.d );
        moveOnKeyPress(nextObj);
        object = nextObj;
        nextObj = Controller.makeRect();
        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.setResizable(false);
        stage.show();

        
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
                            top++;
                        else
                            top = 0;
                            
                        if (top == 2) {
                            // GAME OVER text
                            group.getChildren().clear();
                            Text over = text("GAME OVER",250,30,Color.RED,"-fx-font: 70 arial;");
                            group.getChildren().addAll(over);
                            game = false;
                        }else{
                            if (game) {
                                formMoveDown(object);
                                moveScore.setText("Move = " + move);
                                lines.setText("lines = " + linesNo);
                            }
                        }
                    }
                });
            }
        };
        fall.schedule(task, 0, 150); 
    }

    
//      Start method Responsible for responding to press on keyboard
	private void moveOnKeyPress(Form form) {
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                    case RIGHT:
                        Controller.MoveRight(form);
                        move++;
                        break;
                    case DOWN:
                        formMoveDown(form);
                        move++;
                        break;
                    case LEFT:
                        Controller.MoveLeft(form);
                        move++;
                        break;
                    case UP:
                        MoveTurn(form);
                        move++;
                        break;
                    }
                }
            });
	}
//      End method Responsible for responding to press on keyboard


        
//      /////// Start function to chnge the situation of form depend on form name /////////
        private void MoveTurn(Form form) {
		int f = form.form;
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		switch (form.getName()) {
		case "j":
			if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case "l":
			if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveRight(form.b);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveRight(form.b);
				MoveRight(form.b);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveLeft(form.b);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveLeft(form.b);
				MoveLeft(form.b);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			break;
		case "o":
			break;
		case "s":
			if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case "t":
			if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.c);
				MoveRight(form.c);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.c);
				MoveLeft(form.c);
				form.changeForm();
				break;
			}
			break;
		case "z":
			if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		case "i":
			if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		}
	}
        
        
//      Start function to Check if the form can be changed without leaving the design
	private boolean cB(Rectangle rect, int x, int y) {
            boolean xb = false;
            boolean yb = false;
            if (x >= 0)
                    xb = rect.getX() + x * MOVE <= XMAX - SIZE;
            if (x < 0)
                    xb = rect.getX() + x * MOVE >= 0;
            if (y >= 0)
                    yb = rect.getY() - y * MOVE > 0;
            if (y < 0)
                    yb = rect.getY() + y * MOVE < YMAX;
            return xb && yb && MESH[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
	}
//      End function to Check if the form can be changed without leaving the design
        
        
//      Start functions to move rectangles (up down left right) to change situation of forms
	private void MoveRight(Rectangle rect) {
            if (rect.getX() + MOVE <= XMAX - SIZE)
                rect.setX(rect.getX() + MOVE);
	}
	private void MoveLeft(Rectangle rect) {
            if (rect.getX() - MOVE >= 0)
                rect.setX(rect.getX() - MOVE);
	}
	private void MoveDown(Rectangle rect) {
            if (rect.getY() + MOVE < YMAX)
                rect.setY(rect.getY() + MOVE);
	}
	private void MoveUp(Rectangle rect) {
            if (rect.getY() - MOVE > 0)
                rect.setY(rect.getY() - MOVE);
	}
//      End functions to move rectangles (up down left right) to change situation of forms

        
//  /////////End function to chnge the situation of form depend on form///////////

        
	private void RemoveRows(Pane pane) {
            ArrayList<Node> rects = new ArrayList<Node>();
            ArrayList<Integer> lines = new ArrayList<Integer>();
            ArrayList<Node> newrects = new ArrayList<Node>();
            int full = 0;
            for (int i = 0; i < MESH[0].length; i++) {
                for (int j = 0; j < MESH.length; j++) {
                    if (MESH[j][i] == 1)
                        full++;
                }
                if (full == MESH.length)
                    lines.add(i);
                
                full = 0;
            }
            if (lines.size() > 0)
                do {
                    for (Node node : pane.getChildren()) {
                        if (node instanceof Rectangle)
                            rects.add(node);
                        }
                    linesNo++;

                    for (Node node : rects) {
                        Rectangle a = (Rectangle) node;
                        if (a.getY() == lines.get(0) * SIZE) {
                            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                            pane.getChildren().remove(node);
                        } else
                            newrects.add(node);
                    }

                    for (Node node : newrects) {
                        Rectangle a = (Rectangle) node;
                        if (a.getY() < lines.get(0) * SIZE) {
                            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                            a.setY(a.getY() + SIZE);
                        }
                    }

                    lines.remove(0);
                    rects.clear();
                    newrects.clear();

                    for (Node node : pane.getChildren()) {
                        if (node instanceof Rectangle)
                            rects.add(node);
                    }

                    for (Node node : rects) {
                        Rectangle a = (Rectangle) node;
                        try {
                            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                        } catch (ArrayIndexOutOfBoundsException e) { }
                    }

                    rects.clear();

                } while (lines.size() > 0);
	}
    
        
//      /////////////// Start function to move form to down or stop it and start a new form //////////
        
        
	private void formMoveDown(Form form) {
            if ( unMoveableDown(form.a) || unMoveableDown(form.b) || unMoveableDown(form.c) || unMoveableDown(form.d)) {
                MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
                MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
                MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
                MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
                RemoveRows(group);

                nextObj = Controller.makeRect();
                object = nextObj;
                group.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
                moveOnKeyPress(nextObj);
            }else{
                form.a.setY(form.a.getY() + MOVE);
                form.b.setY(form.b.getY() + MOVE);
                form.c.setY(form.c.getY() + MOVE);
                form.d.setY(form.d.getY() + MOVE);
            }
	}
        
//      Start to check if form should stop or not 
        private boolean unMoveableDown(Rectangle rect){
            if(rect.getY() == YMAX - SIZE){
                return true;
            }
            else if(MESH[(int) rect.getX() / SIZE][((int) rect.getY() / SIZE) + 1] == 1){
                return true;
            }
                return false;
            
        }
//      End to check if form should stop or not 

        
//      ///////////End function to move form to down or stop it and start a new form///////////// 


        
//      Start function to prepare start settings of game
        public void gameStartSetting(){
                Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Enter Your Name");

        TextField textField = new TextField();
        VBox vbox = new VBox(new Label("Enter Your Name:"), textField);
        dialog.getDialogPane().setContent(vbox);
        
        Button button = new Button("Start Game");
        button.setOnAction(event -> {
            String playerName = textField.getText();
            startGame(playerName);
            dialog.close();
        });
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        vbox.getChildren().add(button);

        dialog.showAndWait();

              
        group.setStyle("-fx-background-color: black ");


        for (int i = 0; i < XMAX + 25; i+=25) {
            Line linex = new Line(i , 0 , i , YMAX);
            linex.setStroke(Color.GRAY);
            group.getChildren().addAll(linex);
        }
                
        for (int i = 0; i < YMAX; i+=25) {
            Line liney = new Line(0 , i , XMAX  , i);
            liney.setStroke(Color.GRAY);
            group.getChildren().add(liney);
        }
//                
//  Player tsxt
        player = text("Player Name : " + playerName,50,XMAX + 35,Color.WHITE,"-fx-font: 20 arial;");
        
//  score text
        moveScore = text("Move: " ,100,XMAX + 35,Color.WHITE,"-fx-font: 20 arial;");
        
//  Lines text      
        lines = text("Lines: ",150,XMAX + 35,Color.GREEN,"-fx-font: 20 arial;");
                
        for (int i = 0; i < MESH.length; i++) {
            for (int j = 0; j < MESH[i].length; j++) {
                MESH[i][j] = 0;
            }
        }
    }
//      End function to prepare start settings of game

            
//      Start function to create texts
        public static Text text(String txt,int y,int x,Color color,String font ){
        Text text = new Text(txt);
        text.setStyle(font);
        text.setY(y);
        text.setX(x);
        text.setFill(color);
        return text;
    }
//      End function to create texts
            
}
