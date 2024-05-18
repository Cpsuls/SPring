package com.example.demo.rnn;

public class neiron_metod {
    public double[] tanhActivation(double[] inputo) {
        double[] outputo = new double[inputo.length];

        for (int i = 0; i < inputo.length; i++) {
            outputo[i] = Math.tanh(inputo[i]);
            //outputo[i] = 1 / (1 + Math.exp(-inputo[i]));
        }
        return outputo;
    }
    public double[] deTanh_mas(double[] x) {
        double[] res = new double[x.length];
        for (int i = 0; i < x.length; i++){
            //res[i] = 1 - Math.pow(Math.tanh(x[i]), 2);
            //res[i] = x[i] * (1 - x[i]);
            res[i] = 1 / Math.pow(Math.cos(x[i]), 2);
        }
        return res;
    }

    public static double Loss_MAE(double[] trueValues, double[] predictedValues) {
        double sumSquaredErrors = 0.0;
        for (int i = 0; i < trueValues.length; i++) {
            sumSquaredErrors += Math.abs(trueValues[i] - predictedValues[i]);
        }

        return sumSquaredErrors / trueValues.length;
    }
    public static double[] Loss_MAE_mas(double[] trueValues, double[] predictedValues) {
        double[] sumSquaredErrors = new double[trueValues.length];
        for (int i = 0; i < trueValues.length; i++) {
            sumSquaredErrors[i] = Math.abs(trueValues[i] - predictedValues[i]);
        }
        return sumSquaredErrors;
    }

    public static double[][] update_mat(double[][] mat, double[][] grad, double learn){
        double[][] result = new double[mat.length][mat[0].length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                result[i][j] = mat[i][j] - (learn * grad[i][j]);
            }
        }
        return result;
    }
    public static double[] update_mas(double[] mat, double[] grad, double learn){
        double[] result = new double[mat.length];
        for(int i = 0; i < mat.length; i++){
            result[i] = mat[i] - (learn * grad[i]);
        }
        return result;
    }
    public static double[] denorm(double[] ans, double dif, double min){
        double[] result = new double[ans.length];
        for (int i = 0; i < ans.length; i++){
            result[i] = ans[i] * dif + min;
        }
        return result;
    }
}
