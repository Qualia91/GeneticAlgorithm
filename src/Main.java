import GA.GAGeneral;
import GA.StringPopulationItem;
import GA.Utils;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String targetString = "Try and get this one. And just a longer sentence.";

        StringBuilder example = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < targetString.length(); i++) {
            example.append(Utils.rndChar(random));
        }

        StringPopulationItem stringPopulationItem = new StringPopulationItem(example.toString(), targetString);

        GAGeneral stringVersion = new GAGeneral(1000, 0.001, stringPopulationItem, "");
        stringVersion.start();

        System.out.println(stringVersion.getRunCount());
        System.out.println(stringVersion.getGaRuntimeInformation().populationItem.getString());

    }
}
