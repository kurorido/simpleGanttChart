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
	
	private static String[] monthArray = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private int _timelineHeight = 35;
	private int _unitSize = 35;
	
	public GanttChartStage(GanttChart chart) {
		
		setWidth(1100);
		setHeight(550);
		
		this._chart = chart;
		
		_taskBarList = new LinkedList<TaskBar>();
		_taskMap = new HashMap<GanttTask, TaskBar>();
		_styleMap = new HashMap<Comparable<?>, SeriesStyle>();
		
		// create a default color list
		colorList = new String[8];
		colorList[0] = "#eb6841";
		colorList[1] = "#edc951";
		colorList[2] = "#cc2a36";		
		colorList[3] = "#00a0b0";
		colorList[4] = "#4f372d";		
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
		drawTimelineSimple();
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
			
			// text on bar
			Text text = new Text();
			bar.setText(text);
			_barLayer.appendChild(text);
		}
		Text text = bar.getText();
		
		// apply the style to task bar
		Calendar cal = getCalendar();
		long x = dateDiff(task.getStart(), cal.getTime());
		
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		Date end = cal.getTime();
		
		bar.setX(x * _unitSize); // dynamic change with view start date
		bar.setHeight(_unitSize);
		
		// visibility is depending on view start date & end date
		if(style.isVisible()) {
			if(isBetween(task.getStart(), start, end) || isBetween(task.getEnd(), start, end)) {
				bar.setVisible(true);
			} else {
				bar.setVisible(false);
			}
		}
		
		bar.setFill(style.getColor());
		bar.setStroke("black");
		
		// apply text style
		text.setX((x + 1) * _unitSize);
		text.setFontSize("18");
		text.setTextContent(task.getDescription());
		text.setFill("black");		
		
		// calculate width for task bar
		long width = dateDiff(task.getEnd(), task.getStart()) + 1;
		bar.setWidth(width * _unitSize);
		
		// go through task children
		if(task.getSubtasks() != null && task.getSubtasks().length > 0) {
			createTaskBar(series, task, style);
		}
	}
	
	private boolean isBetween(Date date, Date start, Date end) {
		return date.after(start) && date.before(end);
	}
	
	/**
	 * create a clean calendar
	 * @return
	 */
	private Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(_chart.getStartDate());
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
		return cal;
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
			if(bar.isVisible()) { // only draw visible bar
				bar.setY(barCounter++ * _unitSize + _timelineHeight);
				bar.getText().setY(bar.getY());
				if(bar.getParent() != this._barLayer) {// don't append again
					this._barLayer.appendChild(bar);
					this._barLayer.appendChild(bar.getText());
				}
			}
		}
	}
	
	public void drawTimelineSimple() {
		// draw current month only
		Calendar cal = getCalendar();
		int month = cal.get(Calendar.MONTH); // remember the current month

		Text monthYear = new Text();
		monthYear.setTextContent(cal.get(Calendar.YEAR) + " " + monthArray[cal.get(Calendar.MONTH)]);
		monthYear.setX(0);
		monthYear.setFontSize("40");
		monthYear.setFill("black");
		_timelineLayer.appendChild(monthYear);
		
		for(int i = 0; i < 31; i++) {
			if(cal.get(Calendar.MONTH) != month) {
				break;
			}
			// draw day text
			Text day = new Text();
			day.setX(i * _unitSize);
			day.setY(35);
			day.setTextContent(String.valueOf(cal.get(Calendar.DATE)));
			day.setFontSize("24");
			day.setFill("black");
			_timelineLayer.appendChild(day);
			
			// add day
			cal.add(Calendar.DATE, 1);
		}
	}
	
	/**
	 * 
	 */
	public void drawTimeline() {
		
		// draw 7 * 6 days (6 weeks) 
		Calendar cal = Calendar.getInstance();
		cal.setTime(_chart.getStartDate());
		
		int month = cal.get(Calendar.MONTH); // remember the current month
		
		// boundary issue
		// don't draw month text on the month boundary (5 days)
		cal.add(Calendar.DATE, 5);
		if(cal.get(Calendar.MONTH) == month) {
			Text monthYear = new Text();
			monthYear.setTextContent(cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH));
			monthYear.setX(0);
			monthYear.setFontSize("40");
			monthYear.setFill("black");
			_timelineLayer.appendChild(monthYear);
		}
		cal.add(Calendar.DATE, -5);
		
		for(int i = 0; i < 7 * 6; i++) {
			
			// draw next month year text
			if(cal.get(Calendar.MONTH) != month) {
				Text anotherMonthYear = new Text();
				anotherMonthYear.setTextContent(cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH));
				anotherMonthYear.setX(i * _unitSize);
				anotherMonthYear.setFontSize("40");
				anotherMonthYear.setFill("black");		
				_timelineLayer.appendChild(anotherMonthYear);
				month = cal.get(Calendar.MONTH);
			}
			
			// draw day text
			Text day = new Text();
			day.setX(i * _unitSize);
			day.setY(35);
			day.setTextContent(String.valueOf(cal.get(Calendar.DATE)));
			day.setFontSize("24");
			day.setFill("black");
			_timelineLayer.appendChild(day);
			
			// add day
			cal.add(Calendar.DATE, 1);
		}
		
	}
	
	public SeriesStyle getSeriesStyle(Comparable<?> series) {
		return _styleMap.get(series);
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
