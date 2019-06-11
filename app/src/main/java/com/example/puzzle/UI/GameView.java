package com.example.puzzle.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    int moves;
    int movesToSoluction;
    private GameViewActionListener listener;
    GameBoard board;
    Context context;

    public GameView(Context context, AttributeSet attrs, GameViewActionListener listener) {
        super(context, attrs);


        this.context = context;
        this.listener = listener;
        this.moves = 0;
        board = new GameBoard(context);
        board.calcSolve();
        this.movesToSoluction=  board.getNMoves();
        System.out.println("SOLUCAO ->"+this.movesToSoluction);
        String text = "Movimentos: " + String.valueOf(this.moves) + " Min: " + String.valueOf(this.movesToSoluction);
        listener.showMessage(text);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        board.setDimensions(this.getWidth(), this.getHeight());
        board.draw(canvas);

    }

    public void getSolve(){
        board.calcSolve();
        this.moves ++;
        /*Remove first element because it is null*/
        board.wayToSolve.remove(0);
        //System.out.println("REMOVIDO-> " +removido+" Tamanho do way-> "+board.wayToSolve.size());
    }

    public void solve(){
        if(board.wayToSolve.size() == 0) {
            return;
        }
        board.solve();
        board.wayToSolve.remove(0);
        this.moves++;
        String text = "Movimentos: " + String.valueOf(this.moves) + " Min: " + String.valueOf(this.movesToSoluction);
        listener.showMessage(text);

        this.postInvalidate();
        check_game_over();

    }

    public int getStepsForSoluction(){
        return board.stepsForSoluction;
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                moveBox(x, y);
                break;
            default:
                return false;
        }


        return true;
    }

    public void moveBox(float x, float y) {
        if (board.updateBoard(x, y))
        {
            this.moves++;
            String text = "Movimentos: " + String.valueOf(this.moves) + " Min: " + String.valueOf(this.movesToSoluction);
            listener.showMessage(text);
            check_game_over();

        }
        this.invalidate();
    }

    public void check_game_over()
    {
        if(board.check_game_state() && this.moves <= getStepsForSoluction())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Parabéns! Você venceu o jogo em "+ String.valueOf(this.moves) + " movimentos")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listener.showMessage("create_new_game");
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }if (board.check_game_state() && this.moves > getStepsForSoluction()){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Você Falhou! Tente fazer no máximo o número mínimo de movimentos. Boa Sorte!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.showMessage("create_new_game");
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    }
    public interface GameViewActionListener {
        void showMessage(String message);
    }
}
