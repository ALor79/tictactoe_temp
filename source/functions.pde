float scale;
char turn;
Board board;
PVector origin;
ResetButton rb;
boolean finished=false;
//------------------------------------------------------------------------------
void endOfGame(String gameState) {
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
  float Size=1.5*width/s.length();
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
