package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class Spell {
	public String name;
	public String points;
	public String level;

	public static List<Spell> generate(NodeList list) {
		ArrayList<Spell> spells = new ArrayList<Spell>();
		for (int i = 0; i < list.getLength(); i++) {
			Element e = (Element) list.item(i);
			Spell spell = generate(e);
			spells.add(spell);
		}
		return spells;
	}

	public static Spell generate(Element node) {
		Spell spell = new Spell();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null) {
			name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		spell.name = name;
		spell.points = XMLUtilities.getOptionalNode(node, "points");
		spell.level = node.getElementsByTagName("level").item(0)
				.getTextContent();
		return spell;
	}
}
