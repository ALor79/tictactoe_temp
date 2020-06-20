
void setup () {
  turn='X';
  fullScreen();
  //size(400, 650);
  if (width<height)
    scale=width;
  else
    scale=height;
  origin=new PVector((width-scale)/2, (height-scale)/2);
  board=new Board();
}
void draw () {

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
void mousePressed() {
  board.clicked(mouseX, mouseY);
  if (finished) {
    rb.clicked(mouseX, mouseY);
    redraw();
  }
}
