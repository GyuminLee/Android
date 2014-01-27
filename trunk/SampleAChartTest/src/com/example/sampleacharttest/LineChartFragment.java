package com.example.sampleacharttest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class LineChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		XYMultipleSeriesRenderer renderer = DataModel.getInstance()
				.getRenderer();
		renderer.setClickEnabled(true);
		final GraphicalView view = ChartFactory.getLineChartView(getActivity(),
				DataModel.getInstance().getDataset(), renderer);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SeriesSelection selection = view.getCurrentSeriesAndPoint();
				if (selection != null) {
					Toast.makeText(
							getActivity(),
							"series index : " + selection.getSeriesIndex()
									+ ", point index : "
									+ selection.getPointIndex() + ", x : "
									+ selection.getXValue() + ", y : "
									+ selection.getValue(), Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		return view;
	}
}
