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
        dataset.setValue("���ÿռ�", Used);
        dataset.setValue("���ÿռ�", Available);
        return dataset;
    }
    public static ChartPanel GetChartPanel(double Used, double Available, String ChartName){
        ChartPanel temp = null;
        DefaultPieDataset data = setDataSet(Used, Available);
        JFreeChart chart = ChartFactory.createPieChart3D(ChartName,data,true,false,false);
        //���ðٷֱ�
        PiePlot pieplot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");//���һ��DecimalFormat������Ҫ������С������
        NumberFormat nf = NumberFormat.getNumberInstance();//���һ��NumberFormat����
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//���StandardPieSectionLabelGenerator����
        pieplot.setLabelGenerator(sp1);//���ñ�ͼ��ʾ�ٷֱ�

        //û�����ݵ�ʱ����ʾ������
        pieplot.setNoDataMessage("��������ʾ");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);//���ò���ʾ��ֵ
        pieplot.setIgnoreZeroValues(true);//���ò���ʾ��ֵ
        temp = new ChartPanel (chart,true);
        chart.getTitle().setFont(new Font("����",Font.BOLD,20));//���ñ�������
        PiePlot piePlot= (PiePlot) chart.getPlot();//��ȡͼ���������
        piePlot.setLabelFont(new Font("����",Font.BOLD,10));//�������
        pieplot.setSectionPaint("���ÿռ�", Color.decode("#EBB205"));
        pieplot.setSectionPaint("���ÿռ�", Color.decode("#426CB6"));
        chart.getLegend().setItemFont(new Font("����",Font.BOLD,10));
        return temp;
    }

}
