package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class Trait {
	public String name;
	public String points;
	public String modifier;

	public static List<Trait> generate(NodeList list) {
		ArrayList<Trait> traits = new ArrayList<Trait>();
		for (int i = 0; i < list.getLength(); i++) {
			Element e = (Element) list.item(i);
			Trait trait = generate(e);

			traits.add(trait);
		}
		return traits;
	}

	public static Trait generate(Element node) {
		Trait trait = new Trait();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		name = node.getElementsByTagName("name").item(0).getTextContent();
		trait.points = XMLUtilities.getOptionalNode(node, "points");

		String modifier = "";

		Node modifierNode = (Node) node.getElementsByTagName("Modifier").item(
				0);
		if (modifierNode != null) {
			String modiferLevel = Utilities.getLevelName(modifierNode);
			String modifierName = ((Element) modifierNode)
					.getElementsByTagName("name").item(0).getTextContent();
			if (modiferLevel != null && !modiferLevel.equals("")) {
				modifier = String.format("%s (%s)", modifierName,
						modiferLevel);
			} else {
				modifier = modifierName;
			}
		}
		String level = Utilities.getLevelName(node);
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null) {
			name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		if (level != null && !level.equals("")) {
			trait.name = String.format("%s (%s)", name, level);
		} else {
			trait.name = name;
		}
		trait.modifier = modifier;
		return trait;
	}


}
