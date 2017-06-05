package main;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
/**
 * Created by winmaster on 2017/3/7.
 * every provience field similarity
 */
public class EuclideanSimilarity {
    public static List ReadData(String fileName) {
        List<Province> provinceList = new ArrayList<Province>();
        try {
            InputStream is = new FileInputStream(fileName);//连续三行为读取Excel文件的初始化工作
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int nrows = xssfSheet.getLastRowNum();//
            for (int i = 0; i < nrows; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i + 1);//选中Excel表中的某一行
                XSSFCell cellProvince = xssfRow.getCell(0);//得到省份信息
                if (cellProvince != null) {
                    Province province = new Province();
                    province.setProvinceContext(cellProvince.toString());
                    List<Field> provinceFieldList = new ArrayList<Field>();
                    for (int j = 0; j < 300; j++) {
                        XSSFCell cellField = xssfRow.getCell(j + 1);
                        if (cellField != null) {
                            Field field = new Field();
                            field.setFieldContext(cellField.toString());
                            provinceFieldList.add(field);
                        }
                    }
                    province.setProvinceFields(provinceFieldList);
                    provinceList.add(province);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceList;
    }
    //输出相似度
    public static void DistanceWrite(List<Province> provinceList, String filePath) {
        try {
            OutputStream os = new FileOutputStream(filePath + "相似度.xlsx");//一下三行为写入Excel初始化操作
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("相似度");
            SXSSFRow row;
            row = (SXSSFRow) sheet.createRow(0);
            for (int i = 0; i < provinceList.size(); i++) {
                String str = provinceList.get(i).getProvinceContext().toString();
                row.createCell(i + 1).setCellValue(str);
            }
            for (int i = 0; i < provinceList.size(); i++) {
                row = (SXSSFRow) sheet.createRow(i + 1);
                String str = provinceList.get(i).getProvinceContext().toString();
                row.createCell(0).setCellValue(str);

                for (int j = 0; j < provinceList.size(); j++) {
                    double distance = 0;
                    if (provinceList.get(i).getProvinceFields().size() == provinceList.get(j).getProvinceFields().size())

                        for (int k = 0; k < provinceList.get(i).getProvinceFields().size(); k++) {
                            double aInt = Double.parseDouble(provinceList.get(i).getProvinceFields().get(k).getFieldContext());
                            double bInt = Double.parseDouble(provinceList.get(j).getProvinceFields().get(k).getFieldContext());
                            double temp = Math.pow(aInt - bInt, 2);
                            distance += temp;
                        }
                    distance = Math.sqrt(distance);
                    row.createCell(j + 1).setCellValue(distance);
                }
            }
            wb.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

