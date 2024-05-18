package com.example.demo.rnn;

import java.util.Random;

public class dop_metods_1 {
    public static void read_mat(double[][] data) { // Чтение матриц
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
    public static void read_mas(double[] data) { // Чтение массивов
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println(" ");
    }
    public static double[] mult_mas_mat(double[] array, double[][] matrix) { // Перемножение массива и матрицы
        int rows = matrix.length; //2
        int cols = matrix[0].length; //3

        double[] result = new double[cols];

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                result[i] += array[j] * matrix[j][i];
            }
        }
        return result;
    }

    public static double[] mult_mas_num(double[] array, double num) { // Перемножение массива и матрицы

        double[] result = new double[array.length];

        for (int j = 0; j < array.length; j++) {
            result[j] = array[j] * num;
        }
        return result;
    }
    public double[][] mult_mas_mas(double[] columnMatrix, double[] rowMatrix) {
        double[][] resultMatrix = new double[columnMatrix.length][rowMatrix.length];
        for (int i = 0; i < columnMatrix.length; i++) {
            for (int j = 0; j < rowMatrix.length; j++) {
                resultMatrix[i][j] = columnMatrix[i] * rowMatrix[j];
            }
        }
        return resultMatrix;
    }
    public double[] mult_mas_mas_one(double[] columnMatrix, double[] rowMatrix) {
        double[] res = new double[1];
        for (int i = 0; i < columnMatrix.length; i++) {
            res[0] += columnMatrix[i] * rowMatrix[i];
        }
        return res;
    }

    public static double[] mult_mat_mas(double[][] matrix, double[] vector) {
        double[] result = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }

    public static double[][] random_mat(double[][] matrix) { // Рандомное заполнение матрицы
        Random random = new Random();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextDouble() * 0.1;
            }
        }
        return matrix;
    }
    public static double[] random_mas(double[] matrix) { // Рандомное заполнение массива
        Random random = new Random();

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = random.nextDouble() * 0.1;
        }
        return matrix;
    }
    public static double[] zero_mas(double[] matrix) { // Нулевое заполнение массива
        Random random = new Random();

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = 0; // Генерация случайного числа от -2 до 5
        }
        return matrix;
    }
    public static double[][] sum_mat(double[][] matrix1, double[][] matrix2) { // Сложение матриц
        int rows = matrix1.length;
        int cols = matrix1[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }
    public static double[] sum_mas(double[] mas1, double[] mas2) { // Сложение маcивов
        int rows = mas1.length;

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            result[i] = mas1[i] + mas2[i];
        }

        return result;
    }
    public static double[] diff_mas(double[] mas1, double[] mas2) {
        int rows = mas1.length;

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            result[i] = mas1[i] - mas2[i];
        }

        return result;
    }
    public static double[][] mult_one_one(double[] mas1, double[] mas2){
        double[][] result = new double[mas1.length][mas1.length];
        for (int i = 0; i < mas1.length; i++){
            for (int j = 0; j < mas1.length; j++){
                result[i][j] = mas1[i] * mas2[j];
            }
        }
        return result;
    }
    public static double[][] transMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] transposedMatrix = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }
        return transposedMatrix;
    }
    public static double[] adamar(double[] arr1, double[] arr2){
        double[] result = new double[arr1.length];
        for(int i = 0; i < arr1.length; i++){
            result[i] = arr1[i] * arr2[i];
        }
        return result;
    }
}
