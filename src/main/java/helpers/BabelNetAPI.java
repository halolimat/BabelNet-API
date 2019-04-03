package helpers;

import com.google.gson.Gson;
import it.uniroma1.lcl.babelnet.*;
import it.uniroma1.lcl.babelnet.data.*;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.kb.Domain;
import it.uniroma1.lcl.kb.SynsetType;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BabelNetAPI
{

	public static String parse(String word) {

		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(word, Language.EN);
		Collections.sort(synsets, new BabelSynsetComparator(word));

		System.out.println(synsets.size());

		JSONArray json_out = new JSONArray();

		try {
			for (BabelSynset synset : synsets) {

				//			POS pos = synset.getPOS();
				Optional<BabelSense> main_sense = synset.getMainSense(Language.EN);
				//			Optional<BabelSense> pref_sense = synset.getMainSensePreferrablyIn(Language.EN);
				//			List<BabelSenseSource> sense_sources = synset.getSenseSources();
				//			List<BabelExample> examples = synset.getExamples();
				//			List<BabelExample> en_examlples=synset.getExamples(Language.EN);
				//			boolean is_concept=synset.isKeyConcept();
				//			Optional<BabelExample> main_example = synset.getMainExample();
				//			List<BabelSynsetRelation> out_edges = synset.getOutgoingEdges();

				List<BabelLemma> lemma = synset.getLemmas(Language.EN);
				List<BabelCategory> cats = synset.getCategories(Language.EN);
				HashMap<Domain, Double> domains = synset.getDomains();
				List<BabelGloss> glosses = synset.getGlosses(Language.EN);
				List<BabelSense> senses = synset.getSenses(Language.EN);
				SynsetType type = synset.getType();

				JSONObject json_obj = new JSONObject();
				json_obj.put("lemma", new Gson().toJson(lemma));
				json_obj.put("categories", new Gson().toJson(cats));
				json_obj.put("domains", new Gson().toJson(domains));
				json_obj.put("glosses", new Gson().toJson(glosses));
				json_obj.put("senses", new Gson().toJson(senses));
				json_obj.put("type", new Gson().toJson(type));
				json_obj.put("main_sense", new Gson().toJson(main_sense));

				json_out.add(json_obj);

				System.out.println("#######################");
				System.out.println(main_sense);
				System.out.println("#######################");

				for(BabelSense sense: senses){
					System.out.println(sense);
				}
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(cats);
				System.exit(0);

			}
		}

		catch(Exception e){

		}

		return json_out.toJSONString();
	}

	public static String get_categories(String word) {

		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(word, Language.EN);
		Collections.sort(synsets, new BabelSynsetComparator(word));

		JSONObject json_obj = new JSONObject();

		try {

			BabelSynset synset = synsets.get(0);
			List<BabelCategory> cats = synset.getCategories(Language.EN);
			json_obj.put("categories", new Gson().toJson(cats));
		}

		catch(Exception e){}

		return json_obj.toJSONString();

	}

	public static void main(String args[]){
		String output = parse("t-cells");

		System.out.println(output);
	}
}
