package uk.org.downesward.gurps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.org.downesward.utiliites.XMLUtilities;

import com.lamerman.FileDialog;
import com.lamerman.SelectionMode;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterViewerActivity extends TabActivity {
	private static final int REQUEST_OPEN = 1;
	private Document character;
	private GeneralAtrributeAdapter m_generaladapter;
	private ArrayList<GeneralAttribute> m_general = new ArrayList<GeneralAttribute>();
	private ArrayList<Attribute> m_attribs = new ArrayList<Attribute>();
	private AttributeAdapter m_attribsadapter;
	private ArrayList<Trait> m_advantages = new ArrayList<Trait>();
	private TraitAdapter m_advantagesadapter;
	private ArrayList<Trait> m_disadvantages = new ArrayList<Trait>();
	private TraitAdapter m_disadvantagesadapter;
	private ArrayList<Trait> m_perks = new ArrayList<Trait>();
	private TraitAdapter m_perksadapter;
	private ArrayList<Trait> m_quirks = new ArrayList<Trait>();
	private TraitAdapter m_quirksadapter;
	private ArrayList<Skill> m_skills = new ArrayList<Skill>();
	private SkillAdapter m_skillsadapter;
	private ArrayList<RangedWeapon> m_rangedweapons = new ArrayList<RangedWeapon>();
	private RangedWeaponAdapter m_rangedweaponsadapter;
	private ArrayList<MeleeWeapon> m_meleeweapons = new ArrayList<MeleeWeapon>();
	private MeleeWeaponAdapter m_meleeweaponsadapter;
	private ArrayList<Equipment> m_equipment = new ArrayList<Equipment>();
	private EquipmentAdapter m_equipmentadapter;
	private ArrayList<Spell> m_spells = new ArrayList<Spell>();
	private SpellAdapter m_spelladapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab

		this.m_generaladapter = new GeneralAtrributeAdapter(this,
				R.layout.generalattribute, m_general);
		this.m_attribsadapter = new AttributeAdapter(this, R.layout.attribute,
				m_attribs);
		this.m_advantagesadapter = new TraitAdapter(this, R.layout.trait,
				m_advantages);
		this.m_disadvantagesadapter = new TraitAdapter(this, R.layout.trait,
				m_disadvantages);
		this.m_perksadapter = new TraitAdapter(this, R.layout.trait, m_perks);
		this.m_quirksadapter = new TraitAdapter(this, R.layout.trait, m_quirks);
		this.m_skillsadapter = new SkillAdapter(this, R.layout.skill, m_skills);
		this.m_rangedweaponsadapter = new RangedWeaponAdapter(this,
				R.layout.rangedweapon, m_rangedweapons);
		this.m_meleeweaponsadapter = new MeleeWeaponAdapter(this,
				R.layout.meleeweapon, m_meleeweapons);
		this.m_equipmentadapter = new EquipmentAdapter(this,
				R.layout.equipment, m_equipment);
		this.m_spelladapter = new SpellAdapter(this, R.layout.spell, m_spells);

		ListView lstGeneral = (ListView) findViewById(R.id.lstGeneral);
		lstGeneral.setAdapter(this.m_generaladapter);

		ListView lstAttribs = (ListView) findViewById(R.id.lstAttributes);
		lstAttribs.setAdapter(this.m_attribsadapter);

		ListView lstAdvantages = (ListView) findViewById(R.id.lstAdvantages);
		lstAdvantages.setAdapter(this.m_advantagesadapter);

		ListView lstDisadvantages = (ListView) findViewById(R.id.lstDisadvantages);
		lstDisadvantages.setAdapter(this.m_disadvantagesadapter);

		ListView lstPerks = (ListView) findViewById(R.id.lstPerks);
		lstPerks.setAdapter(this.m_perksadapter);

		ListView lstQuirks = (ListView) findViewById(R.id.lstQuirks);
		lstQuirks.setAdapter(this.m_quirksadapter);

		ListView lstSkills = (ListView) findViewById(R.id.lstSkills);
		lstSkills.setAdapter(this.m_skillsadapter);

		ListView lstRangedWeapons = (ListView) findViewById(R.id.lstRangedWeapons);
		lstRangedWeapons.setAdapter(this.m_rangedweaponsadapter);

		ListView lstMeleeWeapons = (ListView) findViewById(R.id.lstMeleeWeapons);
		lstMeleeWeapons.setAdapter(this.m_meleeweaponsadapter);

		ListView lstEquipment = (ListView) findViewById(R.id.lstEquipment);
		lstEquipment.setAdapter(this.m_equipmentadapter);

		ListView lstSpells = (ListView) findViewById(R.id.lstSpells);
		lstSpells.setAdapter(this.m_spelladapter);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("general")
				.setIndicator("General",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstGeneral);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("attributes")
				.setIndicator("Attributes",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstAttributes);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("advantages")
				.setIndicator("Advantages",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstAdvantages);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("disadvantages")
				.setIndicator("Disadvantages",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstDisadvantages);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("perks")
				.setIndicator("Perks", res.getDrawable(R.drawable.ic_tab_perks))
				.setContent(R.id.lstPerks);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("quirks")
				.setIndicator("Quirks", res.getDrawable(R.drawable.ic_tab_quirks))
				.setContent(R.id.lstQuirks);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("skills")
				.setIndicator("Skills", res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstSkills);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("rangedweapons")
				.setIndicator("Ranged Weapons",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstRangedWeapons);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("meleeweapons")
				.setIndicator("Melee Weapons",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstMeleeWeapons);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("equipment")
				.setIndicator("Equipment",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstEquipment);
		tabHost.addTab(spec);

		spec = tabHost
				.newTabSpec("spells")
				.setIndicator("Spells", res.getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.lstSpells);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.character, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.mnu_character_open) {
			getCharacterFile();
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void getCharacterFile() {
		Intent intent = new Intent(this.getBaseContext(), FileDialog.class);
		intent.putExtra(FileDialog.START_PATH,
				Environment.getExternalStorageDirectory());
		intent.putExtra(FileDialog.SELECTION_MODE, SelectionMode.MODE_OPEN);
		this.startActivityForResult(intent, REQUEST_OPEN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Resources res = getResources();

		if (requestCode == REQUEST_OPEN) {
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra(FileDialog.RESULT_PATH);
				try {
					character = XMLUtilities.readXmlFile(path);

					Node general = character.getElementsByTagName("General")
							.item(0);
					Node campaign = character.getElementsByTagName("Campaign")
							.item(0);

					NodeList attribs = general.getChildNodes();

					XPathFactory factory = XPathFactory.newInstance();
					XPath xPath = factory.newXPath();

					for (int i = 0; i < attribs.getLength(); i++) {
						Node e = attribs.item(i);
						if (e.getNodeName().equals("name")) {
							this.setTitle("Gurps Character Viewer ("
									+ e.getTextContent() + ")");
							break;
						}
					}

					Node node = (Node) xPath.evaluate("basetl", campaign,
							XPathConstants.NODE);

					// Build a list of attributes
					this.m_generaladapter.notifyDataSetInvalidated();
					this.m_generaladapter.clear();
					addAttribute(general, xPath, "playername", "Player Name");
					addAttribute(general, xPath, "race", "Race");
					addAttribute(general, xPath, "height", "Height");
					addAttribute(general, xPath, "weight", "Weight");
					addAttribute(general, xPath, "appearance", "Appearance");
					addAttribute(general, xPath, "currentload", "Current Load");
					addAttribute(general, xPath, "encumbrancelevel",
							"Encumbrance Level");
					addAttribute(general, xPath, "parryusing", "Parry Using");
					addAttribute(general, xPath, "parryscore", "Parry Score");
					addAttribute(general, xPath, "blockusing", "Block Using");
					addAttribute(general, xPath, "blockscore", "Block Score");

					View tab;

					if (node != null && node.getTextContent().length() > 0) {
						this.m_generaladapter.add(new GeneralAttribute(
								"Campaign Tech Level", node.getTextContent()));
					}
					this.m_generaladapter.notifyDataSetChanged();

					// Now do the attributes
					Node traits = character.getElementsByTagName("Traits")
							.item(0);

					NodeList attributes = (NodeList) xPath.evaluate(
							"Attribute[mainwin]", traits,
							XPathConstants.NODESET);
					List<Attribute> attributeList = Attribute
							.generate(attributes);

					this.m_attribsadapter.notifyDataSetInvalidated();
					this.m_attribsadapter.clear();
					for (int i = 0; i < attributeList.size(); i++) {
						this.m_attribsadapter.add(attributeList.get(i));
					}
					this.m_attribsadapter.notifyDataSetChanged();

					// Generate a list of advantages
					setupTraitList(this.m_advantagesadapter,
							character.getElementsByTagName("Advantage"));

					// Generate list of disadvantages
					setupTraitList(this.m_disadvantagesadapter,
							character.getElementsByTagName("Disadvantage"));

					// Generate list of perks
					setupTraitList(this.m_perksadapter,
							character.getElementsByTagName("Perks"));

					tab = getTabHost().getTabWidget().getChildAt(4);
					if (tab != null) {
						if (this.m_perksadapter.isEmpty()) {
							tab.setVisibility(View.GONE);
						} else {
							tab.setVisibility(View.VISIBLE);
						}
					}

					// Generate list of Quirks
					setupTraitList(this.m_quirksadapter,
							character.getElementsByTagName("Quirk"));

					tab = getTabHost().getTabWidget().getChildAt(5);
					if (tab != null) {
						if (this.m_quirksadapter.isEmpty()) {
							tab.setVisibility(View.GONE);
						} else {
							tab.setVisibility(View.VISIBLE);
						}
					}

					// Generate list of skills
					List<Skill> skillsList = Skill.generate(character
							.getElementsByTagName("Skill"));
					this.m_skillsadapter.notifyDataSetInvalidated();
					this.m_skillsadapter.clear();
					for (int i = 0; i < skillsList.size(); i++) {
						this.m_skillsadapter.add(skillsList.get(i));
					}
					this.m_skillsadapter.notifyDataSetChanged();

					// Ranged weapons
					List<RangedWeapon> rangedWeaponList = RangedWeapon
							.generate((NodeList) xPath.evaluate(
									"Equipment[rangehalfdam]", traits,
									XPathConstants.NODESET));
					this.m_rangedweaponsadapter.notifyDataSetInvalidated();
					this.m_rangedweaponsadapter.clear();
					for (int i = 0; i < rangedWeaponList.size(); i++) {
						this.m_rangedweaponsadapter
								.add(rangedWeaponList.get(i));
					}
					this.m_rangedweaponsadapter.notifyDataSetChanged();

					tab = getTabHost().getTabWidget().getChildAt(7);
					if (tab != null) {
						if (this.m_rangedweaponsadapter.isEmpty()) {
							tab.setVisibility(View.GONE);
						} else {
							tab.setVisibility(View.VISIBLE);
						}
					}
					// Melee Weapons
					List<MeleeWeapon> meleeWeaponList = MeleeWeapon
							.generate((NodeList) xPath.evaluate(
									"Equipment[contains(cat, 'Melee')]",
									traits, XPathConstants.NODESET));
					this.m_meleeweaponsadapter.notifyDataSetInvalidated();
					this.m_meleeweaponsadapter.clear();
					for (int i = 0; i < meleeWeaponList.size(); i++) {
						this.m_meleeweaponsadapter.add(meleeWeaponList.get(i));
					}
					this.m_meleeweaponsadapter.notifyDataSetChanged();

					tab = getTabHost().getTabWidget().getChildAt(8);
					if (tab != null) {
						if (this.m_meleeweaponsadapter.isEmpty()) {
							tab.setVisibility(View.GONE);
						} else {
							tab.setVisibility(View.VISIBLE);
						}
					}

					// Other Equipment
					List<Equipment> equipmentList = Equipment
							.generate((NodeList) xPath.evaluate(
									"Equipment[not (damage)]", traits,
									XPathConstants.NODESET));
					this.m_equipmentadapter.notifyDataSetInvalidated();
					this.m_equipmentadapter.clear();
					for (int i = 0; i < equipmentList.size(); i++) {
						this.m_equipmentadapter.add(equipmentList.get(i));
					}
					this.m_equipmentadapter.notifyDataSetChanged();

					tab = getTabHost().getTabWidget().getChildAt(9);
					if (tab != null) {
						if (this.m_equipmentadapter.isEmpty()) {
							tab.setVisibility(View.GONE);
						} else {
							tab.setVisibility(View.VISIBLE);
						}
					}

					// Spells
					List<Spell> spells = Spell.generate(character
							.getElementsByTagName("Spell"));
					this.m_spelladapter.notifyDataSetInvalidated();
					this.m_spelladapter.clear();
					for (int i = 0; i < spells.size(); i++) {
						this.m_spelladapter.add(spells.get(i));
					}
					this.m_spelladapter.notifyDataSetChanged();
					tab = getTabHost().getTabWidget().getChildAt(10);
					if (tab != null) {
						if (this.m_spelladapter.isEmpty()) {
							tab.setVisibility(View.GONE);
						} else {
							tab.setVisibility(View.VISIBLE);
						}
					}
					
				} catch (SAXException e) {
					Toast.makeText(
							this,
							String.format(
									res.getString(R.string.sax_exception),
									e.getMessage()), Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					Toast.makeText(
							this,
							String.format(res.getString(R.string.io_exception),
									e.getMessage()), Toast.LENGTH_LONG).show();
				} catch (ParserConfigurationException e) {
					Toast.makeText(
							this,
							String.format(
									res.getString(R.string.parser_config_exception),
									e.getMessage()), Toast.LENGTH_LONG).show();
				} catch (XPathExpressionException e) {
					Toast.makeText(
							this,
							String.format(
									res.getString(R.string.parser_config_exception),
									e.getMessage()), Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private void setupTraitList(TraitAdapter traitAdapter, NodeList traitList) {
		List<Trait> advantagesList = Trait.generate(traitList);
		traitAdapter.notifyDataSetInvalidated();
		traitAdapter.clear();
		for (int i = 0; i < advantagesList.size(); i++) {
			traitAdapter.add(advantagesList.get(i));
		}
		traitAdapter.notifyDataSetChanged();
	}

	private void addAttribute(Node attributes, XPath xPath, String attribute,
			String label) throws XPathExpressionException {
		Node node = (Node) xPath.evaluate(attribute, attributes,
				XPathConstants.NODE);
		if (node != null && node.getTextContent().length() > 0) {
			this.m_generaladapter.add(new GeneralAttribute(label, node
					.getTextContent()));
		}
	}

	private class GeneralAtrributeAdapter extends
			ArrayAdapter<GeneralAttribute> {
		private ArrayList<GeneralAttribute> items;

		public GeneralAtrributeAdapter(Context context, int textViewResourceId,
				ArrayList<GeneralAttribute> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.generalattribute, null);
			}
			GeneralAttribute o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				TextView bt = (TextView) v.findViewById(R.id.txtValue);
				if (tt != null) {
					tt.setText(o.name);
				}
				if (bt != null) {
					bt.setText(o.value);
				}
			}
			return v;
		}

	}

	private class AttributeAdapter extends ArrayAdapter<Attribute> {
		private ArrayList<Attribute> items;

		public AttributeAdapter(Context context, int textViewResourceId,
				ArrayList<Attribute> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.attribute, null);
			}
			Attribute o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				TextView bt = (TextView) v.findViewById(R.id.txtPoints);
				TextView bv = (TextView) v.findViewById(R.id.txtScore);
				if (tt != null) {
					tt.setText(o.name);
				}
				if (bt != null) {
					bt.setText(o.points);
				}
				if (bv != null) {
					bv.setText(o.score);
				}
			}
			return v;
		}

	}

	private class TraitAdapter extends ArrayAdapter<Trait> {
		private ArrayList<Trait> items;

		public TraitAdapter(Context context, int textViewResourceId,
				ArrayList<Trait> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.trait, null);
			}
			Trait o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				TextView bt = (TextView) v.findViewById(R.id.txtPoints);
				TextView tm = (TextView) v.findViewById(R.id.txtModifier);
				if (tt != null) {
					tt.setText(o.name);
				}
				if (bt != null) {
					bt.setText(o.points);
				}
				if (tm != null) {
					tm.setText(o.modifier);
				}
			}
			return v;
		}

	}

	private class SkillAdapter extends ArrayAdapter<Skill> {
		private ArrayList<Skill> items;

		public SkillAdapter(Context context, int textViewResourceId,
				ArrayList<Skill> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.skill, null);
			}
			Skill o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				TextView bt = (TextView) v.findViewById(R.id.txtPoints);
				TextView tm = (TextView) v.findViewById(R.id.txtTypeName);
				TextView ts = (TextView) v.findViewById(R.id.txtStep);
				TextView tl = (TextView) v.findViewById(R.id.txtLevel);
				TextView tb = (TextView) v.findViewById(R.id.txtBonusList);
				if (tt != null) {
					tt.setText(o.name);
				}
				if (bt != null) {
					bt.setText(o.points);
				}
				if (tm != null) {
					tm.setText(o.typename);
				}
				if (ts != null) {
					ts.setText(o.step);
				}
				if (tl != null) {
					tl.setText(o.level);
				}
				if (tb != null) {
					tb.setText(o.bonuslist);
				}
			}
			return v;
		}

	}

	private class RangedWeaponAdapter extends ArrayAdapter<RangedWeapon> {
		private ArrayList<RangedWeapon> items;

		public RangedWeaponAdapter(Context context, int textViewResourceId,
				ArrayList<RangedWeapon> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.rangedweapon, null);
			}
			RangedWeapon o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				if (tt != null) {
					tt.setText(o.name);
				}
				TextView tlc = (TextView) v.findViewById(R.id.txtLc);
				if (tlc != null) {
					tlc.setText(o.lc);
				}
				TextView tdam = (TextView) v.findViewById(R.id.txtDamage);
				if (tdam != null) {
					tdam.setText(o.damage);
				}
				TextView tacc = (TextView) v.findViewById(R.id.txtAcc);
				if (tacc != null) {
					tacc.setText(o.acc);
				}
				TextView tr = (TextView) v.findViewById(R.id.txtRange);
				if (tr != null) {
					tr.setText(o.range);
				}
				TextView trof = (TextView) v.findViewById(R.id.txtRof);
				if (trof != null) {
					trof.setText(o.rof);
				}
				TextView tshots = (TextView) v.findViewById(R.id.txtShots);
				if (tshots != null) {
					tshots.setText(o.shots);
				}
				TextView tminst = (TextView) v.findViewById(R.id.txtMinst);
				if (tminst != null) {
					tminst.setText(o.minst);
				}
				TextView tbulk = (TextView) v.findViewById(R.id.txtBulk);
				if (tbulk != null) {
					tbulk.setText(o.bulk);
				}
				TextView tcost = (TextView) v.findViewById(R.id.txtCost);
				if (tcost != null) {
					tcost.setText(o.cost);
				}
				TextView tweight = (TextView) v.findViewById(R.id.txtWeight);
				if (tweight != null) {
					tweight.setText(o.weight);
				}
			}
			return v;
		}

	}

	private class MeleeWeaponAdapter extends ArrayAdapter<MeleeWeapon> {
		private ArrayList<MeleeWeapon> items;

		public MeleeWeaponAdapter(Context context, int textViewResourceId,
				ArrayList<MeleeWeapon> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.meleeweapon, null);
			}
			MeleeWeapon o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				if (tt != null) {
					tt.setText(o.name);
				}
				TextView tlc = (TextView) v.findViewById(R.id.txtLc);
				if (tlc != null) {
					tlc.setText(o.lc);
				}

				TextView tcost = (TextView) v.findViewById(R.id.txtCost);
				if (tcost != null) {
					tcost.setText(o.cost);
				}
				TextView tweight = (TextView) v.findViewById(R.id.txtWeight);
				if (tweight != null) {
					tweight.setText(o.weight);
				}
				TextView tdamage = (TextView) v.findViewById(R.id.txtDamage);
				if (tdamage != null) {
					tdamage.setText(o.damage);
				}
				TextView tminst = (TextView) v.findViewById(R.id.txtMinst);
				if (tminst != null) {
					tminst.setText(o.minst);
				}
				TextView treach = (TextView) v.findViewById(R.id.txtReach);
				if (treach != null) {
					treach.setText(o.reach);
				}					
			}
			return v;
		}

	}

	private class EquipmentAdapter extends ArrayAdapter<Equipment> {
		private ArrayList<Equipment> items;

		public EquipmentAdapter(Context context, int textViewResourceId,
				ArrayList<Equipment> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.equipment, null);
			}
			Equipment o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.txtName);
				if (tt != null) {
					tt.setText(o.name);
				}
				TextView tlc = (TextView) v.findViewById(R.id.txtLocation);
				if (tlc != null) {
					tlc.setText(o.location);
				}

				TextView tcost = (TextView) v.findViewById(R.id.txtCost);
				if (tcost != null) {
					tcost.setText(o.cost);
				}
				TextView tweight = (TextView) v.findViewById(R.id.txtWeight);
				if (tweight != null) {
					tweight.setText(o.weight);
				}
				TextView txtQuantity = (TextView) v.findViewById(R.id.txtQuantity);
				if (txtQuantity != null) {
					txtQuantity.setText(o.quantity);
				}
			}
			return v;
		}

	}

	private class SpellAdapter extends ArrayAdapter<Spell> {
		private ArrayList<Spell> items;

		public SpellAdapter(Context context, int textViewResourceId,
				ArrayList<Spell> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.spell, null);
			}
			Spell o = items.get(position);
			if (o != null) {
				TextView txtName = (TextView) v.findViewById(R.id.txtName);
				TextView txtPoints = (TextView) v.findViewById(R.id.txtPoints);
				TextView txtLevel = (TextView) v.findViewById(R.id.txtLevel);
				if (txtName != null) {
					txtName.setText(o.name);
				}
				if (txtPoints != null) {
					txtPoints.setText(o.points);
				}
				if (txtLevel != null) {
					txtLevel.setText(o.level);
				}

			}
			return v;
		}

	}
}