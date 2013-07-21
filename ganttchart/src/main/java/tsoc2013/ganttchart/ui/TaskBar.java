/* TaskBar.java

{{IS_NOTE
	Purpose:
		task bar component.
	Description:
		use in gantt chart component 
	History:
		2013/7/21 , Created by roliroli
}}IS_NOTE

{{IS_RIGHT
}}IS_RIGHT
*/
package tsoc2013.ganttchart.ui;

import org.zkoss.graphics.Rect;
import org.zkoss.zul.GanttModel.GanttTask;

public class TaskBar extends Rect {
	
	private static final long serialVersionUID = 5727854308619312027L;
	
	private GanttTask _task;
	
	public TaskBar(GanttTask task) {
		this._task = task;
	}

	public GanttTask getTask() {
		return _task;
	}

	public void setTask(GanttTask task) {
		this._task = task;
	}
	
}
