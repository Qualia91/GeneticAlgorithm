package GA;

import java.util.Random;

public class GAGeneral {

    public class GARuntimeInformation {

        GARuntimeInformation(PopulationItem[] population, boolean finished, PopulationItem populationItem) {
            this.population = population;
            this.finished = finished;
            this.populationItem = populationItem;
        }

        PopulationItem[] population;
        boolean finished;
        public PopulationItem populationItem;
    }

    private final Random r = new Random();

    private final int populationSize;
    private final double mutationRate;

    private PopulationItem[] population;

    private GARuntimeInformation gaRuntimeInformation;

    private final Object helperObject;

    private int runCount = 0;

    public GAGeneral(int populationSize, double mutationRate, PopulationItem exampleItem, Object helperObject) {
        this.helperObject = helperObject;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;

        this.population = createInitialPopulation(this.populationSize, exampleItem);

    }

    private PopulationItem[] createInitialPopulation(int populationSize, PopulationItem populationItem) {

        PopulationItem[] population = new PopulationItem[populationSize];

        for (int populationIndex = 0; populationIndex < populationSize; populationIndex++) {

            population[populationIndex] = populationItem.getRandomItem(r);

        }

        return population;

    }

    public void start() {

        while (true) {

            gaRuntimeInformation = run(this.population, this.mutationRate);

            if (gaRuntimeInformation.finished) {
                return;
            }

            this.population = gaRuntimeInformation.population;

            if (runCount % 100 == 0) {
                System.out.println(runCount);
                System.out.println(gaRuntimeInformation.populationItem.getString());
            }
            runCount++;

        }
    }

    public GARuntimeInformation run(PopulationItem[] population, double mutationRate) {

        PopulationItem[] newPopulationList = new PopulationItem[population.length];

        // get Max Fitness
        double maxFitness = 0;
        PopulationItem populationItem = null;
        for (int populationIndex = 0; populationIndex < population.length; populationIndex++) {

            if (population[populationIndex].getFitness(helperObject) > maxFitness) {
                maxFitness = population[populationIndex].getFitness(helperObject);
                populationItem = population[populationIndex];
            }

            if (population[populationIndex].isComplete()) {
                populationItem = population[populationIndex];
                return new GARuntimeInformation(population, true, populationItem);
            }

        }

        // create new population with old population
        // get new parents
        for (int populationIndex = 0; populationIndex < population.length; populationIndex++) {

            PopulationItem parentOne = rejectionSampling(population, maxFitness, r);
            PopulationItem parentTwo = rejectionSampling(population, maxFitness, r);

            newPopulationList[populationIndex] = parentOne.proCreate(parentTwo, mutationRate, r);

        }

        population = newPopulationList;

        return new GARuntimeInformation(population, false, populationItem);

    }

    private PopulationItem rejectionSampling(PopulationItem[] population, double maxFitness, Random r) {

        PopulationItem selectedItem = population[r.nextInt(population.length)];
        double randomFitness = r.nextDouble() * maxFitness;

        if (selectedItem.getFitness(helperObject) > randomFitness) {
            return selectedItem;
        }

        return rejectionSampling(population, maxFitness, r);

    }

    public int getRunCount() {
        return runCount;
    }

    public GARuntimeInformation getGaRuntimeInformation() {
        return gaRuntimeInformation;
    }
}
