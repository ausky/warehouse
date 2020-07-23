import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.*;

/**
 * @author ausky
 * @date 2020/3/3 13:20
 * @desc TODO
 */
public class PoiDemo {

    private static final Map<String, List<String>> ALL_NODE_NAME = new HashMap<>();

    public static void main(String[] args) throws Exception {
        readFile("/Users/aohaiyang/Desktop/产线ccms.xls");

        System.out.println("size:" + ALL_NODE_NAME.size());
    }


    private static final void readFile(String filePath) throws Exception {
        Workbook workbook = WorkbookFactory.create(new File(filePath));

        Sheet sheet = workbook.getSheetAt(2);

        int i = 0;
        Iterator<Row> rowIterable = sheet.rowIterator();
        while (rowIterable.hasNext()) {
            i++;
            Row row = rowIterable.next();
            Cell groupSheet = row.getCell(0);
            Cell nodeNameSheet = row.getCell(1);

            if (groupSheet != null && nodeNameSheet != null) {
                String group = groupSheet.getStringCellValue();
                String nodeName = nodeNameSheet.getStringCellValue();

                if (nodeName.contains(group)) {
                    String key = nodeName.substring(group.length());
                    if (!ALL_NODE_NAME.containsKey(nodeName.substring(group.length()))) {

                    }
                } else {
                    System.out.println("废弃数据:" + group + ":" + nodeName);
                }
            } else {
                System.out.println("null line :" + i);
            }
        }
    }
}
