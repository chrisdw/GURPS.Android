package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class RangedWeapon {
	public String name;
	public String cost;
	public String weight;
	public String lc;
	public String damage;
	public String range;
	public String minst;
	public String rcl;
	public String acc;
	public String rof;
	public String shots;
	public String bulk;
	
	public static List<RangedWeapon> generate(NodeList list) {
		ArrayList<RangedWeapon> weapons = new ArrayList<RangedWeapon>();
		for (int j = 0; j < list.getLength(); j++) {
			Element e = (Element) list.item(j);
			RangedWeapon weapon = generate(e);   

			weapons.add(weapon);
		}
		return weapons;
	}

	public static RangedWeapon generate(Element node) {
		RangedWeapon weapon = new RangedWeapon();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null) {
			name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		weapon.name = name;
		weapon.cost = String.format("$%s", node.getElementsByTagName("cost").item(0)
				.getTextContent());
		weapon.weight = node.getElementsByTagName("weight").item(0)
				.getTextContent();
		String delimiter = "\\|";
		weapon.lc = node.getElementsByTagName("lc").item(0).getTextContent();
		String[] charDamage = XMLUtilities.getOptionalNode(node, "chardamage").split(delimiter);
		String[] damType = XMLUtilities.getOptionalNode(node, "damtype").split(delimiter);
		String[] charrangehalfdam = XMLUtilities.getOptionalNode(node, "charrangehalfdam").split(delimiter);
		String[] charrangemax = XMLUtilities.getOptionalNode(node, "charrangemax").split(delimiter);
		String[] shots = XMLUtilities.getOptionalNode(node, "shots").split(delimiter);
		String[] stList = node.getElementsByTagName("minst").item(0).getTextContent().split(delimiter);
		String[] bulk = node.getElementsByTagName("bulk").item(0).getTextContent().split(delimiter);
		String[] rof = XMLUtilities.getOptionalNode(node, "rof").split(delimiter);
		String[] acc = XMLUtilities.getOptionalNode(node, "acc").split(delimiter);
		String[] mode =  XMLUtilities.getOptionalNode(node, "mode").split(delimiter);

		int index = 0;
		if (mode.length > 1)
		{
		    // Need to find "thrown"
		    for (int i = 0; i < mode.length; i++)
		    {
		        if (mode[i].equalsIgnoreCase("thrown"))
		        {
		            index = i;
		            break;
		        }
		    }
		}
		weapon.damage = String.format("%s %s", charDamage[index], damType[index]);
		if (charrangehalfdam[index].trim().length() > 0 && charrangemax[index].trim().length() > 0) {
			weapon.range = String.format("%s/%s", charrangehalfdam[index].trim(), charrangemax[index].trim());
		}
		weapon.minst = stList[index];   

		weapon.rcl = XMLUtilities.getOptionalNode(node, "rcl");

		weapon.acc = acc[index];   
		weapon.rof = rof[index];   
		weapon.shots = shots[index];   
		weapon.bulk = bulk[index];
		return weapon;
	}
}
