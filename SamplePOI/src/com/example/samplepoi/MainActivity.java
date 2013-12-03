package com.example.samplepoi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 View writeButton = findViewById(R.id.write);
	        writeButton.setOnClickListener(this);
	        View readButton = findViewById(R.id.read);
	        readButton.setOnClickListener(this);
	        View writeExcelButton = findViewById(R.id.writeExcel);
	        writeExcelButton.setOnClickListener(this);
	        View readExcelButton = findViewById(R.id.readExcel);
	        readExcelButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.write:
            saveFile(this,"myFile.txt");
            break;
        case R.id.read:
            readFile(this,"myFile.txt");
            break;
        case R.id.writeExcel:
            saveExcelFile(this,"myExcel.xls");
            break;
        case R.id.readExcel:
            readExcelFile(this,"myExcel.xls");
            break;   
        }
    }

    private static boolean saveFile(Context context, String fileName) { 

        // check if available and not read only 
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) { 
            Log.w("FileUtils", "Storage not available or read only"); 
            return false; 
        } 

        // Create a path where we will place our List of objects on external storage 
        File file = new File(context.getExternalFilesDir(null), fileName); 
        PrintStream p = null; // declare a print stream object
        boolean success = false; 

        try { 
            OutputStream os = new FileOutputStream(file); 
            // Connect print stream to the output stream
            p = new PrintStream(os);
            p.println("This is a TEST");
            Log.w("FileUtils", "Writing file" + file); 
            success = true; 
        } catch (IOException e) { 
            Log.w("FileUtils", "Error writing " + file, e); 
        } catch (Exception e) { 
            Log.w("FileUtils", "Failed to save file", e); 
        } finally { 
            try { 
                if (null != p) 
                    p.close(); 
            } catch (Exception ex) { 
            } 
        } 

        return success; 
    } 

    private static void readFile(Context context, String filename) { 

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) 
        { 
            Log.w("FileUtils", "Storage not available or read only"); 
            return; 
        } 

        FileInputStream fis = null;

        try 
        { 
            File file = new File(context.getExternalFilesDir(null), filename); 
            fis = new FileInputStream(file); 
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                Log.w("FileUtils", "File data: " + strLine);
                Toast.makeText(context, "File Data: " + strLine , Toast.LENGTH_SHORT).show();
            }
            in.close();
        } 
        catch (Exception ex) { 
            Log.e("FileUtils", "failed to load file", ex); 
        } 
        finally { 
            try {if (null != fis) fis.close();} catch (IOException ex) {} 
        } 

        return;
    } 

    private static boolean saveExcelFile(Context context, String fileName) { 

        // check if available and not read only 
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) { 
            Log.w("FileUtils", "Storage not available or read only"); 
            return false; 
        } 

        boolean success = false; 
        
        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
       
        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("myOrder");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("Item Number");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Quantity");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("Price");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        // Create a path where we will place our List of objects on external storage 
        File file = new File(context.getExternalFilesDir(null), fileName); 
        FileOutputStream os = null; 

        try { 
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file); 
            success = true; 
        } catch (IOException e) { 
            Log.w("FileUtils", "Error writing " + file, e); 
        } catch (Exception e) { 
            Log.w("FileUtils", "Failed to save file", e); 
        } finally { 
            try { 
                if (null != os) 
                    os.close(); 
            } catch (Exception ex) { 
            } 
        } 

        return success; 
    } 

    private static void readExcelFile(Context context, String filename) { 

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) 
        { 
            Log.w("FileUtils", "Storage not available or read only"); 
            return; 
        } 

        try{
            // Creating Input Stream 
            File file = new File(context.getExternalFilesDir(null), filename); 
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object 
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System 
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook 
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator<Row> rowIter = mySheet.rowIterator();

            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = myRow.cellIterator();
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    switch(myCell.getCellType()) {
                    case HSSFCell.CELL_TYPE_BLANK :
                    case HSSFCell.CELL_TYPE_BOOLEAN :
                    case HSSFCell.CELL_TYPE_FORMULA :
                    case HSSFCell.CELL_TYPE_NUMERIC :
                    case HSSFCell.CELL_TYPE_STRING :
                    case HSSFCell.CELL_TYPE_ERROR :
                    }
                    Log.w("FileUtils", "Cell Value: " +  myCell.toString());
                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){e.printStackTrace(); }

        return;
    } 

    public static boolean isExternalStorageReadOnly() { 
        String extStorageState = Environment.getExternalStorageState(); 
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) { 
            return true; 
        } 
        return false; 
    } 

    public static boolean isExternalStorageAvailable() { 
        String extStorageState = Environment.getExternalStorageState(); 
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) { 
            return true; 
        } 
        return false; 
    } 	
}
