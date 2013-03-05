package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RangeChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		GraphicalView v = ChartFactory.getRangeBarChartView(getActivity(), 
				DataModel.getInstance().getRangeDataset(), 
				DataModel.getInstance().getRangeRenderer(), 
				Type.DEFAULT);
		return v;
	}
}
