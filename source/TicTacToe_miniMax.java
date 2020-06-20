import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TicTacToe_miniMax extends PApplet {


public void setup () {
  turn='X';
  
  //size(400, 650);
  if (width<height)
    scale=width;
  else
    scale=height;
  origin=new PVector((width-scale)/2, (height-scale)/2);
  board=new Board();
}
public void draw () {

  background(0);


  bestMove();
  board.show();
  if (board.Won()!='E') {
    endOfGame("WON");
  }
  if (board.Draw()) {
    endOfGame("DRAW");
  }
}
public void mousePressed() {
  board.clicked(mouseX, mouseY);
  if (finished) {
    rb.clicked(mouseX, mouseY);
    redraw();
  }
}
class Board {
  Square[][] squares = new Square[3][3];
  Board() {
    initilizeBoard();
  }
  public void initilizeBoard() {
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        squares[i][j]=new Square(new PVector((i)*(scale/3), (j)*(scale/3)));
      }
    }
  }
  public char Won() {
    for (int i=0; i<3; i++) {

      //rows
      if (squares[i][0].type!='E'&&squares[i][0].type==squares[i][1].type&&squares[i][0].type==squares[i][2].type) {
        return squares[i][0].type;
      }
      //columns
      if (squares[0][i].type!='E'&&squares[0][i].type==squares[1][i].type&&squares[0][i].type==squares[2][i].type) {
        return squares[0][i].type;
      }
    }
    //diagonals
    if (squares[0][0].type!='E' && squares[0][0].type==squares[1][1].type && squares[2][2].type==squares[0][0].type) {
      return squares[0][0].type;
    }
    if (squares[2][0].type!='E'&&squares[2][0].type==squares[1][1].type&&squares[1][1].type==squares[0][2].type) {
      return squares[2][0].type;
    }
    return 'E';
  }
  public void clicked(float x, float y) {
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        squares[i][j].clicked(x, y);
      }
    }
  }
  public void show() {
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        squares[i][j].show();
      }
    }
  }
  public boolean Draw() {
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        if (squares[i][j].type!='E')//skip it while its not emptty
          continue;
        else
          return false;
      }
    }
    return true;//if none is empty and Won hasn't worked yet then its a draw
  }
}
class Square {
  char type;
  PVector pos;
  //color colour;
  Square(PVector position) {
    type='E';
    pos=position.copy();
    //colour=color(170);
  }

  public void show() {
    if (type=='E') {
      fill(170);
      rect(origin.x+pos.x, origin.y+ pos.y, scale/3, scale/3);
    }
    if (type!='E') {
      fill(255);
      rect(origin.x+pos.x, origin.y+ pos.y, scale/3, scale/3);
      float mWidth=textWidth(type);
      fill(0);
      float Size=(scale/3);
      textSize(Size);
      text(type, origin.x+pos.x-mWidth/2+scale/3/2, origin.y+pos.y+mWidth/2+scale/3/2);
    }
  }
  public void clicked(float x, float y) {

    if (inThis(x, y)) {
      if (type=='E') {
        if (turn==human) {
          type=human;
          turn=AI;
        }
      }
    }
  }
  private boolean inThis(float x, float y) {

    float thisLeft=pos.x;
    float thisRight=pos.x+scale/3;
    if (x-origin.x<thisRight&&x-origin.x>thisLeft) {//seeing if the x of mouse is in the box

      float thisUp=pos.y;
      float thisDown=pos.y+ scale/3;
      if (-origin.y+y<thisDown&&-origin.y+y>thisUp) {//seeing if the y of mouse is in the box
        return true;
      }
    }
    return false;
  }
}
class ResetButton {
  PVector pos;
  PVector size;
  ResetButton(PVector s) {
    size=s;
    pos=new PVector(width/2-size.x/2, height*2.0f/3);
  }
  public void clicked(float x, float y) {

    if (inThis(x, y)) {
      loop();
      restartGame();
    }
  }
  public boolean inThis(float x, float y) {

    float thisLeft=pos.x;
    float thisRight=pos.x+size.x;
    if (x<thisRight&&x>thisLeft) {//seeing if the x of mouse is in the box

      float thisUp=pos.y;
      float thisDown=pos.y+size.y;
      if (y<thisDown&&y>thisUp) {//seeing if the y of mouse is in the box
        return true;
      }
    }
    return false;
  }
  public void show() {
    fill(255);
    rect(pos.x, pos.y, size.x, size.y);

    String s="Restart";
    fill(0);  
    float Size=1.5f*(size.x/s.length());
    textSize(Size);
    redraw();
    float sWidth=textWidth(s);
    float sHeight=textAscent();
    text(s, pos.x+size.x/2-sWidth/2, pos.y+size.y/2+sHeight/2/2);
  }
  public void restartGame() {
    turn='X';
    board=new Board();
    finished=false;
  }
}
float scale;
char turn;
Board board;
PVector origin;
ResetButton rb;
boolean finished=false;
//------------------------------------------------------------------------------
public void endOfGame(String gameState) {
  noLoop();
  background(168);
  finished=true;
  String s="";
  if (gameState=="DRAW") {
    s="It's a draw!!";
    fill(255, 255, 0);
  }
  if (gameState=="WON") {

    if (turn=='X') {
      s="AI won :(";
      fill(255, 0, 0);
    } else {
      s="You Won!!";
      fill(0, 255, 0);
    }
  }
  float Size=1.5f*width/s.length();
  textSize(Size);
  float sWidth=textWidth(s);
  float sHeight=textWidth(s.charAt(0));
  text(s, width/2-sWidth/2, height/2+sHeight/2);
  rb=new ResetButton(new PVector(scale/3, scale/5));
  rb.show();
}
//void computer() {
//  if (turn=='O') {
//    for (int i=0; i<3; i++) {
//      for (int j=0; j<3; j++) {
//        if (check4win())
//          return;
//        else if (check4lose())
//          return;
//        else
//          randomChoice();
//      }
//    }
//  }
//}
final char human='X';
final char AI='O';
private boolean skip=false;
public void bestMove() {


  if (turn==AI) {//This part is to determine if a delay is required
    skip=!skip;
  }
  if (turn==AI&&!skip) {
    delay(1000);//this is to make the ai delay so that it would look more authentic
    // AI to make its turn
    int bestScore = -1000;
    PVector move= new PVector( 0, 0 );
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Is the spot available?
        if (board.squares[i][j].type == 'E') {
          board.squares[i][j].type= AI;
          int score = minimax(board, false);
          board.squares[i][j].type = 'E';
          if (score > bestScore) {
            bestScore = score;
            move = new PVector( i, j );
          }
        }
      }
    }

    board.squares[(int)move.x][(int)move.y].type = 'O';
    turn='X';
  }
}



private int minimax(Board b, boolean isMaximizing) {

  char result=b.Won();
  if (result!='E') {
    if (result=='X') {
      return -10;
    } else
      return 10;
  }
  if (b.Draw()) {
    return 0;
  }


  if (isMaximizing) {
    int bestScore = -1000;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Is the spot available?
        if (b.squares[i][j].type == 'E') {
          b.squares[i][j].type = AI;
          int score = minimax(b, false);
          b.squares[i][j].type = 'E';
          bestScore = max(score, bestScore);
        }
      }
    }
    return bestScore;
  } else {
    int bestScore = 1000;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Is the spot available?
        if (b.squares[i][j].type == 'E') {
          b.squares[i][j].type = human;
          int score = minimax(board, true);
          b.squares[i][j].type = 'E';
          bestScore = min(score, bestScore);
        }
      }
    }
    return bestScore;
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TicTacToe_miniMax" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
