package tsoc2013.ganttchart.test;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;

import tsoc2013.ganttchart.model.SimpleGanttModel;

public class MyViewModel {
	
	private GanttModel model;
	
	@AfterCompose
	public void init() {
		
		model = new SimpleGanttModel();
		
		// Project 1
		model.addValue("Project 1", new GanttTask("Requirement Analysis", date(2008,4,1), date(2008,4,5), 0.0));
		model.addValue("Project 1", new GanttTask("Design Analysis", date(2008,4,5), date(2008,4,10), 0.0));
		model.addValue("Project 1", new GanttTask("Implement", date(2008,4,15), date(2008,4,20), 0.0));
		model.addValue("Project 1", new GanttTask("Test", date(2008,4,25), date(2008,4,30), 0.0));
		
		// Project 2
		model.addValue("Project 2", new GanttTask("Task 2-1", date(2008,4,1), date(2008,4,5), 0.0));
		model.addValue("Project 2", new GanttTask("Task 2-2", date(2008,4,5), date(2008,4,10), 0.0));		
		model.addValue("Project 2", new GanttTask("Task 2-3", date(2008,4,10), date(2008,4,15), 0.0));		
		model.addValue("Project 2", new GanttTask("Task 2-4", date(2008,4,15), date(2008,4,20), 0.0));	
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
