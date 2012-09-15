package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class Equipment {
	public String name;
	public String cost;
	public String weight;
	public String location;
	public String quantity;
	
	public static List<Equipment> generate(NodeList list) {
		ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
		for (int j = 0; j < list.getLength(); j++) {
			Element e = (Element) list.item(j);
			Equipment weapon = generate(e);

			equipmentList.add(weapon);
		}
		return equipmentList;
	}	
	
	public static Equipment generate(Element node) {
		Equipment equipment = new Equipment();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null) {
			name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		equipment.name = name;
		equipment.cost = String.format("$%s", node.getElementsByTagName("cost")
				.item(0).getTextContent());
		equipment.weight = node.getElementsByTagName("weight").item(0)
				.getTextContent();
		equipment.location = XMLUtilities.getOptionalNode(node, "location");		
		equipment.quantity = XMLUtilities.getOptionalNode(node, "count");		
		return equipment;
	}
}
