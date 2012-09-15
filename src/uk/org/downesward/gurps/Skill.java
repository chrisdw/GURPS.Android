package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class Skill {
	public String name;
	public String points;
	public String typename;
	public String step;
	public String level;
	public String bonuslist;

	public static List<Skill> generate(NodeList list) {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		for (int i = 0; i < list.getLength(); i++) {
			Element e = (Element) list.item(i);
			Skill skill = generate(e);
			skills.add(skill);
		}
		return skills;
	}

	public static Skill generate(Element node) {
		Skill skill = new Skill();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		String typeName = node.getElementsByTagName("type").item(0)
				.getTextContent();
		String level = node.getElementsByTagName("level").item(0)
				.getTextContent();
		String step = node.getElementsByTagName("stepoff").item(0)
				.getTextContent()
				+ node.getElementsByTagName("step").item(0).getTextContent();
		skill.points = XMLUtilities.getOptionalNode(node, "points");
		Node tl = node.getElementsByTagName("tl").item(0);
		if (tl != null && !tl.getTextContent().equals(""))
		{
		    name = String.format("%s/TL%s", name, tl.getTextContent());
		}
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null)
		{
		    name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		skill.name = name;
		skill.typename = typeName;
		skill.step = step;
		skill.level = level;
		skill.bonuslist = XMLUtilities.getOptionalNode(node, "bonuslist");

		return skill;
	}
}
