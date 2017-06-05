package test;


import main.AnalysisIO;
import main.EuclideanSimilarity;

/**
 * Created by lizhaofu on 2017/3/8.
 */
public class EuclideanSimilarityTest {
    public static void main(String[] args) {
        String fileName = "data/9/向量化.xlsx";
        String outFileName = "data/9/";
        EuclideanSimilarity.DistanceWrite(EuclideanSimilarity.ReadData(fileName),outFileName);//相似度

    }
}
