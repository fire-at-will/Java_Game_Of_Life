package com.willtaylor;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public Board board;
    private GameThread thread;

    public static final String PREGAME         = "PREGAME";
    public static final String GAME            = "GAME";

    public JButton resetButton;

    private String gameState = PREGAME;

    public int generation = 1;

    public JButton startButton, stopButton;

    private boolean mouseClicked = false;

    public boolean gameRunning = false;

    private JLabel generationLabel;
    private JTextArea speedArea;

    public PatternFrame patternFrame;

    public float speed = 1000;

    public static void main(String[] args) {
	// write your code here
        Main main = new Main();
    }

    public Main(){

        board = new Board(this);

        JFrame frame = new JFrame("Controls");

        frame.setSize(300, 150);

        startButton = new JButton("Start");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.this.startGame();
            }
        });

        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int boardX = board.getX();
        int boardY = board.getY();

        int x = boardX + 825;

        frame.setLocation(x, boardY);

        JLabel speedLabel = new JLabel("Generation speed in seconds:");
        speedArea = new JTextArea("0.5");
        speedArea.setColumns(5);
        JButton speedButton = new JButton("Set Speed");
        speedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.this.speedButtonPressed();
            }
        });

        generationLabel = new JLabel("Generation: 0");

        frame.add(generationLabel, BorderLayout.NORTH);
        generationLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel aPanel = new JPanel();
        aPanel.add(speedLabel);
        aPanel.add(speedArea);
        aPanel.add(speedButton);

        frame.add(aPanel, BorderLayout.CENTER);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.this.stopButtonPressed();
            }
        });

        resetButton = new JButton("Reset Board");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.this.resetButtonPressed();
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(stopButton);
        southPanel.add(resetButton);

        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        patternFrame = new PatternFrame(this);

        thread = new GameThread(this, board.cells);

    }

    public void stopButtonPressed(){
        resetButton.setEnabled(true);
        startButton.setEnabled(true);
        gameState = PREGAME;
        gameRunning = false;
    }

    public void resetButtonPressed(){
        generation = 1;
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        gameState = PREGAME;
        gameRunning = false;

        generationLabel.setText("Generation: 0");

        for(int i = 0; i < 35; i++) {

            for (int j = 0; j < 35; j++) {

                board.cells[i][j].turnOff();

            }

        }
    }

    public void speedButtonPressed(){

        String input = speedArea.getText();

        speed = Float.parseFloat(input) * 1000;

    }

    public void setMouseClicked(boolean clicked){
        this.mouseClicked = clicked;
    }

    public boolean getMouseClicked(){
        return mouseClicked;
    }

    public String getGameState(){
        return gameState;
    }

    public void setGameState(String gameState){
        this.gameState = gameState;
    }

    public void startGame(){
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        resetButton.setEnabled(false);
        gameState = GAME;
        gameRunning = true;
        thread = new GameThread(this, board.cells);
        thread.start();
    }

    private class GameThread extends Thread{

        Main main;
        Board board;
        Cell[][] cells;

        public GameThread(Main main, Cell[][] cells){
            this.main = main;
            this.board = main.board;

            this.cells = cells;
        }

        public void run(){

            while(main.gameRunning){

                main.generationLabel.setText("Generation: " + main.generation);

                boolean[][] states = new boolean[35][35];

                for(int i = 0; i < 35; i++){

                    for(int j = 0; j < 35; j++){

                        Cell cell = cells[i][j];
                        boolean cellIsAlive = cell.isAlive();

                        int count = cell.getNeighbors();

                        boolean result = false;

                        // Apply the rules and set the next state.
                        if (cellIsAlive && count < 2) {

                            result = false;

                        } else if (cellIsAlive && (count == 2 || count == 3)) {

                            result = true;

                        } else if (cellIsAlive && count > 3) {

                            result = false;

                        } else if (!cellIsAlive && count == 3) {

                            result = true;

                        }

                        states[i][j] = result;
                    }

                }

                for(int i = 0; i < 35; i++){
                    for(int j = 0; j < 35; j++){
                        cells[i][j].setAlive(states[i][j]);
                    }
                }

                try {
                    Thread.sleep((long) main.speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                main.generation++;
            }

        }

    }

}
