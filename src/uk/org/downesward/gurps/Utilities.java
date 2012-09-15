package uk.org.downesward.gurps;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Utilities {
	public static String getLevelName(Node node) {
		boolean zeroBased = false;

		Node levelNamesNode = ((Element) node).getElementsByTagName(
				"levelnames").item(0);
		if (levelNamesNode != null) {
			String[] levelNames = levelNamesNode.getTextContent().split(",");
			if (levelNames.length > 0) {
				if (levelNames[0].equals("[None]")) {
					zeroBased = true;
				}
			}
			String levelIndex = ((Element) node).getElementsByTagName("level")
					.item(0).getTextContent();
			int index = Integer.parseInt(levelIndex);
			if (!zeroBased) {
				index--;
			}
			return levelNames[index];
		}
		return "";
	}
}
