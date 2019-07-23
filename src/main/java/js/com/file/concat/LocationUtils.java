package js.com.file.concat;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class LocationUtils {
	
	public static Point getLocationPoint(Dimension myDimension) {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		Point locationPoint = new Point(screenDimension.width/2-myDimension.width/2,screenDimension.height/2-myDimension.height/2);
		return locationPoint;
	}
	
	/**
	 * ���ϵ���һЩ����
	 * @param myDimension
	 * @param topAjust
	 * @return
	 */
	public static Point getLocationPointTopAjust(Dimension myDimension,int topAjust) {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		Point locationPoint = new Point(screenDimension.width/2-myDimension.width/2,screenDimension.height/2-myDimension.height/2-topAjust);
		return locationPoint;
	}

}
