package com.company;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        // создание лабиринта
        Node[][] matrix = new Node[5][7];
        boolean[][] visited = new boolean[5][7];
        ArrayList<Pair> path = new ArrayList<Pair>();
        int end_i = 2, end_j = 6;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                matrix[i][j] = new Node(0, 0, false);
                visited[i][j] = false;
                matrix[i][j].i = i;
                matrix[i][j].j = j;
                matrix[i][j].blue = (Math.abs(end_j - j) + Math.abs(end_i - i)) * 10;
            }
        }
        // вручную задаю стены
        matrix[0][3].setIs_wall(true);
        matrix[1][3].setIs_wall(true);
        matrix[1][2].setIs_wall(true);
        matrix[1][1].setIs_wall(true);
        matrix[2][2].setIs_wall(true);
        matrix[2][5].setIs_wall(true);
        matrix[3][2].setIs_wall(true);
        matrix[3][4].setIs_wall(true);
        matrix[3][5].setIs_wall(true);
        matrix[0][5].setIs_wall(true);
        matrix[4][4].setIs_wall(true);
        // вручную задаю начало и конец
        matrix[0][0].setIs_start(true);
        matrix[2][6].setIs_end(true);
        int curr_i = 0;
        int curr_j = 0;
        int start_i = curr_i;
        int start_j = curr_j;
        visited[curr_i][curr_j] = true;
        // вывод до поиска пути
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (!matrix[i][j].isIs_way()) {
                    if (matrix[i][j].isIs_wall()) {
                        System.out.print("[W]");
                    } else {
                        System.out.print("[O]");
                    }
                } else {
                    System.out.print("[X]");
                }
            }
            System.out.print('\n');
        }
        System.out.print('\n');
        // сам алгоритм
        while (curr_i != end_i || curr_j != end_j) {
            visited[curr_i][curr_j] = true;
            matrix[curr_i][curr_j].setIs_way(true);
            path.add(new Pair(curr_i, curr_j));
            int min_red = Integer.MAX_VALUE;
            int min_i = curr_i, min_j =  curr_j;
            if (curr_i > 0 && !matrix[curr_i - 1][curr_j].isIs_wall() && !visited[curr_i - 1][curr_j]) {
                matrix[curr_i - 1][curr_j].green = matrix[curr_i][curr_j].green + 10;
                if (min_red > (matrix[curr_i - 1][curr_j].green + matrix[curr_i - 1][curr_j].blue)) {
                    min_red = matrix[curr_i - 1][curr_j].green + matrix[curr_i - 1][curr_j].blue;
                    min_i = curr_i - 1;
                    min_j = curr_j;
                }
            }
            if (curr_i > 0 && curr_j < matrix[0].length - 1 && !matrix[curr_i - 1][curr_j + 1].isIs_wall() && !visited[curr_i - 1][curr_j + 1]) {
                matrix[curr_i - 1][curr_j + 1].green = matrix[curr_i][curr_j].green + 14;
                if (min_red > (matrix[curr_i - 1][curr_j + 1].green + matrix[curr_i - 1][curr_j + 1].blue)) {
                    min_red = matrix[curr_i - 1][curr_j + 1].green + matrix[curr_i - 1][curr_j + 1].blue;
                    min_i = curr_i - 1;
                    min_j = curr_j + 1;
                }
            }
            if (curr_j < matrix[0].length - 1 && !matrix[curr_i][curr_j + 1].isIs_wall() && !visited[curr_i][curr_j + 1]) {
                matrix[curr_i][curr_j + 1].green = matrix[curr_i][curr_j].green + 10;
                if (min_red > (matrix[curr_i][curr_j + 1].green + matrix[curr_i][curr_j + 1].blue)) {
                    min_red = matrix[curr_i][curr_j + 1].green + matrix[curr_i][curr_j + 1].blue;
                    min_i = curr_i;
                    min_j = curr_j + 1;
                }
            }
            if (curr_j < matrix[0].length - 1 && curr_i < matrix.length - 1 && !matrix[curr_i + 1][curr_j + 1].isIs_wall() && !visited[curr_i + 1][curr_j + 1]) {
                matrix[curr_i + 1][curr_j + 1].green = matrix[curr_i][curr_j].green + 14;
                if (min_red > (matrix[curr_i + 1][curr_j + 1].green + matrix[curr_i + 1][curr_j + 1].blue)) {
                    min_red = matrix[curr_i + 1][curr_j + 1].green + matrix[curr_i + 1][curr_j + 1].blue;
                    min_i = curr_i + 1;
                    min_j = curr_j + 1;
                }
            }
            if (curr_i < matrix.length - 1 && !matrix[curr_i + 1][curr_j].isIs_wall() && !visited[curr_i + 1][curr_j]) {
                matrix[curr_i + 1][curr_j].green = matrix[curr_i][curr_j].green + 10;
                if (min_red > (matrix[curr_i + 1][curr_j].green + matrix[curr_i + 1][curr_j].blue)) {
                    min_red = matrix[curr_i + 1][curr_j].green + matrix[curr_i + 1][curr_j].blue;
                    min_i = curr_i + 1;
                    min_j = curr_j;
                }
            }
            if (curr_i < matrix.length - 1 && curr_j > 0 && !matrix[curr_i + 1][curr_j - 1].isIs_wall() && !visited[curr_i + 1][curr_j - 1]) {
                matrix[curr_i + 1][curr_j - 1].green = matrix[curr_i][curr_j].green + 14;
                if (min_red > (matrix[curr_i + 1][curr_j - 1].green + matrix[curr_i + 1][curr_j - 1].blue)) {
                    min_red = matrix[curr_i + 1][curr_j - 1].green + matrix[curr_i + 1][curr_j - 1].blue;
                    min_i = curr_i + 1;
                    min_j = curr_j - 1;
                }
            }
            if (curr_j > 0 && !matrix[curr_i][curr_j - 1].isIs_wall() && !visited[curr_i][curr_j - 1]) {
                matrix[curr_i][curr_j - 1].green = matrix[curr_i][curr_j].green + 10;
                if (min_red > (matrix[curr_i][curr_j - 1].green + matrix[curr_i][curr_j - 1].blue)) {
                    min_red = matrix[curr_i][curr_j - 1].green + matrix[curr_i][curr_j - 1].blue;
                    min_i = curr_i;
                    min_j = curr_j - 1;
                }
            }
            if (curr_j > 0 && curr_i > 0 && !matrix[curr_i - 1][curr_j - 1].isIs_wall() && !visited[curr_i - 1][curr_j - 1]) {
                matrix[curr_i - 1][curr_j - 1].green = matrix[curr_i][curr_j].green + 14;
                if (min_red > (matrix[curr_i - 1][curr_j - 1].green + matrix[curr_i - 1][curr_j - 1].blue)) {
                    min_red = matrix[curr_i - 1][curr_j - 1].green + matrix[curr_i - 1][curr_j - 1].blue;
                    min_i = curr_i - 1;
                    min_j = curr_j - 1;
                }
            }
            if (min_i == start_i && min_j == start_j) {
                System.out.print("There is no path from start to end\n");
                break;
            }
            if (curr_i == min_i && curr_j == min_j) {
                curr_i = start_i;
                curr_j = start_j;
                for (Pair p : path) {
                    matrix[p.first][p.second].setIs_way(false);
                }
                path.clear();
                path.add(new Pair(start_i, start_j));
            } else {
                curr_i = min_i;
                curr_j = min_j;
            }
        }
        matrix[curr_i][curr_j].setIs_way(true);
        // вывод после поиска пути
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (!matrix[i][j].isIs_way()) {
                    if (matrix[i][j].isIs_wall()) {
                        System.out.print("[W]");
                    } else {
                        System.out.print("[O]");
                    }
                } else {
                    System.out.print("[X]");
                }
            }
            System.out.print('\n');
        }
    }
};
