/* SeriesStyle.java

{{IS_NOTE
	Purpose:
		maintain the style of a series
	Description:
		color, visibility
	History:
		2013/7/21 , Created by roliroli
}}IS_NOTE

{{IS_RIGHT
}}IS_RIGHT
*/
package tsoc2013.ganttchart.ui;

public class SeriesStyle {
	
	private String _color;
	private boolean _visible;

	public String getColor() {
		return _color;
	}

	public void setColor(String color) {
		this._color = color;
	}

	public boolean isVisible() {
		return _visible;
	}

	public void setVisible(boolean visible) {
		this._visible = visible;
	}

}
