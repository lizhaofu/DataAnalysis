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
 * 第一次降维向量化结果
 */
public class AnalysisIO {
    public static List ReadData(String fileName) {
        List<Province> provinceList = new ArrayList<Province>();
        try {
            InputStream is = new FileInputStream(fileName);//连续三行为读取Excel文件的初始化工作
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int nrows = xssfSheet.getLastRowNum();//
            for (int i = 0; i < nrows; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i+1);//选中Excel表中的某一行
                XSSFCell cellProvince = xssfRow.getCell(0);//得到省份信息
                if (cellProvince!=null){
                    Province province = new Province();
                    province.setProvinceContext(cellProvince.toString());
                    List<Field> provinceFieldList = new ArrayList<Field>();
                    for (int j = 0; j < 300; j++) {
                        XSSFCell cellField = xssfRow.getCell(j+1);
                        if (cellField != null) {
                            Field field = new Field();
                            field.setFieldContext(cellField.toString());
                            provinceFieldList.add(field);
                        }
                    }
                    province.setProvinceFields(provinceFieldList);
                    provinceList.add(province);
                }
                int a =0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    public static List<String> SetDataRead(String fileName) {
        List<String> list = new ArrayList<String>();
        try {
            InputStream is = new FileInputStream(fileName);//连续三行为读取Excel文件的初始化工作
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int nrows = xssfSheet.getLastRowNum();//
            for (int i = 1; i < nrows; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);//选中Excel表中的某一行
                XSSFCell vector = xssfRow.getCell(0);//读取第一列
                if (vector != null)
                    list.add(vector.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static void hanyuLPWrite(List<String> setDataReadList, List<Province> provinceList, String filePath) {
        try {
            OutputStream os = new FileOutputStream(filePath + "向量化.xlsx");//一下三行为写入Excel初始化操作
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("向量化");
            SXSSFRow row;
            row = (SXSSFRow) sheet.createRow(0);
            for (int i = 0; i < setDataReadList.size(); i++) {
                row.createCell(i+ 1).setCellValue(setDataReadList.get(i));
            }
            for (int i = 0; i < provinceList.size(); i++) {
                row = (SXSSFRow) sheet.createRow(i + 1);
                String str = provinceList.get(i).getProvinceContext().toString();
                row.createCell(0).setCellValue(str);
                List<String> list = new ArrayList<String>();
                Map map = new HashMap();
                for (int j = 0; j < provinceList.get(i).getProvinceFields().size(); j++) {
                    String tmp = provinceList.get(i).getProvinceFields().get(j).getFieldContext();
                    list.add(tmp);
                    List<Integer> vectorList = new ArrayList<Integer>();
                    Map<String, Integer> hashMap = new HashMap<String, Integer>();
                    for (String temp : list) {
                        Integer count = hashMap.get(temp);
                        hashMap.put(temp, (count == null) ? 1 : count + 1);
                    }
                    int b =0;
                    for (int k = 0; k < setDataReadList.size(); k++) {
                        if(hashMap.containsKey(setDataReadList.get(k))){
                            vectorList.add(hashMap.get(setDataReadList.get(k)));
                        }else {
                            vectorList.add(0);
                        }
                    }
                    for (int k = 0; k < vectorList.size(); k++) {
                        row.createCell(k + 1).setCellValue(vectorList.get(k));

                    }

                }
                int a =0;
            }
            wb.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

