package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuFromFile {


    public static final int EMPTY = 0;
    public static int SIZE;
    private int[][] board;
    public SudokuFromFile(int[][] board) {
        this.board = board;
        SIZE = board.length;
    }

    public static int[][] createBoard(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("NOT FOUND");
            return null;
        }
        List<Integer[]> container = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        int length = 0;
        while (scanner.hasNextLine()) {
            String[] lineStr = scanner.nextLine().split(" ");
            Integer[] numbers = new Integer[lineStr.length];
            length = lineStr.length;
            for (int j = 0; j < lineStr.length; j++)
                numbers[j] = Integer.parseInt(lineStr[j]);
            container.add(numbers);
        }
        int[][] returnBoard = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                returnBoard[i][j] = container.get(i)[j];
            }
        }
        scanner.close();
        return returnBoard;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int[][] valami = createBoard("C:\\work\\train\\untitled\\src\\com\\company\\table2");
        SudokuFromFile sudoku = new SudokuFromFile(valami);
        System.out.println("Sudoku grid to solve");
        sudoku.display();

        if (sudoku.solve()) {
            System.out.println("Sudoku Grid solved with simple BT");
            sudoku.display();
        } else {
            System.out.println("Unsolvable");
        }
    }

    private boolean isInRow(int row, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == number)
                return true;

        return false;
    }

    private boolean isInCol(int col, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[i][col] == number)
                return true;
        return false;
    }

    private boolean isInBox(int row, int col, int number) {

        int r = row - row % (SIZE / 3);
        int c = col - col % 3;
        for (int i = r; i < r + SIZE / 3; i++)
            for (int j = c; j < c + 3; j++)
                if (board[i][j] == number)
                    return true;
        return false;
    }

    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number);
    }

    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            board[row][col] = number;
                            if (solve()) { // backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                board[row][col] = EMPTY;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true; // sudoku solved
    }

    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }

}
