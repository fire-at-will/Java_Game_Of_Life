package com.willtaylor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by willtaylor on 11/2/14.
 */
public class Board extends JFrame{

    Cell[][] cells = new Cell[35][35];

    Main main;

    public Board(Main main){

        this.main = main;

        setTitle("Game of Life");

        setSize(825, 825);
        setLayout(new GridLayout(35, 35));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        for (int i = 0; i < 35; i++){
            for (int j = 0; j < 35; j++){
                Cell cell = new Cell(i, j, this);
                add(cell);
                cells[i][j] = cell;
            }
        }

        setVisible(true);

    }


}
