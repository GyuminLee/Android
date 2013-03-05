package com.example.sampleacharttest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.DialRenderer.Type;

import android.graphics.Color;
import android.graphics.Paint.Align;

public class DataModel {

	private static DataModel instance;
	public static DataModel getInstance() {
		if (instance == null) {
			instance = new DataModel();
		}
		return instance;
	}

	XYMultipleSeriesDataset multipleSeries = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer multipleRenderer = new XYMultipleSeriesRenderer();

	XYMultipleSeriesDataset bubbleSeries = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer bubbleRenderer = new XYMultipleSeriesRenderer();
	
	CategorySeries dialCategory = new CategorySeries("Weight indic");
	DialRenderer dialRenderer = new DialRenderer();
	
	MultipleCategorySeries doughnutCategory = new MultipleCategorySeries("Project budget");
	DefaultRenderer doughnutRenderer = new DefaultRenderer();
	
	CategorySeries pieCategory = new CategorySeries("Project budget");
	DefaultRenderer pieRenderer = new DefaultRenderer();
	
	XYMultipleSeriesDataset rangeSeries = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer rangeRenderer = new XYMultipleSeriesRenderer();
	
	XYMultipleSeriesDataset timeSeries = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer timeRenderer = new XYMultipleSeriesRenderer();
	
	private DataModel() {
		makeMultipleSeries(multipleRenderer, multipleSeries);
		makeBubbleSeries(bubbleRenderer, bubbleSeries);
		makeDialCategory(dialCategory, dialRenderer);
		makeDoughnutCategory(doughnutCategory, doughnutRenderer);
		makePieCategory(pieCategory, pieRenderer);
		makeRangeSeries(rangeSeries, rangeRenderer);
		makeTimeSeries(timeSeries, timeRenderer);
	}

	public XYMultipleSeriesDataset getDataset() {
		return multipleSeries;
	}
	
	public XYMultipleSeriesRenderer getRenderer() {
		return multipleRenderer;
	}
	
	public XYMultipleSeriesDataset getBubbleDataset() {
		return bubbleSeries;
	}
	
	public XYMultipleSeriesRenderer getBubbleRenderer() {
		return bubbleRenderer;
	}
	
	public CategorySeries getDialCategory() {
		return dialCategory;
	}
	
	public DialRenderer getDialRenderer() {
		return dialRenderer;
	}
	
	public MultipleCategorySeries getDoughnutCategory() {
		return doughnutCategory;
	}
	
	public DefaultRenderer getDoughnutRenderer() {
		return doughnutRenderer;
	}
	
	public CategorySeries getPieCategory() {
		return pieCategory;
	}
	
	public DefaultRenderer getPieRenderer() {
		return pieRenderer;
	}
	
	public XYMultipleSeriesDataset getRangeDataset() {
		return rangeSeries;
	}
	
	public XYMultipleSeriesRenderer getRangeRenderer() {
		return rangeRenderer;
	}

	public XYMultipleSeriesDataset getTimeDataset() {
		return timeSeries;
	}
	
	public XYMultipleSeriesRenderer getTimeRenderer() {
		return timeRenderer;
	}
	
