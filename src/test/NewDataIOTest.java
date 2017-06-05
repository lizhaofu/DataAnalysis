package test;

import main.DataIO;
import main.NewDataIO;

/**
 * Created by lizhaofu on 2017/3/9.
 */
public class NewDataIOTest {
    public static void main(String[] args) {
        String fileName = "data/9/新.xlsx";

        String outFileName = "data/9/";


        DataIO.hanyuLPWrite(NewDataIO.ReadData(fileName),outFileName);//分词结果
        NewDataIO.NewDataPWrite(NewDataIO.ReadData(fileName),outFileName);//特征库





    }
}
