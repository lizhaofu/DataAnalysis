package test;

import main.AnalysisIO;

/**
 * Created by lizhaofu on 2017/3/9.
 */
public class VectorIOTest {
    public static void main(String[] args) {
        String fileName = "data/1/分词结果.xlsx";
        String outFileName = "data/1/";
        String fileNameA = "data/1/特征库.xlsx";
        //DataIO.ReadData(fileName);
        //DataIO.hanyuLPWrite(DataIO.ReadData(fileName),outFileName);
        AnalysisIO.hanyuLPWrite(AnalysisIO.SetDataRead(fileNameA), AnalysisIO.ReadData(fileName), outFileName);
    }
}
