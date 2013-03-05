package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BubbleChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		GraphicalView v = ChartFactory.getBubbleChartView(getActivity(), 
				DataModel.getInstance().getBubbleDataset(), 
				DataModel.getInstance().getBubbleRenderer());
		return v;
	}
}
