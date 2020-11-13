import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class LineChart extends ApplicationFrame {

    public LineChart( String applicationTitle , String chartTitle, double[] points, double[] values) {

        super(applicationTitle);

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Y","X",
                createDataset(points, values),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }


    private DefaultCategoryDataset createDataset(double[] points, double[] values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for(int i = 0; i< values.length; ++i) {
                dataset.addValue(values[i], "value", Double.toString(points[i]));
        }
        return dataset;
    }
}