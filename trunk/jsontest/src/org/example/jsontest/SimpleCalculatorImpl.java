package org.example.jsontest;

import org.example.jsontest.shared.Calculator;

public class SimpleCalculatorImpl implements Calculator {

	@Override
	public double add(double x, double y) {
		// TODO Auto-generated method stub
		return x+y;
	}

	@Override
	public double multiply(double x, double y) {
		// TODO Auto-generated method stub
		return x*y;
	}

}
