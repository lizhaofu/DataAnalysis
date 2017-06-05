package main;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

import static java.awt.SystemColor.text;

/**
 * Created by winmaster on 2017/3/7.
 */
public class VectorIO {
    public static int sum(int[] aa, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + aa[i];

        }
        return sum;
    }

    public static List ReadData(String fileName) {
        List<Province> provinceList = new ArrayList<Province>();

        try {
            InputStream is = new FileInputStream(fileName);//连续三行为读取Excel文件的初始化工作
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int nrows = xssfSheet.getLastRowNum();//

            for (int i = 1; i < nrows; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);//选中Excel表中的某一行
                XSSFCell cellProvince = xssfRow.getCell(0);//读取第一列的省份信息一下五行为从excel表中读取各列的数据
                if (cellProvince != null) {
                    int[] provinceArray = {17, 8, 8, 10, 4, 12, 4, 9, 7, 12, 1, 10, 12, 8, 15, 11, 1, 1, 1, 7, 20, 4, 14, 11, 10, 23, 10, 5, 11, 11};
                    Province province = new Province();
                    province.setProvinceContext(cellProvince.toString());
                    List<Field> provinceFieldList = new ArrayList<Field>();
                    for (int j = 1; j < provinceArray[provinceList.size()] + 1; j++) {
                        //System.out.println(provinceArray[provinceList.size()]);
                        XSSFRow xssfRowF = xssfSheet.getRow(j + sum(provinceArray, (provinceList.size())));//选中Excel表中的某一行
                        XSSFCell cellField = xssfRowF.getCell(1);//读取第2列的省份信息一下五行为从excel表中读取各列的数据
                        if (cellField != null) {
                            Field field = new Field();
                            field.setFieldContext(cellField.toString());
                            List<SubField> fieldSubFieldList = new ArrayList<SubField>();
                            for (int k = 0; k < 1; k++) {
                                XSSFRow xssfRowS = xssfSheet.getRow(j + sum(provinceArray, (provinceList.size())));//选中Excel表中的某一行
                                XSSFCell cellSubFiled = xssfRowF.getCell(2);//读取第3列的省份信息一下五行为从excel表中读取各列的数据
                                if (cellSubFiled != null) {
                                    String[] subFiledsArray = cellSubFiled.getStringCellValue().split("，");
                                    for (int p = 0; p < subFiledsArray.length; p++) {
                                        SubField subField = new SubField();
                                        subField.setSubFieldContext(subFiledsArray[p]);
                                        fieldSubFieldList.add(subField);
                                    }
                                }
                            }
                            field.setFieldSubFields(fieldSubFieldList);
                            provinceFieldList.add(field);
                        }
                    }
                    province.setProvinceFields(provinceFieldList);
                    provinceList.add(province);
                }
            }
            int a = 0;

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
            OutputStream os = new FileOutputStream(filePath + "hanLP7.xlsx");//一下三行为写入Excel初始化操作
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("hanLP7");
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
                    List<Term> termList = NLPTokenizer.segment(tmp);
                    for (Term term : termList) {
                        String strTerm = term.toString();
                        String[] strTermArray = strTerm.split("/");
                        if (strTermArray.length == 2 && !strTermArray[1].equals("p") && !strTermArray[1].equals("c")
                                && !strTermArray[1].equals("w") && strTermArray[0].length() > 1) {
                            list.add(strTermArray[0]);
                        }
                    }
                    List<Integer> vectorList = new ArrayList<Integer>();
                    Map<String, Integer> hashMap = new HashMap<String, Integer>();
                    for (String temp : list) {
                        Integer count = hashMap.get(temp);
                        hashMap.put(temp, (count == null) ? 1 : count + 1);
                    }
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
            }
            wb.write(os);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

