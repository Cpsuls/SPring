package com.example.demo.rnn;

import com.example.demo.controller.MeteoDataProcessor;


public class Rnn_class{
    double[][] y1; double[] err; double[] h2_f; double[][] W1; double[][] W2; double[][] ty1; double[] ty2;
    double[][] V1; double[][] V2; double[][] U1; double[][] U2; double[][] by; double[] y2; double[][] th1;
    double[][] bh; double[][] Ws; double[] bs; double[][] h1; double[][] h2; double[] ans; double[][] th2;
    int neiron; int size; int input; int output;
    neiron_metod metod = new neiron_metod();
    dop_metods_1 dop = new dop_metods_1();
    public Rnn_class(int neiron, int size, int input, int output) {
        this.ans = new double[output];
        this.err = new double[output];
        this.y1 = new double[input][neiron];
        this.y2 = new double[neiron];
        this.ty1 = new double[input][neiron];
        this.ty2 = new double[neiron];
        this.h2_f = new double[neiron];
        this.W1 = new double[size][neiron];
        this.W2 = new double[neiron][neiron];
        this.V1 = new double[neiron][neiron];
        this.V2 = new double[neiron][neiron];
        this.U1 = new double[neiron][neiron];
        this.U2 = new double[neiron][neiron];
        this.by = new double[2][neiron];
        this.bh = new double[2][neiron];
        this.Ws = new double[neiron][output];
        this.bs = new double[output];
        this.h1 = new double[input + 1][neiron];
        this.h2 = new double[input + 1][neiron];
        this.th1 = new double[input + 1][neiron];
        this.th2 = new double[input + 1][neiron];
        this.neiron = neiron;
        this.input = input;
        this.size = size;
        this.output = output;
        init();
    }
    private void init(){
        h2_f = dop.zero_mas(h2_f);
        h1 = dop.random_mat(h1);
        h2 = dop.random_mat(h2);
        h1[0] = dop.zero_mas(h1[0]);
        h2[0] = dop.zero_mas(h2[0]);
        W1 = dop.random_mat(W1);
        W2 = dop.random_mat(W2);
        Ws = dop.random_mat(Ws);
        U1 = dop.random_mat(U1);
        U2 = dop.random_mat(U2);
        V1 = dop.random_mat(V1);
        V2 = dop.random_mat(V2);
        bh[0] = dop.random_mas(bh[0]);
        bh[1] = dop.random_mas(bh[1]);
        by[0] = dop.random_mas(by[0]);
        by[1] = dop.random_mas(by[1]);
        bs = dop.random_mas(bs);
    }
    public void forward(double[][] inputs, int delema){
        for (int i = 0; i < input; i++){
            h1[i + 1] = metod.tanhActivation(dop.sum_mas(dop.sum_mas(dop.mult_mas_mat(inputs[delema - input + i + 1], W1), dop.mult_mas_mat(h1[i], U1)), bh[0]));
            y1[i] = metod.tanhActivation(dop.sum_mas(dop.mult_mas_mat(h1[i + 1], V1), by[0]));
            h2[i + 1] = metod.tanhActivation(dop.sum_mas(dop.sum_mas(dop.mult_mas_mat(y1[i], W2), dop.mult_mas_mat(h2[i], U2)), bh[1]));
        }
        y2 = metod.tanhActivation(dop.sum_mas(dop.mult_mas_mat(h2[input], V2), by[1]));
        // Полносвязный слой
        err = dop.sum_mas(dop.mult_mas_mat(y2, Ws), bs);
    }
    public void backward(Rnn_class Grad, double[][] inputs, int delema, int len_inputs) {

        Grad.err = dop.diff_mas(err, ans);
        Grad.y2 = dop.mult_mas_mat(Grad.err, dop.transMatrix(Ws));
        Grad.Ws = dop.mult_mas_mas(y2, Grad.err);
        Grad.bs = Grad.err;

        Grad.ty2 = dop.adamar(Grad.y2, metod.deTanh_mas(dop.sum_mas(dop.mult_mat_mas(V2, h2[len_inputs]), by[1])));
        Grad.h2[len_inputs] = dop.mult_mas_mat(Grad.ty2, dop.transMatrix(V2));
        Grad.V2 = dop.mult_mas_mas(h2[len_inputs], Grad.ty2);
        Grad.by[1] = Grad.ty2;

        Grad.th2[len_inputs] = dop.adamar(Grad.h2[len_inputs], metod.deTanh_mas(dop.sum_mas(dop.sum_mas(
                    dop.mult_mas_mat(y1[len_inputs - 1], W2), dop.mult_mas_mat(h2[len_inputs - 1], U2)), bh[1])));

        for (int i = 1; i < len_inputs; i++) {
            Grad.h2[len_inputs - i] = dop.mult_mas_mat(Grad.th2[len_inputs - i + 1], dop.transMatrix(U2));
            Grad.th2[len_inputs - i] = dop.adamar(Grad.h2[len_inputs - i], metod.deTanh_mas(dop.sum_mas(dop.sum_mas(
                    dop.mult_mas_mat(y1[len_inputs - i - 1], W2), dop.mult_mas_mat(h2[len_inputs - i - 1], U2)), bh[1])));

        }

        Grad.W2 = dop.mult_mas_mas(y1[len_inputs - 1], Grad.th2[len_inputs]);
        Grad.U2 = dop.mult_mas_mas(h2[len_inputs - 1], Grad.th2[len_inputs]);
        Grad.bh[1] = Grad.th2[len_inputs];

        double[][] copy = new double[Grad.W2.length][Grad.W2[0].length];
        double[] copy2 = new double[Grad.bh.length];

        for (int i = 1; i < len_inputs; i++) {
            copy = dop.mult_mas_mas(y1[len_inputs - i - 1], Grad.th2[len_inputs - i]);
            Grad.W2 = dop.sum_mat(Grad.W2, copy); // возможно здесь так нельзя, но хз
            copy = dop.mult_mas_mas(h2[len_inputs - i - 1], Grad.th2[len_inputs - i]);
            Grad.U2 = dop.sum_mat(Grad.U2, copy); // возможно здесь так нельзя, но хз
            copy2 = Grad.th2[len_inputs - i];
            Grad.bh[1] = dop.sum_mas(Grad.bh[1], copy2);
        }

        for (int i = 0; i < len_inputs; i++) {
            Grad.y1[len_inputs - i - 1] = dop.mult_mas_mat(Grad.th2[len_inputs - i], dop.transMatrix(W2));
            Grad.ty1[len_inputs - i - 1] = dop.adamar(Grad.y1[len_inputs - i - 1], metod.deTanh_mas(dop.sum_mas(dop.mult_mas_mat
                    (h1[len_inputs - i], V1), by[0])));
        }

        Grad.V1 = dop.mult_mas_mas(h1[len_inputs], Grad.ty1[len_inputs - 1]);
        Grad.by[0] = Grad.ty1[len_inputs - 1];

        for (int i = 1; i < len_inputs; i++) {
            copy = dop.mult_mas_mas(h1[len_inputs - i], Grad.ty1[len_inputs - i - 1]);
            Grad.V1 = dop.sum_mat(Grad.V1, copy);
            copy2 = Grad.ty1[len_inputs - i - 1];
            Grad.by[0] = dop.sum_mas(Grad.by[0], copy2);
        }

        Grad.h1[len_inputs] = dop.mult_mas_mat(Grad.ty1[len_inputs - 1], dop.transMatrix(V1));
        Grad.th1[len_inputs] = dop.adamar(Grad.h1[len_inputs], metod.deTanh_mas(dop.sum_mas
                (dop.sum_mas(dop.mult_mas_mat(inputs[delema], W1),
                        dop.mult_mas_mat(h1[len_inputs - 1], U1)), bh[0])));

        for (int i = 1; i < len_inputs; i++) {
            Grad.h1[len_inputs - i] = dop.mult_mas_mat(Grad.th1[len_inputs - i + 1], dop.transMatrix(U1));
            Grad.th1[len_inputs - i] = dop.adamar(Grad.h1[len_inputs - i], metod.deTanh_mas(dop.sum_mas
                    (dop.sum_mas(dop.mult_mas_mat(inputs[delema - i], W1),
                            dop.mult_mas_mat(h1[len_inputs - i - 1], U1)), bh[0])));
        }

        Grad.U1 = dop.mult_mas_mas(h1[len_inputs - 1], Grad.th1[len_inputs]);
        Grad.W1 = dop.mult_mas_mas(inputs[delema], Grad.th1[len_inputs]);
        Grad.bh[0] = Grad.th1[len_inputs];

        for (int i = 1; i < len_inputs; i++){
            copy = dop.mult_mas_mas(h1[len_inputs - i - 1], Grad.th1[len_inputs - i]);
            Grad.U1 = dop.sum_mat(Grad.U1, copy);
            copy2 = Grad.th1[len_inputs - i];
            Grad.bh[0] = dop.adamar(Grad.bh[0], copy2);
            copy = dop.mult_mas_mas(inputs[delema - i], Grad.th1[len_inputs - i]);
            Grad.W1 = dop.sum_mat(Grad.W1, copy);
        }
    }
    public void update(Rnn_class Grad, double learnRate){
        neiron_metod metod = new neiron_metod();
        this.Ws = metod.update_mat(this.Ws, Grad.Ws, learnRate);
        this.bs = metod.update_mas(this.bs, Grad.bs, learnRate);
        this.W2 = metod.update_mat(this.W2, Grad.W2, learnRate);
        this.U2 = metod.update_mat(this.U2, Grad.U2, learnRate);
        this.bh = metod.update_mat(this.bh, Grad.bh, learnRate);
        this.V2 = metod.update_mat(this.V2, Grad.V2, learnRate);
        this.W1 = metod.update_mat(this.W1, Grad.W1, learnRate);
        this.U1 = metod.update_mat(this.U1, Grad.U1, learnRate);
        this.V1 = metod.update_mat(this.V1, Grad.V1, learnRate);
        this.by = metod.update_mat(this.by, Grad.by, learnRate);
    }
    public void fit(double[][] inputs, double[] target, int epoh, double learnRate) {
        Rnn_class Grad = new Rnn_class(neiron, size, input, output);
        Grad.init();
        int len = inputs.length;
        double loss = 0;
        double globalloss = 0;
        for (int ep = 0; ep < epoh; ep++){
            System.out.println("Globalloss: " + ep + " " + globalloss / len);
            globalloss = 0;
            for (int i = input - 1; i < len - output + 1; i++) {
                globalloss += loss;
                for (int y = 0; y < output; y++) {
                    ans[y] = target[i + y];
                }
                forward(inputs, i);
                loss = metod.Loss_MAE(ans, err);
                globalloss += loss;
                //System.out.print("Надо предсказать: " + MeteoDataProcessor.deNormal(target[i]));
                //System.out.println("А мы предсказали: " + MeteoDataProcessor.deNormal(err);
                backward(Grad, inputs, i,  input);
                update(Grad, learnRate);
            }
        }
    }
    public double[] predict(double[][] inputs, int output_predict){
        // Вытащим из данных input последних значений
        double[][] array_for_predict = MeteoDataProcessor.get_last(inputs, input);
        dop.read_mat(array_for_predict);
        forward(array_for_predict, input - 1);
        return err;
    }
}

