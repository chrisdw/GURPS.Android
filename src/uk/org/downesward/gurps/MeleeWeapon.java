package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.org.downesward.utiliites.XMLUtilities;

public class MeleeWeapon {
	public String name;
	public String cost;
	public String weight;
	public String lc;
	public String damage;
	public String minst;
	public String reach;

	public static List<MeleeWeapon> generate(NodeList list) {
		ArrayList<MeleeWeapon> weapons = new ArrayList<MeleeWeapon>();
		for (int j = 0; j < list.getLength(); j++) {
			Element e = (Element) list.item(j);
			MeleeWeapon weapon = generate(e, "swing");
			
			weapons.add(weapon);
			
			weapon = generate(e, "thrust");

			weapons.add(weapon);
		}
		return weapons;
	}

	/**
	 * Generates a basic melee weapon record as if for a piece of
	 * equipment.
	 * @param node
	 * @return
	 */
	public static MeleeWeapon generate(Element node) {
		MeleeWeapon weapon = new MeleeWeapon();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null) {
			name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		weapon.name = name;
		weapon.cost = String.format("$%s", node.getElementsByTagName("cost")
				.item(0).getTextContent());
		weapon.weight = node.getElementsByTagName("weight").item(0)
				.getTextContent();
		weapon.lc = node.getElementsByTagName("lc").item(0).getTextContent();		
		return weapon;
	}
	
	/**
	 * Generates a melee weapon record with combat data for a particular
	 * mode of operation
	 * @param node
	 * @param modeRequired
	 * @return
	 */
	public static MeleeWeapon generate(Element node, String modeRequired) {
		MeleeWeapon weapon = new MeleeWeapon();
		String name = node.getElementsByTagName("name").item(0)
				.getTextContent();
		name = String.format("%s: %s", name, modeRequired);
		Node nameExt = node.getElementsByTagName("nameext").item(0);
		if (nameExt != null) {
			name = String.format("%s (%s)", name, nameExt.getTextContent());
		}
		weapon.name = name;
		weapon.cost = String.format("$%s", node.getElementsByTagName("cost")
				.item(0).getTextContent());
		weapon.weight = node.getElementsByTagName("weight").item(0)
				.getTextContent();
		weapon.lc = node.getElementsByTagName("lc").item(0).getTextContent();

		String delimiter = "\\|";
		String[] charDamage = XMLUtilities.getOptionalNode(node, "chardamage").split(delimiter);
		String[] damType = XMLUtilities.getOptionalNode(node, "damtype").split(delimiter);
		String[] mode =  XMLUtilities.getOptionalNode(node, "mode").split(delimiter);
		String[] stList = node.getElementsByTagName("minst").item(0).getTextContent().split(delimiter);
		String[] reach = node.getElementsByTagName("reach").item(0).getTextContent().split(delimiter);
			
		int index = 0;
		if (mode.length > 1)
		{
		    // Need to find correct mode
		    for (int i = 0; i < mode.length; i++)
		    {
		        if (mode[i].equalsIgnoreCase(modeRequired))
		        {
		            index = i;
		            break;
		        }
		    }
		}
		weapon.damage = String.format("%s %s", charDamage[index], damType[index]);
		weapon.minst = stList[index];   
		weapon.reach = reach[index];   
		return weapon;
	}
}
