package com.example.sampleocr302;

public class TesseractData {

	public String fileName;
	public int resId;
	public TesseractData(String fileName, int resId) {
		this.fileName = fileName;
		this.resId = resId;
	}
	
	public static final TesseractData[] fileData = { new TesseractData("eng.cube.bigrams",R.raw.eng_cube_bigrams),
											new TesseractData("eng.cube.fold",R.raw.eng_cube_fold),
											new TesseractData("eng.cube.lm",R.raw.eng_cube_lm),
											new TesseractData("eng.cube.nn",R.raw.eng_cube_nn),
											new TesseractData("eng.cube.params",R.raw.eng_cube_params),
											new TesseractData("eng.cube.size",R.raw.eng_cube_size),
											new TesseractData("eng.cube.word-freq",R.raw.eng_cube_word_freq),
											new TesseractData("eng.tesseract_cube.nn",R.raw.eng_tesseract_cube_nn),
											new TesseractData("eng.traineddata",R.raw.eng_traineddata),
											new TesseractData("kor.traineddata",R.raw.kor_traineddata)
											};
}
