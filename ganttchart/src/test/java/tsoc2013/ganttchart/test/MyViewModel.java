package tsoc2013.ganttchart.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.TreeModel;

import tsoc2013.ganttchart.model.SimpleGanttModel;
import tsoc2013.ganttchart.ui.GanttChart;
import tsoc2013.ganttchart.ui.SeriesStyle;

public class MyViewModel {
	
	private GanttModel model;
	private boolean listSeries;
	private boolean listTasks;
	private Comparable<?> selectedSeries;
	private List<GanttTask> taskList;
	
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
        
        // Project 3
        cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        cal2 = Calendar.getInstance();
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        cal2.add(Calendar.DATE, 5);
        
		model.addValue("Project 3", new GanttTask("Task 3-1", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
        
		model.addValue("Project 3", new GanttTask("Task 3-2", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
        
		model.addValue("Project 3", new GanttTask("Task 3-3", cal.getTime(), cal2.getTime(), 0.0));
		
        cal.add(Calendar.DATE, step2);
        cal2.add(Calendar.DATE, step2);
        
		model.addValue("Project 3", new GanttTask("Task 3-4", cal.getTime(), cal2.getTime(), 0.0));
		
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
	
	@Command("showSeriesList")
	@NotifyChange("listSeries")
	public void showSeriesList() {
		setListSeries(true);
	}
	
	@Command("showTaskList")
	@NotifyChange({"listTasks", "listSeries", "taskList"})
	public void showTaskList(@BindingParam("listbox") Listbox listbox) {
		Listitem item = listbox.getSelectedItem();
		Listcell cell = ((Listcell) item.getFirstChild());
		setSelectedSeries(cell.getLabel());
		taskList = Arrays.asList(model.getTasks(selectedSeries));
		setListSeries(false);
		setListTasks(true);
		listbox.selectItem(null);
	}
	
	@NotifyChange({"listTasks", "model"})
	@Command("closeTaskList")
	public void closeTaskList(@BindingParam("chart") GanttChart chart) {
		setListTasks(false);
		chart.invalidate(); // refresh ui
	}

	public GanttModel getModel() {
		return model;
	}

	public void setModel(GanttModel model) {
		this.model = model;
	}

	public boolean isListSeries() {
		return listSeries;
	}

	public void setListSeries(boolean listSeries) {
		this.listSeries = listSeries;
	}

	public boolean isListTasks() {
		return listTasks;
	}

	public void setListTasks(boolean listTasks) {
		this.listTasks = listTasks;
	}

	public Comparable<?> getSelectedSeries() {
		return selectedSeries;
	}

	public void setSelectedSeries(Comparable<?> selectedSeries) {
		this.selectedSeries = selectedSeries;
	}

	public List<GanttTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<GanttTask> taskList) {
		this.taskList = taskList;
	}
	
	@NotifyChange({"taskList"})
	@Command("delete")
	public void deleteTask(@BindingParam("listbox") Listbox listbox) {
		GanttTask task = listbox.getSelectedItem().getValue();
		model.removeValue(selectedSeries, task);
		taskList = Arrays.asList(model.getTasks(selectedSeries));
		System.out.println("remove " + task.getDescription());
	}

}
