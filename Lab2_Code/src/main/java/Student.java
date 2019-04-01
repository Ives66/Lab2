import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private String studentId;
    private String git;



    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getGit() {
        return this.git;
    }

    public void setId(int numericCellValue) {
        this.id=numericCellValue;
    }

    public void setName(String stringCellValue) {
        this.name=stringCellValue;
    }

    public void setStudentId(String stringCellValue) {
        this.studentId=stringCellValue;
    }

    public void setGit(String parse) {
        this.git=parse;
    }

    public static List<Student> readXls() throws Exception {
        InputStream is = new FileInputStream("software_testing_list.xlsx");

        XSSFWorkbook excel = new XSSFWorkbook(is);
        Student stu = null;
        List<Student> list = new ArrayList<Student>();

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            XSSFSheet sheet = excel.getSheetAt(numSheet);
            if (sheet == null)
                continue;
            // 循环行Row
            for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null)
                    continue;
                stu = new Student();
                XSSFCell cell0 = row.getCell(0);
                stu.setId((int)cell0.getNumericCellValue());
                XSSFCell cell1 = row.getCell(1);
                DecimalFormat df = new DecimalFormat("0");
                stu.setStudentId(String.valueOf(df.format(cell1.getNumericCellValue())));
                XSSFCell cell2 = row.getCell(2);
                stu.setName(cell2.getStringCellValue());
                XSSFCell cell3 = row.getCell(3);
                stu.setGit(cell3.getStringCellValue().replace(" ",""));
                list.add(stu);
            }
        }

        return list;
    }

}
