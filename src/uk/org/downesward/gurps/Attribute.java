package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class Attribute implements Comparable<Attribute> {
	public String name;
	public String points;
	public String score;
	public Integer mainwin;
	
	public Attribute()
	{
	}
	
	public Attribute(String name, String points, String score, int mainwin) 
	{
		this.name = name;
		this.points = points;
		this.score = score; 
		this.mainwin = mainwin;
	}
	
	@Override
	public int compareTo(Attribute arg0) {
		return this.mainwin.compareTo(arg0.mainwin);
	}
	
	public static List<Attribute> generate(NodeList list)
	{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		for (int i = 0; i < list.getLength(); i++)
		{
			Element e = (Element)list.item(i);
			Boolean shouldHide = false;
			Node hide = e.getElementsByTagName("hide").item(0);
			if (hide != null && hide.getTextContent().equalsIgnoreCase("yes"))
			{
				shouldHide = true;
			}
			if (!shouldHide)
			{
				Attribute attrib = generate(e);
				attributes.add(attrib);
			}
		}

		Collections.sort(attributes);
		return attributes;
	}

	public static Attribute generate(Element node) {
		Attribute attrib = new Attribute();

		attrib.name = node.getElementsByTagName("name").item(0).getTextContent();
		attrib.mainwin = Integer.parseInt(node.getElementsByTagName("mainwin").item(0).getTextContent());
		attrib.points = XMLUtilities.getOptionalNode(node, "points");
		attrib.score = XMLUtilities.getOptionalNode(node, "score");

		return attrib;
	}

}
