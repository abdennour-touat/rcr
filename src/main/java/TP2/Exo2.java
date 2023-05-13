package TP2;

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

public class Exo2 {
    public static void main(String[] args) throws IOException {

//        FolSignature sig = new FolSignature(true);
//
//        //Add sort
//        Sort sortVoiture = new Sort("voiture");
//        sig.add(sortVoiture);
//        Sort sortEtudiant = new Sort("etudiant");
//        sig.add(sortEtudiant);
//
//        Constant vh = new Constant("A",sortVoiture);
//        Constant ms = new Constant("B",sortEtudiant);
//        sig.add(vh, ms);
//
//        //Add predicates
//        List<Sort> predicateList = new ArrayList<Sort>();
//        predicateList.add(sortVoiture);
//        Predicate p = new Predicate("Voiture",predicateList);
//        List<Sort> predicateList2 = new ArrayList<Sort>();
//        predicateList2.add(sortEtudiant);
//        Predicate p2 = new Predicate("Etudiant",predicateList2);
//        List<Sort> auteurList = new ArrayList<Sort>();
//        auteurList.add(sortEtudiant);
//        auteurList.add(sortVoiture);
//        Predicate p3 = new Predicate("Possede", auteurList);
//        List<Sort> predicateList3 = new ArrayList<Sort>();
//
//        sig.add(p, p2,p3);
//        System.out.println("Signature: " + sig);
//
//        /*
//         * Example 2: Parse formulas with FolParser using the signature defined above
//         */
//        FolParser parser = new FolParser();
//        parser.setSignature(sig); //Use the signature defined above
//        FolBeliefSet bs = new FolBeliefSet();
//        FolFormula a = (FolFormula)parser.parseFormula("forall X: (Voiture(X) => exists Y: (Possede(Y,X) && forall Z: (Possede(Z,X) => ==(Z,Y))))");
//        FolFormula b = (FolFormula)parser.parseFormula("exists X: (Etudiant(X) && exists Y: (Voiture(Y) && Possede(X, Y)))");
//        FolFormula c = (FolFormula)parser.parseFormula("exists X : (Etudiant(X) && forall Y : (Voiture(Y) => !Possede(X,Y)))");
//        FolFormula A = (FolFormula)parser.parseFormula("Voiture(A)");
//
//        bs.add(a, b, c,A);
//        System.out.println("\nParsed BeliefBase: " + bs);
//
//
//        /*
//         * Example 3: Use one of the provers to check whether various formulas can be inferred from the knowledge base parsed in Example 2.
//         */
//        FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); //Set default prover, options are NaiveProver, EProver, Prover9
//        FolReasoner prover = FolReasoner.getDefaultReasoner();
////        System.out.println("ANSWER 1: " + prover.query(bs, (FolFormula)parser.parseFormula("Voiture(A) => exists Y: (Possede(Y,A) && forall Z: (Possede(Z,A) => ==(Z, Y)))")));
//        System.out.println("ANSWER 1: " + prover.query(bs, (FolFormula)parser.parseFormula("exists X: (Possede(X, A))")));
        FolSignature sig = new FolSignature(true);

        //Add sort
        Sort sortEtudiant = new Sort("etudiant");
        Sort sortVoiture = new Sort("voiture");
        sig.add(sortEtudiant, sortVoiture);

        //Add constants
        Constant constantA = new Constant("a", Sort.ANY);
        Constant constantB = new Constant("b", Sort.ANY);
        sig.add(constantA, constantB);

        //Add predicates
        List<Sort> predicateList = new ArrayList<Sort>();
        predicateList.add(sortVoiture);
        Predicate p = new Predicate("voiture",predicateList);
        List<Sort> predicateList3 = new ArrayList<Sort>();
        predicateList3.add(sortEtudiant);
        Predicate p3 = new Predicate("etudiant",predicateList3);
        List<Sort> predicateList2 = new ArrayList<Sort>();
        predicateList2.add(sortEtudiant);
        predicateList2.add(sortVoiture);
        Predicate p2 = new Predicate("possede",predicateList2); //Add Predicate Knows(Animal,Animal)
        sig.add(p, p2,p3);
        System.out.println("Signature: " + sig);

        /*
         * Example 2: Parse formulas with FolParser using the signature defined above
         */
        FolParser parser = new FolParser();
        parser.setSignature(sig); //Use the signature defined above
        FolBeliefSet bs = new FolBeliefSet();
        FolFormula f1 = (FolFormula)parser.parseFormula("forall X: (voiture(X) => exists Y: (possede(Y,X) && forall Z: (possede(Z,X) => ==(Z,Y))))");
        FolFormula f2 = (FolFormula)parser.parseFormula("exists X: (etudiant(X) && exists Y: (voiture(Y) && possede(X, Y)))");
        FolFormula f3 = (FolFormula)parser.parseFormula("exists X : (etudiant(X) && forall Y : (voiture(Y) => !possede(X,Y)))");
        bs.add(f1, f2, f3);

        // 1 Evaluation de A :
        FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); //Set default prover, options are NaiveProver, EProver, Prover9
        FolReasoner prover = FolReasoner.getDefaultReasoner();
        FolBeliefSet bs1 = new FolBeliefSet();
        FolFormula f5 = (FolFormula)parser.parseFormula("voiture(a)");
        FolFormula f6 = (FolFormula)parser.parseFormula("!voiture(b)");
        bs1.add(f1, f2, f3,f5 ,f6);
        // X = A
        System.out.println("a-1. X = A: " + prover.query(bs1, (FolFormula)parser.parseFormula("voiture(a) => exists Y:(possede(Y,a) && forall Z: (possede(Z, a)=> ==(Z, Y)))")));
        // X= A et Z = A
        System.out.println("a-1.1. Z=A: " + prover.query(bs1, (FolFormula)parser.parseFormula("voiture(a) => exists Y:(possede(Y,a) && possede(a, a)=> ==(a, Y))")));
        // Z= B
        System.out.println("a-1.2. Z=B  : " + prover.query(bs1, (FolFormula)parser.parseFormula("voiture(a) => exists Y:(possede(Y,a) && possede(b, a)=> ==(b, Y))")));
        // X = B
        System.out.println("a-2. X=B: " + prover.query(bs1, (FolFormula)parser.parseFormula("voiture(b) => exists Y:(possede(Y,b) && forall Z: (possede(Z, b)=> ==(Z, Y)))")));

        // 2 Evaluation de B :
//        X = A
        System.out.println("b-1. X=A: " + prover.query(bs1, (FolFormula)parser.parseFormula("etudiant(a) && exists Y: (voiture(Y) && possede(a, Y))")));
//        X = B
        System.out.println("b-2. X=B: " + prover.query(bs1, (FolFormula)parser.parseFormula("etudiant(b) && exists Y: (voiture(Y) && possede(b, Y))")));

        // 3 Evaluation de C:
//        X=A
        System.out.println("c-1. X=A: " + prover.query(bs1, (FolFormula)parser.parseFormula("etudiant(a) && forall Y : (voiture(Y) => !possede(a,Y))")));
//        X = A y = A
        System.out.println("c-1.1. Y=A: " + prover.query(bs1, (FolFormula)parser.parseFormula("etudiant(a) && voiture(a) => !possede(a,a)")));
        // X = A y = B
        System.out.println("c-1.2. Y=B: " + prover.query(bs1, (FolFormula)parser.parseFormula("etudiant(a) && voiture(b) => !possede(a,b)")));


    }
}
