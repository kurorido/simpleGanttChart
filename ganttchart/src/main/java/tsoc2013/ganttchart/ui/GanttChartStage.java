/* GanttChartStage.java

{{IS_NOTE
	Purpose:
		main stage of gantt chart component
	Description:
		this stage include a time line layer & task bar layer
		a list maintain all task bar
		a map maintain the relation between task bar and task
		a map maintain the relation between series and style
	History:
		2013/7/21 , Created by roliroli
}}IS_NOTE

{{IS_RIGHT
}}IS_RIGHT
*/
package tsoc2013.ganttchart.ui;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.graphics.Layer;
import org.zkoss.graphics.Stage;
import org.zkoss.graphics.Text;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;


public class GanttChartStage extends Stage {
	
	private static final long serialVersionUID = -753339525531958261L;

	private GanttChart _chart;
	private String[] colorList;
	private Map<GanttTask, TaskBar> _taskMap;
	private Map<Comparable<?>, SeriesStyle> _styleMap;
	private List<TaskBar> _taskBarList;
	
	private Layer _timelineLayer;
	private Layer _barLayer;
	
	private int _unitSize = 20;
	
	public GanttChartStage(GanttChart chart) {
		
		this._chart = chart;
		
		_taskBarList = new LinkedList<TaskBar>();
		_taskMap = new HashMap<GanttTask, TaskBar>();
		_styleMap = new HashMap<Comparable<?>, SeriesStyle>();
		
		// create a default color list
		colorList = new String[8];
		colorList[0] = "#00a0b0";
		colorList[1] = "#4f372d";
		colorList[2] = "#cc2a36";
		colorList[3] = "#eb6841";
		colorList[4] = "#edc951";
		colorList[5] = "#67204e";
		colorList[6] = "#599653";
		colorList[7] = "#9bcd9b";
	}
	
	private void init() {
		
		_timelineLayer = new Layer();
		this.appendChild(_timelineLayer);
		
		_barLayer = new Layer();
		_barLayer.setY(40);
		this.appendChild(_barLayer);
	}

	/**
	 * build the chart with model
	 * @param model
	 * @param chart
	 */
	public void build(GanttModel model) {
		
		init();
		
		Comparable<?>[] seriesList = model.getAllSeries();
		
		for(int i = 0; i < seriesList.length; i++) {
			
			// create series style
			SeriesStyle style = new SeriesStyle();
			style.setColor(colorList[i]);
			style.setVisible(true);
			
			_styleMap.put(seriesList[i], style);
			
			for(GanttTask task : model.getTasks(seriesList[i])) {
				createTaskBar(seriesList[i], task, style);
			}
		}
		
		// update UI
		drawTimeline();
		redrawTaskBars();
	}
	
	/**
	 * Update a single series data
	 * @param model
	 * @param series
	 * @param style
	 */
	public void updateSeries(GanttModel model, Comparable<?> series, SeriesStyle style) {
		
		if(style == null) { // update without style, get from map for origin setting
			style = _styleMap.get(series);
		}
		
		for(GanttTask task : model.getTasks(series)) {
			createTaskBar(series, task, style);
		}
		
		redrawTaskBars(); // update UI
	}

	/**
	 * create a task bar by task if not exist
	 * apply the task bar with series style
	 * calculate the width of task bar
	 * @param series
	 * @param task
	 * @param style
	 */
	private void createTaskBar(Comparable<?> series, GanttTask task, SeriesStyle style) {
		
		TaskBar bar = _taskMap.get(task); // retrieve task bar from map
		if(_taskMap.get(task) == null) { // create task bar if not exist
			bar = new TaskBar(task);
			_taskMap.put(task, bar); // mapping
			_taskBarList.add(bar); // maintain bar list
			_barLayer.appendChild(bar); // append to layer
		}
		
		// apply the style to task bar
		bar.setX(0); // dynamic change with view start date
		bar.setHeight(_unitSize);
		bar.setVisible(style.isVisible());
		bar.setFill(style.getColor());
		bar.setStroke("black");
		
		// calculate width for task bar
		long width = dateDiff(task.getEnd(), task.getStart()) + 1;
		bar.setWidth(width * _unitSize);
		
		// go through task children
		if(task.getSubtasks() != null && task.getSubtasks().length > 0) {
			createTaskBar(series, task, style);
		}
	}
	
	/**
	 * show the task bar on UI
	 * sort the task bar then append it to the chart with y-sequential order
	 * @param chart
	 */
	public void redrawTaskBars() {
		Collections.sort(_taskBarList, new DateComparator());
		int barCounter = 0;
		for(TaskBar bar : _taskBarList) {
			bar.setY(barCounter++ * _unitSize);
			if(bar.getParent() != this._barLayer) // don't append again
				this._barLayer.appendChild(bar);
		}
	}
	
	public void drawTimeline() {
		
		// draw 14 days
		Calendar cal = Calendar.getInstance();
		cal.setTime(_chart.getStartDate());
		
		Text monthYear = new Text();
		monthYear.setTextContent(cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH));
		monthYear.setX(0); monthYear.setY(0);
		monthYear.setFontSize("12");
		monthYear.setFill("black");
		_timelineLayer.appendChild(monthYear);
		
		for(int i = 0; i < 14; i++) {
			Text day = new Text();
			day.setX(i * _unitSize);
			day.setY(10);
			day.setTextContent(String.valueOf(cal.get(Calendar.DATE)));
			day.setFontSize("12");
			day.setFill("black");
			_timelineLayer.appendChild(day);
			
			cal.add(Calendar.DATE, 1);
		}
		
	}
	
	/**
	 * Date utility for calculate the different of date 
	 * @param date1
	 * @param date2
	 * @return
	 */
	private long dateDiff(Date date1, Date date2) {
		return (date1.getTime() - date2.getTime()) / (24*60*60*1000);
	}
	
	/**
	 * Date utility for sorting the task bars
	 *
	 */
	private class DateComparator implements Comparator<TaskBar> {
		public int compare(TaskBar bar1, TaskBar bar2) {
			Date date1 = bar1.getTask().getStart();
			Date date2 = bar2.getTask().getStart();
			return date1.compareTo(date2);
		}
	}

}
