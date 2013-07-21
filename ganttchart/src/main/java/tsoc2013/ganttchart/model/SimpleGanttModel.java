/* SimpleGanttModel.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/7/21 , Created by roliroli
}}IS_NOTE

{{IS_RIGHT
}}IS_RIGHT
*/

package tsoc2013.ganttchart.model;

import org.zkoss.zul.GanttModel;

public class SimpleGanttModel extends GanttModel {

	private static final long serialVersionUID = -9201654780069140972L;

	@Override
	public void addValue(Comparable<?> series, GanttTask task) {
		// TODO Auto-generated method stub
		super.addValue(series, task);
	}

	@Override
	public void removeValue(Comparable<?> series, GanttTask task) {
		// TODO Auto-generated method stub
		super.removeValue(series, task);
	}

	@Override
	public Comparable<?>[] getAllSeries() {
		// TODO Auto-generated method stub
		return super.getAllSeries();
	}

	@Override
	public GanttTask[] getTasks(Comparable series) {
		// TODO Auto-generated method stub
		return super.getTasks(series);
	}
	
}
