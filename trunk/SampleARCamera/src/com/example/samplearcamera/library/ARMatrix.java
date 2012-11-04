package com.example.samplearcamera.library;

import java.security.InvalidParameterException;

import android.opengl.Matrix;

public class ARMatrix {
	/**
	 *  M[ 0]  M[ 4]  M[ 8]  M[12]
	 *  M[ 1]  M[ 5]  M[ 9]  M[13]
	 *  M[ 2]  M[ 6]  M[10]  M[14]
	 *  M[ 3]  M[ 7]  M[11]  M[15]
	 */
	float[] values = new float[16];
	float[] temp = new float[16];
	public ARMatrix() {
		Matrix.setIdentityM(values, 0);
	}
	
	public void reset() {
		Matrix.setIdentityM(values, 0);
	}
	
	public void set(ARMatrix m) {
		System.arraycopy(m.values, 0, values, 0, values.length);
	}
	
	public float[] getValues() {
		return values;
	}
	
	public void setValuesTranspose(float[] v) {
		if (v.length == 16) {
			Matrix.transposeM(values, 0, v, 0);
		} else if (v.length == 9) {
			values[0] = v[0];
			values[1] = v[3];
			values[2] = v[6];
			values[4] = v[1];
			values[5] = v[4];
			values[6] = v[7];
			values[8] = v[2];
			values[9] = v[5];
			values[10] = v[8];
			values[3] = values[7] = values[11] = values[12] = values[13] = values[14] = 0;
			values[15] = 1.0f;
		} else {
			throw new InvalidParameterException();
		}
	}
	
	public void setValues(float[] v) {
		if (v.length == 16) {
			System.arraycopy(v, 0, values, 0, values.length);
		} else if (v.length == 9) {
			values[0] = v[0];
			values[1] = v[1];
			values[2] = v[2];
			values[4] = v[3];
			values[5] = v[4];
			values[6] = v[5];
			values[8] = v[6];
			values[9] = v[7];
			values[10] = v[8];
			values[3] = values[7] = values[11] = values[12] = values[13] = values[14] = 0;
			values[15] = 1.0f;
		} else {
			throw new InvalidParameterException();
		}
	}
	
	public void multipleMatrix(ARMatrix m) {
		System.arraycopy(values, 0, temp, 0, values.length);
		Matrix.multiplyMM(values, 0, temp, 0, m.values, 0);
	}
	
	public void multipleVector(ARVector v) {
		v.multiple(this);
	}
	
	public void invert() {
		System.arraycopy(values, 0, temp, 0, values.length);
		Matrix.invertM(values, 0, temp, 0);
	}
	
	public void rotateX(float degree) {
		Matrix.rotateM(values, 0, degree, 1, 0, 0);
	}
	
	public void rotateY(float degree) {
		Matrix.rotateM(values, 0, degree, 0, 1, 0);
	}
	
	public void rotateZ(float degree) {
		Matrix.rotateM(values, 0, degree, 0, 0, 1);
	}
}
