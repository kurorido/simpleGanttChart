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
import org.zkoss.graphics.Text;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.GanttModel.GanttTask;

public class TaskBar extends Rect {
	
	private static final long serialVersionUID = 5727854308619312027L;
	
	private GanttTask _task;
	private Text text;
	
	public TaskBar() {
		// Post onClick Event
		this.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
		    public void onEvent(MouseEvent event) {
		    	
		    }
		});
	}
	
	public TaskBar(GanttTask task) {
		this._task = task;
	}

	public GanttTask getTask() {
		return _task;
	}

	public void setTask(GanttTask task) {
		this._task = task;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
}
