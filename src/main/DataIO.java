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
public class DataIO {
    public static int sum(int[] aa, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + aa[i];
        }
        return sum;
    }
    //读取各省份的数据
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

    //输出分词结果
    public static void hanyuLPWrite(List<Province> provinceList, String filePath) {
        try {
            OutputStream os = new FileOutputStream(filePath + "分词结果.xlsx");//一下三行为写入Excel初始化操作
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("分词结果");
            SXSSFRow row;
            row = (SXSSFRow) sheet.createRow(0);
            row.createCell(0).setCellValue("省份");//写入第一行各列数据
            row.createCell(1).setCellValue("重点子领域分词结果");//写入第一行各列数据
            Set<String> set = new HashSet();
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
                    for (int k = 0; k < list.size(); k++) {
                        row.createCell(k + 1).setCellValue(list.get(k));
                        set.add(list.get(k));
                        Map<String, Integer> hashMap = new HashMap<String, Integer>();
                        for (String temp : list) {
                            Integer count = hashMap.get(temp);
                            hashMap.put(temp, (count == null) ? 1 : count + 1);
                        }
                    }
                }
            }
            wb.write(os);
            os.close();
            int i = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






