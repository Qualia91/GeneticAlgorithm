package GA;

import java.util.Random;

import static GA.Utils.rndChar;

public class StringPopulationItem<T> implements PopulationItem<T> {

    private final String string;
    private final String targetPopulationString;
    private double fitness;

    public StringPopulationItem(String string, String targetPopulationString) {
        this.string = string;
        this.targetPopulationString = targetPopulationString;

        char[] inputStringChars = string.toCharArray();
        char[] targetStringChars = targetPopulationString.toCharArray();

        int value = 0;

        for (int stringIndex = 0; stringIndex < inputStringChars.length; stringIndex++) {

            if (inputStringChars[stringIndex] == targetStringChars[stringIndex]) {
                value++;
            }

        }

        fitness = (value + 0.01) * (value + 0.01);
    }

    @Override
    public double getFitness(T t) {
        return fitness;
    }

    @Override
    public PopulationItem proCreate(PopulationItem parentTwo, double mutationRate, Random r) {

        int splitAtChar = r.nextInt(string.length() - 1);

        StringBuilder sb = new StringBuilder();

        String stringPickingFrom = string;

        for (int charIndex = 0; charIndex < stringPickingFrom.toCharArray().length; charIndex++) {

            if (charIndex == splitAtChar) {
                if (stringPickingFrom.equals(string)) {
                    stringPickingFrom = ((StringPopulationItem)parentTwo).getString();
                } else {
                    stringPickingFrom = string;
                }
            }

            if (r.nextDouble() < mutationRate) {
                sb.append(rndChar(r));
            } else {
                sb.append(stringPickingFrom.charAt(charIndex));
            }

        }

        return new StringPopulationItem(sb.toString(), targetPopulationString);
    }

    @Override
    public PopulationItem getRandomItem(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            sb.append(Utils.rndChar(r));
        }
        return new StringPopulationItem(sb.toString(), targetPopulationString);
    }

    @Override
    public boolean isComplete() {
        return fitness >= (targetPopulationString.length() * targetPopulationString.length());
    }

    @Override
    public String getString() {
        return string;
    }
}
