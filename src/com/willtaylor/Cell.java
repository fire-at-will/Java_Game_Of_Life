package com.willtaylor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by willtaylor on 11/2/14.
 */
public class Cell extends JLabel {

    public boolean alive = false;

    private java.awt.Color aliveColor = new Color(250, 36, 59);
    private Color deadColor = new Color(57, 57, 57);

    Point point;

    private Board board;



    public Cell(int x, int y, Board board){

        point = new Point(x, y);

        setOpaque(true);

        this.board = board;

        setBackground(deadColor);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Cell.this.toggleState();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Cell.this.board.main.setMouseClicked(true);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Cell.this.board.main.setMouseClicked(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                onMouseEnter();
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void onMouseEnter(){
        if(board.main.getMouseClicked()){
            if(board.main.getGameState().equals(Main.PREGAME)){
                toggleState();
            }
        }
    }

    public void toggleState(){


        alive = !alive;

        if(alive){
            setBackground(aliveColor);
        } else{
            setBackground(deadColor);
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean isAlive){

        if(isAlive != alive){

            if(isAlive){
                setBackground(aliveColor);
            } else{
                setBackground(deadColor);
            }
        }

        this.alive = isAlive;
    }

    public int getNeighbors(){

        int count = 0;

        // Check right cell

        Cell compareCell;

        if(point.getX() != 34){
            compareCell = board.cells[ (int )(point.getX() + 1 )][ (int) point.getY()];
            if(compareCell.isAlive()){
                count++;
            }
        }

        // Check below cell

        if(point.getY() != 34){
            compareCell = board.cells[ (int) point.getX()][ (int) point.getY() + 1];
            if(compareCell.isAlive()){
                count++;
            }
        }

        // Check left of cell
        if(point.getX() != 0){
            compareCell = board.cells[ (int) point.getX() - 1][(int) point.getY()];
            if(compareCell.isAlive()){
                count++;
            }
        }

        // Check above cell
        if(point.getY() != 0){
            compareCell = board.cells[(int) point.getX()][(int) point.getY() - 1];

            if(compareCell.isAlive()){
                count++;
            }
        }

        // Check above right of cell
        if( (point.getY()  != 0) && (point.getX() != 34)){

            compareCell = board.cells[(int) point.getX() + 1][(int) point.getY() - 1];
            if(compareCell.isAlive()){
                count++;
            }

        }

        // Check bottom right of cell
        if( (point.getX() != 34) && (point.getY() != 34)){

            compareCell = board.cells[(int) point.getX() + 1][(int) point.getY() + 1];

            if(compareCell.isAlive()){
                count++;
            }
        }

        // Check bottom left of cell
        if( (point.getX() != 0) && (point.getY() != 34)){

            compareCell = board.cells[(int) point.getX() - 1][(int) point.getY() + 1];

            if(compareCell.isAlive()){
                count++;
            }
        }

        // Check above left of cell
        if( (point.getX() != 0) && (point.getY() != 0) ){

            compareCell = board.cells[(int) point.getX() - 1][(int) point.getY() - 1];

            if(compareCell.isAlive()){
                count++;
            }
        }

        return count;
    }

    public void turnOff(){
        alive = false;
        setBackground(deadColor);
    }
}
