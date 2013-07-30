package tsoc2013.ganttchart.test;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;

import tsoc2013.ganttchart.model.SimpleGanttModel;
import tsoc2013.ganttchart.ui.GanttChart;

public class MyViewModel {
	
	private GanttModel model;
	
	@AfterCompose
	public void init() {
		
		
		model = new SimpleGanttModel();
		
		int step1 = 5;
		int step2 = 7;
		
		// Project 1
		Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
		Calendar cal2 = Calendar.getInstance();
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        cal2.add(Calendar.DATE, 5);
		
		model.addValue("Project 1", new GanttTask("Requirement Analysis", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step1);
        cal2.add(Calendar.DATE, step1);
		
		model.addValue("Project 1", new GanttTask("Design Analysis", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step1);
        cal2.add(Calendar.DATE, step1);
        
		model.addValue("Project 1", new GanttTask("Implement", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step1);
        cal2.add(Calendar.DATE, step1);
        
		model.addValue("Project 1", new GanttTask("Test", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step1);
        cal2.add(Calendar.DATE, step1);
        
		// Project 2
        cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        cal2 = Calendar.getInstance();
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        cal2.add(Calendar.DATE, 5);
        
		model.addValue("Project 2", new GanttTask("Task 2-1", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
        
		model.addValue("Project 2", new GanttTask("Task 2-2", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
        
		model.addValue("Project 2", new GanttTask("Task 2-3", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
        
		model.addValue("Project 2", new GanttTask("Task 2-4", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
	}
	
	@Command("nextMonth")
	public void nextMonth(@BindingParam("chart") GanttChart chart) {
		chart.nextMonth();
	}
	
	@Command("previousMonth")
	public void previousMonth(@BindingParam("chart") GanttChart chart) {
		chart.previousMonth();
	}
	
    private Date date(int year, int month, int day) {
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month-1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Date result = calendar.getTime();
        return result;
    }

	public GanttModel getModel() {
		return model;
	}

	public void setModel(GanttModel model) {
		this.model = model;
	}

}
