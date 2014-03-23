package com.example.samplejexcelapitest;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btnWriteXLS);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					WritableWorkbook workbook = createWorkbook("test.xls");
					WritableSheet sheet = createSheet(workbook, "test", 1);
					writeCell(0, 0, "Cell1", false, sheet);
					writeCell(0, 1, "Cell2", false, sheet);
					workbook.write();
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		});

		btn = (Button) findViewById(R.id.btnReadXLS);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsoluteFile() + "/JExcelApiTest", "test.xls");
				try {
					Workbook workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					if (sheet != null) {
						Cell c1 = sheet.getCell(0, 0);
						Toast.makeText(MainActivity.this, "cell : "+c1.getContents(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(MainActivity.this, "no sheet", Toast.LENGTH_SHORT).show();
					}
				} catch (BiffException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 
	 * @param fileName
	 *            - the name to give the new workbook file
	 * @return - a new WritableWorkbook with the given fileName
	 */
	public WritableWorkbook createWorkbook(String fileName) {
		// exports must use a temp file while writing to avoid memory hogging
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setUseTemporaryFileDuringWrite(true);

		// get the sdcard's directory
		File sdCard = Environment.getExternalStorageDirectory();
		// add on the your app's path
		File dir = new File(sdCard.getAbsolutePath() + "/JExcelApiTest");
		// make them in case they're not there
		dir.mkdirs();
		// create a standard java.io.File object for the Workbook to use
		File wbfile = new File(dir, fileName);

		WritableWorkbook wb = null;

		try {
			// create a new WritableWorkbook using the java.io.File and
			// WorkbookSettings from above
			wb = Workbook.createWorkbook(wbfile, wbSettings);
		} catch (IOException ex) {
			Log.e(TAG, ex.getStackTrace().toString());
			Log.e(TAG, ex.getMessage());
		}

		return wb;
	}

	/**
	 * 
	 * @param wb
	 *            - WritableWorkbook to create new sheet in
	 * @param sheetName
	 *            - name to be given to new sheet
	 * @param sheetIndex
	 *            - position in sheet tabs at bottom of workbook
	 * @return - a new WritableSheet in given WritableWorkbook
	 */
	public WritableSheet createSheet(WritableWorkbook wb, String sheetName,
			int sheetIndex) {
		// create a new WritableSheet and return it
		return wb.createSheet(sheetName, sheetIndex);
	}

	/**
	 * 
	 * @param columnPosition
	 *            - column to place new cell in
	 * @param rowPosition
	 *            - row to place new cell in
	 * @param contents
	 *            - string value to place in cell
	 * @param headerCell
	 *            - whether to give this cell special formatting
	 * @param sheet
	 *            - WritableSheet to place cell in
	 * @throws RowsExceededException
	 *             - thrown if adding cell exceeds .xls row limit
	 * @throws WriteException
	 *             - Idunno, might be thrown
	 */
	public void writeCell(int columnPosition, int rowPosition, String contents,
			boolean headerCell, WritableSheet sheet)
			throws RowsExceededException, WriteException {
		// create a new cell with contents at position
		Label newCell = new Label(columnPosition, rowPosition, contents);

		if (headerCell) {
			// give header cells size 10 Arial bolded
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			// center align the cells' contents
			headerFormat.setAlignment(Alignment.CENTRE);
			newCell.setCellFormat(headerFormat);
		}

		sheet.addCell(newCell);
	}

}
