package test;

import main.AnalysisIO;
import main.DataIO;
import main.NewDataIO;

/**
 * Created by lizhaofu on 2017/3/9.
 */
public class DataIOTest {
    public static void main(String[] args) {
        String fileName = "data/2/新.xlsx";
        String outFileName = "data/2/";
        String fileNameA = "data/1/新材料特征库.xlsx";
        //DataIO.ReadData(fileName);
        DataIO.hanyuLPWrite(NewDataIO.ReadData(fileName),outFileName);
       // AnalysisIO.hanyuLPWrite(AnalysisIO.SetDataRead(fileNameA),AnalysisIO.ReadData(fileName),outFileName);




    }
}
