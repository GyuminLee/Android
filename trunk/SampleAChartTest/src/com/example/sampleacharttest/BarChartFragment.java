package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BarChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		DataModel.getInstance().getRenderer().setOrientation(Orientation.VERTICAL);
		GraphicalView v = ChartFactory.getBarChartView(getActivity(), 
				DataModel.getInstance().getDataset(), 
				DataModel.getInstance().getRenderer(), 
				Type.DEFAULT);
		return v;
	}
}
