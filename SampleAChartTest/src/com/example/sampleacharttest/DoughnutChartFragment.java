package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DoughnutChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		GraphicalView v = ChartFactory.getDoughnutChartView(getActivity(), 
				DataModel.getInstance().getDoughnutCategory(), 
				DataModel.getInstance().getDoughnutRenderer());
		return v;
	}
}
