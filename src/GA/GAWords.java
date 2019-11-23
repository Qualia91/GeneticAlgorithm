package GA;

import java.util.Random;

import static GA.Utils.rndChar;

public class GAWords {

    public class GARuntimeInformation {

        GARuntimeInformation(String[] population, boolean finished) {
            this.population = population;
            this.finished = finished;
        }

        String[] population;
        boolean finished;
    }

    private final Random r = new Random();

    private final String targetString;
    private final int populationSize;
    private final double mutationRate;

    private String[] population;

    private GARuntimeInformation gaRuntimeInformation;

    private int runCount = 0;

    public GAWords(String targetString, int populationSize, double mutationRate) {
        this.targetString = targetString;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;

        this.population = createInitialPopulation(this.populationSize, this.targetString, r);


        while (true) {

            gaRuntimeInformation = run(this.targetString, this.population, this.mutationRate);

            if (gaRuntimeInformation.finished) {
                return;
            }

            this.population = gaRuntimeInformation.population;
            runCount++;

        }

    }

    private String createRandomWord(int wordLength, Random r) {

        StringBuilder word = new StringBuilder();

        for (int charIndex = 0; charIndex < wordLength; charIndex++) {

            word.append(rndChar(r));

        }

        return word.toString();

    }

    private String[] createInitialPopulation(int populationSize, String targetString, Random r) {

        String[] population = new String[populationSize];

        for (int populationIndex = 0; populationIndex < populationSize; populationIndex++) {

            population[populationIndex] = createRandomWord(targetString.length(), r);

        }

        return population;

    }

    public GARuntimeInformation run(String targetString, String[] population, double mutationRate) {

        String[][] fitnessLists = new String[targetString.toCharArray().length][population.length];

        // judge population
        for (int populationIndex = 0; populationIndex < population.length; populationIndex++) {

            int fitness = getFitness(population[populationIndex], targetString);

            if (fitness == targetString.length()) {
                return new GARuntimeInformation(population, true);
            }

            fitnessLists[fitness][populationIndex] = population[populationIndex];

        }

        for (int fitnessListIndex = 0; fitnessListIndex < fitnessLists.length; fitnessListIndex++) {

            fitnessLists[fitnessListIndex] = cullLists(fitnessLists[fitnessListIndex]);

        }

        // create new population with old population

        // create index list with more in for higher fitness
        int[] listIndexes = createWeightedList(targetString);

        // get new parents
        for (int populationIndex = 0; populationIndex < population.length; populationIndex++) {

            String parentOne = getParentString(fitnessLists, listIndexes);
            String parentTwo = getParentString(fitnessLists, listIndexes);

            population[populationIndex] = createNewWord(parentOne, parentTwo, mutationRate);

        }

        return new GARuntimeInformation(population, false);

    }

    private String[] getFitnessList(String[][] fitnessLists, int[] listIndexes) {

        String[] list = fitnessLists[listIndexes[r.nextInt(listIndexes.length - 1)] - 1];

        if (list.length == 0) {

            list = getFitnessList(fitnessLists, listIndexes);

        }

        return list;

    }

    private String getParentString(String[][] fitnessLists, int[] listIndexes) {

        String[] list = getFitnessList(fitnessLists, listIndexes);

        return list[r.nextInt(list.length)];

    }

    private int[] createWeightedList(String targetString) {

        int sum = 0;
        sum = sum(targetString.length(), sum);
        int[] listIndexes = new int[sum];

        int indexCount = 0;

        for (int targetStringIndex = 0; targetStringIndex < targetString.length(); targetStringIndex++) {

            for (int counter = targetString.length() - targetStringIndex; counter > 0; counter--) {

                listIndexes[indexCount] = targetString.length() - targetStringIndex;

                indexCount++;

            }

        }

        return listIndexes;

    }

    private String createNewWord(String parentOne, String parentTwo, double mutationRate) {

        int splitAtChar = r.nextInt(parentOne.length() - 1);

        StringBuilder sb = new StringBuilder();

        String stringPickingFrom = parentOne;

        for (int charIndex = 0; charIndex < parentOne.toCharArray().length; charIndex++) {

            if (charIndex == splitAtChar) {
                if (stringPickingFrom.equals(parentOne)) {
                    stringPickingFrom = parentTwo;
                } else {
                    stringPickingFrom = parentOne;
                }
            }

            if (r.nextDouble() < mutationRate) {
                sb.append(rndChar(r));
            } else {
                sb.append(stringPickingFrom.charAt(charIndex));
            }

        }

        return sb.toString();

    }

    private int sum(int number, int sum) {

        sum += number;

        number--;

        if (number != 0) {
            sum = sum(number, sum);
        }

        return sum;

    }

    private int factorial(int number, int sum) {

        sum *= number;

        number--;

        if (number != 0) {
            factorial(number, sum);
        }

        return sum;

    }

    private String[] cullLists(String[] list) {

        int numberOfNonNullValues = 0;

        int lastFilledIndex = 0;

        for (int listIndex = 0; listIndex < list.length; listIndex++) {

            if (list[listIndex] != null) {

                lastFilledIndex = numberOfNonNullValues;

                numberOfNonNullValues++;

                list[lastFilledIndex] = list[listIndex];

            }

        }

        String[] culledList = new String[numberOfNonNullValues];

        for (int culledListIndex = 0; culledListIndex < culledList.length; culledListIndex++) {

            culledList[culledListIndex] = list[culledListIndex];

        }

        return culledList;

    }

    private int getFitness(String string, String targetString) {

        char[] inputStringChars = string.toCharArray();
        char[] targetStringChars = targetString.toCharArray();

        int value = 0;

        for (int stringIndex = 0; stringIndex < inputStringChars.length; stringIndex++) {

            if (inputStringChars[stringIndex] == targetStringChars[stringIndex]) {
                value++;
            }

        }

        return value;

    }

    public int getRunCount() {
        return runCount;
    }
}
