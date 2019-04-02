package helpers;

import com.babelscape.util.POS;
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

	public static String parse(String word)
	{

		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(word, Language.EN);
		Collections.sort(synsets, new BabelSynsetComparator(word));

		JSONArray json_out = new JSONArray();

		for (BabelSynset synset : synsets){
			POS pos = synset.getPOS();
			List<BabelLemma> lemma=synset.getLemmas(Language.EN);
			List<BabelCategory> cats=synset.getCategories(Language.EN);
			Optional<BabelSense> main_sense = synset.getMainSense(Language.EN);
			Optional<BabelSense> pref_sense = synset.getMainSensePreferrablyIn(Language.EN);
			List<BabelSenseSource> sense_sources = synset.getSenseSources();
			HashMap<Domain, Double> domains=synset.getDomains();
			List<BabelExample> examples = synset.getExamples();
			List<BabelExample> en_examlples=synset.getExamples(Language.EN);
			List<BabelGloss> glosses = synset.getGlosses(Language.EN);
			boolean is_concept=synset.isKeyConcept();
			Optional<BabelExample> main_example = synset.getMainExample();
			List<BabelSynsetRelation> out_edges = synset.getOutgoingEdges();
			List<BabelSense> senses = synset.getSenses(Language.EN);
			SynsetType type=synset.getType();

			JSONObject json_obj = new JSONObject();
			json_obj.put("lemma", new Gson().toJson(lemma));
			json_obj.put("categories", new Gson().toJson(cats));
			json_obj.put("domains", new Gson().toJson(domains));
			json_obj.put("glosses", new Gson().toJson(glosses));
//			json_obj.put("out_edges", out_edges);
			json_obj.put("senses", new Gson().toJson(senses));
			json_obj.put("type", new Gson().toJson(type));
//			json_obj.put("en_examples", en_examlples);

			json_out.add(json_obj);

//			System.out.println("=========================================================");
//
////			System.out.println("pos");
////			System.out.println(pos);
//			System.out.println("lemma");
//			System.out.println(lemma);
//			System.out.println("cats");
//			System.out.println(cats);
////			System.out.println("main_sense");
////			System.out.println(main_sense);
////			System.out.println("pref_sense");
////			System.out.println(pref_sense);
////			System.out.println("sense_sources");
////			System.out.println(sense_sources);
//			System.out.println("domains");
//			System.out.println(domains);
//			System.out.println("examples");
//			System.out.println(examples);
//			System.out.println("en_examlples");
//			System.out.println(en_examlples);
//			System.out.println("glosses");
//			System.out.println(glosses);
//			System.out.println("is_concept");
//			System.out.println(is_concept);
////			System.out.println("main_example");
////			System.out.println(main_example);
//			System.out.println("out_edges");
//			System.out.println(out_edges);
//			System.out.println("senses");
//			System.out.println(senses);
//			System.out.println("type");
//			System.out.println(type);
		}
		return json_out.toJSONString();
	}

	public static void main(String args[]){
		String output = parse("cancer");

		System.out.println(output);
	}
}
