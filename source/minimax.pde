final char human='X';
final char AI='O';
private boolean skip=false;
void bestMove() {


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
