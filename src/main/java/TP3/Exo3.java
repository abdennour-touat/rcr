package TP3;

import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.RelationalFormula;
import org.tweetyproject.logics.fol.syntax.FolFormula;
import org.tweetyproject.logics.fol.syntax.FolSignature;
import org.tweetyproject.logics.ml.parser.MlParser;
import org.tweetyproject.logics.ml.reasoner.AbstractMlReasoner;
import org.tweetyproject.logics.ml.reasoner.SPASSMlReasoner;
import org.tweetyproject.logics.ml.reasoner.SimpleMlReasoner;
import org.tweetyproject.logics.ml.syntax.MlBeliefSet;

import java.io.IOException;

public class Exo3 {
    public static void main(String[] args) throws IOException {
        MlBeliefSet bs = new MlBeliefSet();
        MlParser parser = new MlParser();
        FolSignature sig = new FolSignature();
        sig.add(new Predicate("p", 0));
        sig.add(new Predicate("q", 0));
        sig.add(new Predicate("r", 0));
        parser.setSignature(sig);
        bs.add((RelationalFormula) parser.parseFormula("!(<>(p))"));
        bs.add((RelationalFormula) parser.parseFormula("p || r"));
        bs.add((RelationalFormula) parser.parseFormula("!r || [](q && r)"));
        bs.add((RelationalFormula) parser.parseFormula("[](r && <>(p || q))"));
        bs.add((RelationalFormula) parser.parseFormula("!p && !q"));
        System.out.println("Modal knowledge base: " + bs);
        AbstractMlReasoner reasoner = new SimpleMlReasoner();
        System.out.println("[](!p)      " + reasoner.query(bs, (FolFormula) parser.parseFormula("[](!p)")));
        System.out.println("<>(q || r)  " + reasoner.query(bs, (FolFormula) parser.parseFormula("<>(q || r)")));
        System.out.println("p           " + reasoner.query(bs, (FolFormula) parser.parseFormula("p")));
        System.out.println("r           " + reasoner.query(bs, (FolFormula) parser.parseFormula("r")));
        System.out.println("[](r)       " + reasoner.query(bs, (FolFormula) parser.parseFormula("[](r)")));
        System.out.println("[](q)       " + reasoner.query(bs, (FolFormula) parser.parseFormula("[](q)")));
    }
}
