//package com.example.demo.rnn;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class main_rnnka {
//    public static void Rnnnn() throws Exception {
//        // Эмперика которую получаем с сайта. Эти данные нужны от Кости
////        int output_predict = 2;(кол во дней) // Данные с сайта на сколько дней нужно предсказывать
////        int code_post = 76380;(код) // Код поста
////        int[] stolb_num = {1, 2, 3, 4, 5};(dummies) // Это колличество столбиков в наших обучаемых данных или колличество входных нейронов
//
//
//        // Эмперика которую мы задаем сами
////        int neiron = 7;
////        int epoh = 2;
////        double learnRate = 0.95;
////        int input = 10; // Для обучения (на основе этих последних дней делаем предсказание)
////        int size = stolb_num.length;
//
//        // Объект нейронки
////        Rnn_class Rnn = new Rnn_class(neiron, size, input, output_predict);
////        dop_metods_1 dop = new dop_metods_1();
//
//        // Работа с данными из Excel (нужно будет заменить на другой метод работы c MySql
//        //String path = "C:\\Users\\User\\Desktop\\dat_read.xlsx";
//        //main_ExcelReader Excel = new main_ExcelReader(size);
//        //List<double[]> data = Excel.readExcelFile(path);
//        //double[][] inputs = new double[data.size() / 2][];
//        //double[] targets = new double[data.size() / 2];
//
//        //for (int i = 0; i < data.size(); i += 2) {
//        //    inputs[i / 2] = data.get(i);
//        //    targets[i / 2] = data.get(i + 1)[0];
//        //}Вкид:Скачавание результатов
//
//        // Доставание данных из БД
//        MeteoDataProcessor Metos = new MeteoDataProcessor();
//        double[][] inputs_do = Metos.processMeteoData(code_post);
//        double[][] inputs = new double[inputs_do.length][stolb_num.length];
//        for (int i = 0; i < inputs_do.length; i++){
//            for (int y = 0; y < stolb_num.length; y++){
//                inputs[i][y] = inputs_do[i][stolb_num[y] - 1];
//            }
//        }
//        double[] targets = Metos.targets;
//        Metos.globalMaxMinElement(targets);
//        System.out.println(targets.length);
//
//        // Нормализация данных
//        double[][] detargets = Metos.convert(targets);
//        inputs = Metos.MinMaxScaller(inputs);
//        detargets = Metos.MinMaxScaller(detargets);
//        for (int i = 0; i < detargets.length; i++){
//            targets[i] = detargets[i][0];
//        }
//        dop.read_mas(targets);
//        // Обучение
//        Rnn.fit(inputs, targets, epoh, learnRate);
//        // Предсказание
//        double[] predict_result = Rnn.predict(inputs, output_predict);
//        // Денормализация
//        predict_result = Metos.deNormal(predict_result);
//        // Вывод результата
//        dop.read_mas(predict_result);
//    }
//}
