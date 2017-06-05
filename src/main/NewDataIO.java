package main;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by lizhaofu on 2017/3/9.
 * 输出分词结果
 */
public class NewDataIO {
    //读取文件两列
    public static List ReadData(String fileName) {
        List<Province> provinceList = new ArrayList<Province>();
        try {
            InputStream is = new FileInputStream(fileName);//连续三行为读取Excel文件的初始化工作
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int nrows = xssfSheet.getLastRowNum();//
            for (int i = 0; i < nrows; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i +1);//选中Excel表中的某一行
                XSSFCell cellProvince = xssfRow.getCell(0);//得到省份信息
                if (cellProvince != null) {
                    Province province = new Province();
                    province.setProvinceContext(cellProvince.toString());
                    List<Field> provinceFieldList = new ArrayList<Field>();
                    for (int j = 0; j < 1; j++) {
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

    //输出特征库
    public static void NewDataPWrite(List<Province> provinceList, String filePath) {
        try {
            OutputStream os = new FileOutputStream(filePath + "特征库.xlsx");//一下三行为写入Excel初始化操作
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("特征库");
            SXSSFRow row;
            row = (SXSSFRow) sheet.createRow(0);
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
                        set.add(list.get(k));
                    }
                }
            }
            List<String> slist = new ArrayList();
            slist.addAll(set);
            for (int k = 0; k < slist.size(); k++) {
                row = (SXSSFRow) sheet.createRow(k);
                row.createCell(0).setCellValue(slist.get(k));
            }
            wb.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
