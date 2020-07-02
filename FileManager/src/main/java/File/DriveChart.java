package File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DriveChart {
    public static DefaultPieDataset setDataSet(double Used,double Available){
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("已用空间", Used);
        dataset.setValue("可用空间", Available);
        return dataset;
    }
    public static ChartPanel GetChartPanel(double Used, double Available, String ChartName){
        ChartPanel temp = null;
        DefaultPieDataset data = setDataSet(Used, Available);
        JFreeChart chart = ChartFactory.createPieChart3D(ChartName,data,true,false,false);
        //设置百分比
        PiePlot pieplot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
        pieplot.setLabelGenerator(sp1);//设置饼图显示百分比

        //没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);//设置不显示空值
        pieplot.setIgnoreZeroValues(true);//设置不显示负值
        temp = new ChartPanel (chart,true);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象
        piePlot.setLabelFont(new Font("宋体",Font.BOLD,10));//解决乱码
        pieplot.setSectionPaint("已用空间", Color.decode("#EBB205"));
        pieplot.setSectionPaint("可用空间", Color.decode("#426CB6"));
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));
        return temp;
    }

}