	private void makeMultipleSeries(XYMultipleSeriesRenderer renderer, XYMultipleSeriesDataset dataset) {

		String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };
	    List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
	      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
	    }
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
	        13.9 });
	    values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14, 11 });
	    values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
	    values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
	    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
	        PointStyle.TRIANGLE, PointStyle.SQUARE };

	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setPointSize(5f);
	    renderer.setMargins(new int[] { 20, 30, 15, 20 });
	    
	    for (int i = 0; i < colors.length; i++) {
	        XYSeriesRenderer r = new XYSeriesRenderer();
	        r.setColor(colors[i]);
	        r.setPointStyle(styles[i]);
	        r.setFillPoints(true);
	        renderer.addSeriesRenderer(r);
	    }
	    
	    renderer.setChartTitle("Average temperature");
	    renderer.setXTitle("Month");
	    renderer.setYTitle("Temperature");
	    renderer.setXAxisMin(0.5);
	    renderer.setXAxisMax(12.5);
	    renderer.setYAxisMin(-10);
	    renderer.setYAxisMax(40);
	    renderer.setAxesColor(Color.LTGRAY);
	    renderer.setLabelsColor(Color.LTGRAY);
	    renderer.setXLabels(12);
	    renderer.setYLabels(10);
	    renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
	    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
	    
	    for (int i = 0; i < titles.length; i++) {
	        XYSeries series = new XYSeries(titles[i], 0);
	        double[] xV = x.get(i);
	        double[] yV = values.get(i);
	        int seriesLength = xV.length;
	        for (int k = 0; k < seriesLength; k++) {
	          series.add(xV[k], yV[k]);
	        }
	        dataset.addSeries(series);
	    }
	}
	

	private void makeBubbleSeries(XYMultipleSeriesRenderer renderer, XYMultipleSeriesDataset series) {
	    XYValueSeries newTicketSeries = new XYValueSeries("New Tickets");
	    newTicketSeries.add(1, 2, 14);
	    newTicketSeries.add(2, 2, 12);
	    newTicketSeries.add(3, 2, 18);
	    newTicketSeries.add(4, 2, 5);
	    newTicketSeries.add(5, 2, 1);
	    series.addSeries(newTicketSeries);
	    XYValueSeries fixedTicketSeries = new XYValueSeries("Fixed Tickets");
	    fixedTicketSeries.add(1, 1, 7);
	    fixedTicketSeries.add(2, 1, 4);
	    fixedTicketSeries.add(3, 1, 18);
	    fixedTicketSeries.add(4, 1, 3);
	    fixedTicketSeries.add(5, 1, 1);
	    series.addSeries(fixedTicketSeries);

	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
	    newTicketRenderer.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(newTicketRenderer);
	    XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
	    fixedTicketRenderer.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(fixedTicketRenderer);

	    renderer.setChartTitle("Project work status");
	    renderer.setXTitle("Priority");
	    renderer.setYTitle( "");
	    renderer.setXAxisMin(0.5);
	    renderer.setXAxisMax(5.5);
	    renderer.setYAxisMin( 0);
	    renderer.setYAxisMax(5);
	    renderer.setAxesColor(Color.GRAY);
	    renderer.setLabelsColor(Color.LTGRAY);
	    renderer.setXLabels(7);
	    renderer.setYLabels(0);
	    renderer.setShowGrid(false);
	    renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setZoomButtonsVisible(true);
//	    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
//	    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
	}
	
	private void makeDialCategory(CategorySeries category, DialRenderer renderer) {
	    category.add("Current", 75);
	    category.add("Minimum", 65);
	    category.add("Maximum", 90);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] {20, 30, 15, 0});
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(r);
	    r = new SimpleSeriesRenderer();
	    r.setColor(Color.rgb(0, 150, 0));
	    renderer.addSeriesRenderer(r);
	    r = new SimpleSeriesRenderer();
	    r.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(r);
	    renderer.setLabelsTextSize(10);
	    renderer.setLabelsColor(Color.WHITE);
	    renderer.setShowLabels(true);
	    renderer.setVisualTypes(new DialRenderer.Type[] {Type.ARROW, Type.NEEDLE, Type.NEEDLE});
	    renderer.setMinValue(0);
	    renderer.setMaxValue(150);		
	}
	
	private void makeDoughnutCategory(MultipleCategorySeries series, DefaultRenderer renderer) {
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(new double[] { 12, 14, 11, 10, 19 });
	    values.add(new double[] { 10, 9, 14, 20, 11 });
	    List<String[]> titles = new ArrayList<String[]>();
	    titles.add(new String[] { "P1", "P2", "P3", "P4", "P5" });
	    titles.add(new String[] { "Project1", "Project2", "Project3", "Project4", "Project5" });
	    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };

	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    for (int color : colors) {
	      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      r.setColor(color);
	      renderer.addSeriesRenderer(r);
	    }

	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.rgb(222, 222, 200));
	    renderer.setLabelsColor(Color.GRAY);

	    int k = 0;
	    for (double[] value : values) {
	      series.add(2007 + k + "", titles.get(k), value);
	      k++;
	    }	    
	}
	
	private void makePieCategory(CategorySeries series, DefaultRenderer renderer) {
	    double[] values = new double[] { 12, 14, 11, 10, 19 };
	    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };

	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    for (int color : colors) {
	      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      r.setColor(color);
	      renderer.addSeriesRenderer(r);
	    }

	    renderer.setZoomButtonsVisible(true);
	    renderer.setZoomEnabled(true);
	    renderer.setChartTitleTextSize(20);

	    int k = 0;
	    for (double value : values) {
	      series.add("Project " + ++k, value);
	    }
	}
	
	private void makeRangeSeries(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
	    double[] minValues = new double[] { -24, -19, -10, -1, 7, 12, 15, 14, 9, 1, -11, -16 };
	    double[] maxValues = new double[] { 7, 12, 24, 28, 33, 35, 37, 36, 28, 19, 11, 4 };
	    
	    RangeCategorySeries series = new RangeCategorySeries("Temperature");
	    for (int k = 0; k < minValues.length; k++) {
	      series.add(minValues[k], maxValues[k]);
	    }
	    dataset.addSeries(series.toXYSeries());
	    int[] colors = new int[] { Color.CYAN };

	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    
	    for (int i = 0; i < colors.length; i++) {
	      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      r.setColor(colors[i]);
	      renderer.addSeriesRenderer(r);
	    }
	    
	    renderer.setChartTitle("Monthly temperature range");
	    renderer.setXTitle("Month");
	    renderer.setYTitle("Celsius degrees");
	    renderer.setXAxisMin(0.5);
	    renderer.setXAxisMax(12.5);
	    renderer.setYAxisMin(-30);
	    renderer.setYAxisMax(45);
	    renderer.setAxesColor(Color.GRAY);
	    renderer.setLabelsColor(Color.LTGRAY);
	    
	    renderer.setBarSpacing(0.5);
	    renderer.setXLabels(0);
	    renderer.setYLabels(10);
	    renderer.addXTextLabel(1, "Jan");
	    renderer.addXTextLabel(3, "Mar");
	    renderer.addXTextLabel(5, "May");
	    renderer.addXTextLabel(7, "Jul");
	    renderer.addXTextLabel(10, "Oct");
	    renderer.addXTextLabel(12, "Dec");
	    renderer.addYTextLabel(-25, "Very cold");
	    renderer.addYTextLabel(-10, "Cold");
	    renderer.addYTextLabel(5, "OK");
	    renderer.addYTextLabel(20, "Nice");
	    renderer.setMargins(new int[] {30, 70, 10, 0});
	    renderer.setYLabelsAlign(Align.RIGHT);
	    
	    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
	    r.setDisplayChartValues(true);
	    r.setChartValuesTextSize(12);
	    r.setChartValuesSpacing(3);
	    r.setGradientEnabled(true);
	    r.setGradientStart(-20, Color.BLUE);
	    r.setGradientStop(20, Color.GREEN);
	}
	
	private void makeTimeSeries(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
	    String[] titles = new String[] { "Sales growth January 1995 to December 2000" };
	    List<Date[]> dates = new ArrayList<Date[]>();
	    List<double[]> values = new ArrayList<double[]>();
	    Date[] dateValues = new Date[] { new Date(95, 0, 1), new Date(95, 3, 1), new Date(95, 6, 1),
	        new Date(95, 9, 1), new Date(96, 0, 1), new Date(96, 3, 1), new Date(96, 6, 1),
	        new Date(96, 9, 1), new Date(97, 0, 1), new Date(97, 3, 1), new Date(97, 6, 1),
	        new Date(97, 9, 1), new Date(98, 0, 1), new Date(98, 3, 1), new Date(98, 6, 1),
	        new Date(98, 9, 1), new Date(99, 0, 1), new Date(99, 3, 1), new Date(99, 6, 1),
	        new Date(99, 9, 1), new Date(100, 0, 1), new Date(100, 3, 1), new Date(100, 6, 1),
	        new Date(100, 9, 1), new Date(100, 11, 1) };
	    dates.add(dateValues);

	    values.add(new double[] { 4.9, 5.3, 3.2, 4.5, 6.5, 4.7, 5.8, 4.3, 4, 2.3, -0.5, -2.9, 3.2, 5.5,
	        4.6, 9.4, 4.3, 1.2, 0, 0.4, 4.5, 3.4, 4.5, 4.3, 4 });
	    int[] colors = new int[] { Color.BLUE };
	    PointStyle[] styles = new PointStyle[] { PointStyle.POINT };

	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setPointSize(5f);
	    renderer.setMargins(new int[] { 20, 30, 15, 20 });
	    
	    for (int i = 0; i < colors.length; i++) {
	        XYSeriesRenderer r = new XYSeriesRenderer();
	        r.setColor(colors[i]);
	        r.setPointStyle(styles[i]);
	        r.setFillPoints(true);
	        renderer.addSeriesRenderer(r);
	    }
	    
	    renderer.setChartTitle("Sales growth");
	    renderer.setXTitle("Date");
	    renderer.setYTitle("%");
	    renderer.setXAxisMin(dateValues[0].getTime());
	    renderer.setXAxisMax(dateValues[dateValues.length - 1].getTime());
	    renderer.setYAxisMin(-4);
	    renderer.setYAxisMax( 11);
	    renderer.setAxesColor(Color.GRAY);
	    renderer.setLabelsColor(Color.LTGRAY);
	    
	    renderer.setYLabels(10);

	    for (int i = 0; i < titles.length; i++) {
	        TimeSeries series = new TimeSeries(titles[i]);
	        Date[] xV = dates.get(i);
	        double[] yV = values.get(i);
	        int seriesLength = xV.length;
	        for (int k = 0; k < seriesLength; k++) {
	          series.add(xV[k], yV[k]);
	        }
	        dataset.addSeries(series);
	    }
	}
}
