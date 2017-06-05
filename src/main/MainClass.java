package main;

/**
 * Created by winmaster on 2017/3/7.
 */
public class MainClass {
    public static void main(String[] args) {
        String fileName = "data/new/新能源原始分词数据.xlsx";
        String outFileName = "data/new/";
        String fileNameA = "data/特征库.xlsx";
        //DataIO.ReadData(fileName);
        DataIO.hanyuLPWrite(NewDataIO.ReadData(fileName),outFileName);
        //AnalysisIO.hanyuLPWrite(AnalysisIO.SetDataRead(fileNameA),AnalysisIO.ReadData(fileName),outFileName);




    }
}
