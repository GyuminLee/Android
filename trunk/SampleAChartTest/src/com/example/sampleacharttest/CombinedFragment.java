package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CombinedFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		String[] types = { BarChart.TYPE, BarChart.TYPE, LineChart.TYPE, LineChart.TYPE };
		GraphicalView v = ChartFactory.getCombinedXYChartView(getActivity(), 
				DataModel.getInstance().getDataset(), 
				DataModel.getInstance().getRenderer(), 
				types);
		return v;
	}
}
