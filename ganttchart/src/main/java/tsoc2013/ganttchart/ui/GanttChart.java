/* GanttChart.java

{{IS_NOTE
	Purpose:
		gantt chart component
	Description:
		
	History:
		2013/7/21 , Created by roliroli
}}IS_NOTE

{{IS_RIGHT
}}IS_RIGHT
*/

package tsoc2013.ganttchart.ui;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ChartDataListener;

import tsoc2013.ganttchart.model.SimpleGanttModel;

public class GanttChart extends Div {
	
	private static final long serialVersionUID = 2961915868288891144L;
	
	private GanttChartStage _stage;
	private ChartDataListener _dataListener;
	private SimpleGanttModel _model;
	private Date _startDate;
	
	public GanttChart() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        _startDate = cal.getTime();
	}
	
	public void nextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(_startDate);
		cal.add(Calendar.MONTH, 1);
		this.setStartDate(cal.getTime());
	}
	
	public void previousMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(_startDate);
		cal.add(Calendar.MONTH, -1);
		this.setStartDate(cal.getTime());		
	}
	
	private void initDataListener() {
		if (_dataListener == null) {
			_dataListener = new GanttChartDataListener();
			_model.addChartDataListener(_dataListener);
		}
	}
	
	public void setModel(SimpleGanttModel model) {
		if (_model != model) {
			if (_model != null) {
				_model.removeChartDataListener(_dataListener);
			}
			_model = model;
			initDataListener();
			
			invalidate();
		}
	}
	
	@Override
	public void invalidate() {
		
		this.getChildren().clear();
		
		_stage = new GanttChartStage(this);	 // create a new stage
		this.appendChild(_stage);
		
		_stage.build(_model); // build & redraw stage
	}	
	
	private class GanttChartDataListener implements ChartDataListener, Serializable {

		private static final long serialVersionUID = -1984471929022716659L;

		public void onChange(ChartDataEvent event) {
			_stage.updateSeries(_model, event.getSeries(), null);
		}
	}

	//super//
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer)
	throws java.io.IOException {
		super.renderProperties(renderer);
	}
	
	public void service(AuRequest request, boolean everError) {
		final String cmd = request.getCommand();
		final Map data = request.getData();
		
		if (cmd.equals("onFoo")) {
			final String foo = (String)data.get("foo");
			System.out.println("do onFoo, data:" + foo);
			Events.postEvent(Event.getEvent(request));
		} else
			super.service(request, everError);
	}

	/**
	 * The default zclass is "z-ganttchart"
	 */
	public String getZclass() {
		return (this._zclass != null ? this._zclass : "z-ganttchart");
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		if(startDate != _startDate) {
			this._startDate = startDate;
			invalidate();
		}
	}
	
	public SeriesStyle getSeriesStyle(Comparable<?> series) {
		return _stage.getSeriesStyle(series);
	}

}

