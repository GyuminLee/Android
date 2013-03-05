package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DialChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		GraphicalView v = ChartFactory.getDialChartView(getActivity(), 
				DataModel.getInstance().getDialCategory(), 
				DataModel.getInstance().getDialRenderer());
		return v;
	}
}
