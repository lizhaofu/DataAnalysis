package test;

import main.AnalysisIO;
import main.NewDataIO;

/**
 * Created by lizhaofu on 2017/3/8.
 */
public class AnalysisIOTest {
    //降维，并进行向量化
    public static void main(String[] args) {
        String fileName = "data/9/分词结果.xlsx";
        String outFileName = "data/9/";
        String fileNameA = "data/9/特征库.xlsx";
        //DataIO.ReadData(fileName);
        //DataIO.hanyuLPWrite(DataIO.ReadData(fileName),outFileName);
        AnalysisIO.hanyuLPWrite(AnalysisIO.SetDataRead(fileNameA), AnalysisIO.ReadData(fileName),outFileName);//向量化结果

    }
}
