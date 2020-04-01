//package math;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
//public class Chart extends ApplicationFrame {
//
//    final XYSeries series = new XYSeries("Data");
//
//    public Chart(final String title) {
//        super(title);
//    }
//
//    public void insert(double x, double y) {
//        series.add(x, y);
//    }
//
//    public void plot() {
//        final XYSeriesCollection data = new XYSeriesCollection(series);
//        final JFreeChart chart = ChartFactory.createXYLineChart(
//                "XY Series Demo",
//                "X",
//                "Y",
//                data,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//        setContentPane(chartPanel);
//
//        this.pack();
//        RefineryUtilities.centerFrameOnScreen(this);
//        this.setVisible(true);
//    }
//
//}