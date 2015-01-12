package com.willtaylor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by willtaylor on 11/2/14.
 */
public class PatternFrame extends JFrame {

    Main main;

    JButton checkerButton, gridButton, negateButton, woahButton;

    public PatternFrame(Main main){
        this.main = main;

        setTitle("Patterns");

        checkerButton = new JButton("Checkered");

        checkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCheckered();
            }
        });

        add(checkerButton);

        setLayout(new GridLayout(4, 1));
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int x = main.board.getX();
        int y = main.board.getY();

        y += (825 - 200);

        x += 825;

        setLocation(x, y);

        gridButton = new JButton("Grid");
        gridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGrid();
            }
        });

        add(gridButton);

        negateButton = new JButton("Negate Cells");
        negateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                negateCells();
            }
        });

        add(negateButton);

        woahButton = new JButton("Woah, Duuuuude");
        woahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeWoah();
            }
        });

        add(woahButton);

        setVisible(true);
    }

    public void createGrid(){
        main.resetButtonPressed();

        for(int i = 0; i < 35; i++){

            if( (i % 2) == 0){

                for( int j = 0; j < 35; j++){
                    main.board.cells[i][j].setAlive(true);
                }

            }

        }

        for(int j = 0; j < 35; j++){

            if( (j % 2) == 0){
                for(int i = 0; i < 35; i++){
                    main.board.cells[i][j].setAlive(true);
                }
            }
        }
    }

    public void createCheckered(){
        main.resetButtonPressed();

        boolean last = false;

        for(int i = 0; i < 35; i++){
            for(int j = 0; j < 35; j++){

                if(!last){
                    main.board.cells[i][j].setAlive(true);
                    last = true;
                } else{
                    last = false;
                }

            }
        }
    }

    public void negateCells(){
        for(int i = 0; i < 35; i++){
            for(int j = 0; j < 35; j++){

                boolean temp = main.board.cells[i][j].isAlive();
                main.board.cells[i][j].setAlive(!temp);

            }
        }
    }

    public void makeWoah(){
        main.resetButtonPressed();

        //Diagnals
        for(int i = 0; i < 35; i++){
            main.board.cells[i][i].setAlive(true);
            main.board.cells[i][34 - i].setAlive(true);
        }

        // Horizontals
        for(int i = 0; i < 16; i++){
            main.board.cells[i][17].setAlive(true);
            main.board.cells[34 - i][17].setAlive(true);
        }

        // Verticals
        for( int j = 0; j < 16; j++){
            main.board.cells[17][j].setAlive(true);
            main.board.cells[17][34 - j].setAlive(true);
        }
    }
}
