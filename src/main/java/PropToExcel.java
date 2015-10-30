import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
 
public class PropToExcel {
 
    // Create a HashMap which will store the properties 
    HashMap< String, String > propMap = new HashMap< String, String >();
 
 
    public static void main(String[] args) {
 
    	System.setProperty("file.encoding", "UTF-8");
    	
    	
        // Create object of ReadWriteXlsProperties
    	PropToExcel readWriteXlsDemo = new PropToExcel();
 
        // Call method readProperties() it take path to properties file
        readWriteXlsDemo.readProperties("labels_ar.properties");
 
        // Call method writeToExcel() it will take path to excel file
        readWriteXlsDemo.writeToExcel("test_ar.xls");
 
    }
 
 
    private void readProperties(String propertiesFilePath) {
 
        // Create a File object taking in path of properties
        // file
        File propertiesFile = new File(propertiesFilePath);
 
        // If properties file is a file do below stuff
        if(propertiesFile.isFile()){
            try{
                // Create a FileInputStream for loading the properties file
                FileInputStream fisProp = new FileInputStream(propertiesFile);
        
 
                BufferedReader in = new BufferedReader(new InputStreamReader(fisProp, "UTF8"));
                
                // Create a Properties object and load
                // properties key and value to it through FileInputStream
                Properties properties = new Properties();   
                properties.load(in);
 
                
                // Create a object of Enumeration and call keys()
                // method over properties object created above
                // it will return us back with a Enumeration types
                Enumeration< Object > keysEnum = properties.keys();
 
                
                // Looping over the elements of Enumeration
                while(keysEnum.hasMoreElements()){
                    // Extracting the key and respective values from it.
                    String propKey = (String)keysEnum.nextElement();
                    String propValue = properties.getProperty(propKey);
 
                    // After extracting the key and value from the properties file
                    // we will store the values in a HashMap.
                    propMap.put( propKey.toLowerCase().trim(),propValue.toLowerCase().trim());
 
                }   
 
                // printing the HashMap and closing the file FileInputStream
                System.out.println("Properties Map ... \n" +  propMap);
                fisProp.close();
 
            }catch(FileNotFoundException e){                     
                e.printStackTrace();
            }
            catch(IOException e){                  
                e.printStackTrace();
            }
 
        }
 
    }
 
    private void writeToExcel(String excelPath) {
 
        // Create a Workbook using HSSFWorkbook object
        HSSFWorkbook workBook = new HSSFWorkbook();
 
        // Create a sheet with name "properties" by
        // the createSheet method of the Workbook
        HSSFSheet worksheet = workBook.createSheet("Properties");
 
        // Create a row by calling createRow method of the
        // Worksheet
        HSSFRow row = worksheet.createRow((short) 0);
 
        // Create a cell style by calling createCellStyle()
        // from the workbook
        HSSFCellStyle cellStyle = workBook.createCellStyle();
 
        // setting of the foreground and fill pattern by calling methods
        // of HSSFCellStyle as setFillForegroundColor() and setFillPattern()
 
        cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 
        // Create a HSSFCell from the row object created above
        HSSFCell cell1 = row.createCell(0);
 
        // Setting the value of the cell as the keys by calling
        // setCellValue() method over the HSSFCell
        cell1.setCellValue(new HSSFRichTextString("Keys"));
 
        // Giving it the style created above.
        cell1.setCellStyle(cellStyle);
 
        HSSFCell cell2 = row.createCell(1);
        cell2.setCellValue(new HSSFRichTextString("Values"));                
        cell2.setCellStyle(cellStyle);
 
        // Create a Iterator and as  propMap is a HashMap
        // it is converted to a HashSet by calling keySet() method
        // which will return with Set.
        // Iterator object is pointed to keys of Set

        // Looping across the elements of Iterator
        for (String s : propMap.keySet()) {
            // Creating a new row from the worksheet
            // at the last used row + 1 location
            HSSFRow rowOne = worksheet.createRow(worksheet.getLastRowNum() + 1);

            // Creating two cells in the row at 0 and 1 position.
            HSSFCell cellZero = rowOne.createCell(0);
            HSSFCell cellOne = rowOne.createCell(1);

            // extracting key and value from the map and set
            String key;
            key = s;
            String value = propMap.get(key);

            // setting the extracted keys and values in the cells
            cellZero.setCellValue(new HSSFRichTextString(key));
            cellOne.setCellValue(new HSSFRichTextString(value));

        }         
 
        try{
 
            FileOutputStream fosExcel;
 
            // Creating a xls File
            File fileExcel = new File(excelPath);               
 
            // Setting the File to FileOutputStream
            fosExcel = new FileOutputStream(fileExcel);
 
            // Writing the contents of workbook to the xls
            workBook.write(fosExcel);
 
            // Flushing the FileOutputStream
            fosExcel.flush();
            // Closing the FileOutputStream
            fosExcel.close();
 
        }catch(Exception e){
 
            e.printStackTrace();
 
        }
    }
}
