import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);

        System.out.println("Input a function: ");
        String function = in.nextLine();

        String sourceCode = "import static java.lang.Math.*;\n" +
                "public class Function implements FunctionInterface{\n" +
                "    @Override\n" +
                "    public double calculate(double x) {\n" +
                "        return " + function + ";\n" +
                "    }\n" +
                "}";

        FunctionInterface instance = ReflectCompiler.newInstance(sourceCode, "Function");

        System.out.println("Starting point:");
        double startOfRange = in.nextDouble();
        System.out.println("End point:");
        double endOfRange = in.nextDouble();
        in.close();

        int n = 10000;
        double[] points = new double[n+1];
        double h = (endOfRange - startOfRange)/n;
        double[] values = new double[n+1];

        for (int i = 0; i < points.length; i++)
            points[i] = startOfRange + i*h;

        for (int i = 0; i < values.length; i++)
            values[i] = instance.calculate(points[i]);


        XYSeries series = new XYSeries(function);

        for(int i = 0; i < values.length; ++i)
            series.add(points[i], values[i]);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Plot",
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        try {
            ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
        LineChart chart1= new LineChart("Reflection", "Plot", points, values);
        chart1.pack();
        RefineryUtilities.centerFrameOnScreen( chart1 );
        chart1.setVisible(true);

    }
}
