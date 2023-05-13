package example;

import org.tweetyproject.logics.commons.syntax.Constant;
import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.Sort;
import org.tweetyproject.logics.fol.parser.FolParser;
import org.tweetyproject.logics.fol.reasoner.FolReasoner;
import org.tweetyproject.logics.fol.reasoner.SimpleFolReasoner;
import org.tweetyproject.logics.fol.syntax.FolBeliefSet;
import org.tweetyproject.logics.fol.syntax.FolFormula;
import org.tweetyproject.logics.fol.syntax.FolSignature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Mushroom {
//    All plants are either poisonous or non-poisonous
//Some plants are non-poisonous
//    There is a family of mushrooms that are all poisonous
//A type of mushroom from the family of poisonous mushrooms is harmful to humans
//Mushrooms are plants

//    All mushrooms belonging to the Amanita family are mushrooms
//    There exists at least one mushroom that belongs to the Amanita family
//    death cap mushrooms belong to the Amanita family
public static void main(String[] args) throws IOException {
    FolSignature sig = new FolSignature(true);

    Sort sortPlant = new Sort("plant");
    Sort mushroomFamily = new Sort("mushroomFamily");

    sig.add(sortPlant);
    sig.add(mushroomFamily);



    //les predicats
    List<Sort> predicateList = new ArrayList<Sort>();
    predicateList.add(Sort.ANY);
    Predicate p = new Predicate("Mushroom",predicateList);
    List<Sort> predicateList2 = new ArrayList<Sort>();
    predicateList2.add(Sort.ANY);
    Predicate p2 = new Predicate("Plant",predicateList2);
    List<Sort> poisonousList = new ArrayList<Sort>();
    poisonousList.add(Sort.ANY);
    Predicate p3 = new Predicate("Poisonous", poisonousList);
    List<Sort> familyList = new ArrayList<Sort>();
    familyList.add(Sort.ANY);
    Predicate p4 = new Predicate("Family", familyList);
    List<Sort> belongsList = new ArrayList<Sort>();
    belongsList.add(Sort.ANY);
    belongsList.add(Sort.ANY);
    Predicate p5 = new Predicate("Belongs", belongsList);
    List<Sort> harmfulList = new ArrayList<Sort>();
    harmfulList.add(Sort.ANY);
    Predicate p6 = new Predicate("Harmful", harmfulList);

    sig.add(p, p2,p3, p4, p5, p6);

    //les constantes
    Constant deathCap = new Constant("deathCap",sortPlant);
    Constant amanita = new Constant("amanita",mushroomFamily);
    sig.add(deathCap);
    sig.add(amanita);
    System.out.println("Signature: " + sig);

    //les formules
    FolParser parser = new FolParser();
    parser.setSignature(sig); //Use the signature defined above
    FolBeliefSet bs = new FolBeliefSet();
//    All plants are either poisonous or non-poisonous
    FolFormula f1 = parser.parseFormula("forall X: (Plant(X) => (Poisonous(X) || !Poisonous(X)))");
//    All poisonous plants are harmful to humans
    FolFormula f2 = parser.parseFormula("forall X: ((Poisonous(X) && Plant(X)) => Harmful(X))");
//    Some plants are non-poisonous
    FolFormula f3 = parser.parseFormula("exists X: (Plant(X) && !Poisonous(X))");
//    There is a family of mushrooms, such that all mushrooms in the family are poisonous
    FolFormula f4 = parser.parseFormula("exists X:( Family(X) && forall Y: ((Mushroom(Y) && Belongs(X,Y)) => Poisonous(Y)))");
//    death cap belongs to the Amanita family
    FolFormula f5 = parser.parseFormula("Belongs(deathCap,amanita)");

    //amanita is a mushroom family
    FolFormula f6 = parser.parseFormula("Family(amanita)");
    //amanita is a poisonous mushroom family
    FolFormula f7 = parser.parseFormula("forall X: ((Mushroom(X) && Belongs(X,amanita)) => Poisonous(X))");
    bs.add(f1,f2,f3,f4,f5,f6,f7);

    System.out.println("\nParsed BeliefBase: " + bs);

    //query
    FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); //Set default prover, options are NaiveProver, EProver, Prover9

    FolReasoner prover = FolReasoner.getDefaultReasoner();

    //prove that death cap is poisonous
    FolFormula query = parser.parseFormula("Harmful(deathCap)");
    System.out.println("\nQuery: " + query);
    System.out.println("Query result: " + prover.query(bs, query));

}
}
