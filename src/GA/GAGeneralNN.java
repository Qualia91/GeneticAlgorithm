package GA;

import java.util.Random;

public class GAGeneralNN {

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
    private double[] fitnesses;
    private int popIndex = 0;

    private GARuntimeInformation gaRuntimeInformation;

    private int runCount = 0;

    public GAGeneralNN(int populationSize, double mutationRate, PopulationItem exampleItem) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.population = createInitialPopulation(this.populationSize, exampleItem);
        this.fitnesses = new double[populationSize];
    }

    public double[][][] getNextWeights(Object helperObject) {
        fitnesses[popIndex] = population[popIndex].getFitness(helperObject);
        popIndex++;
        if (popIndex == populationSize) {
            popIndex = 0;
            // get Max Fitness
            double maxFitness = 0;
            for (int i = 0; i < fitnesses.length; i++) {
                if (Math.abs(fitnesses[i]) > maxFitness) {
                    maxFitness = Math.abs(fitnesses[i]);
                    fitnesses[popIndex] = 0;
                }
            }

            // create new population with old population
            // get new parents
            PopulationItem[] newPopulationList = new PopulationItem[population.length];
            for (int populationIndex = 0; populationIndex < population.length; populationIndex++) {

                PopulationItem parentOne = rejectionSampling(population, maxFitness * 2, r, helperObject);
                PopulationItem parentTwo = rejectionSampling(population, maxFitness * 2, r, helperObject);

                newPopulationList[populationIndex] = parentOne.proCreate(parentTwo, mutationRate, r);

            }

            population = newPopulationList;

            fitnesses[popIndex] = population[popIndex].getFitness(helperObject);

        }
        return ((WeightsPopulationItem) population[popIndex]).getWeights();
    }

    private PopulationItem[] createInitialPopulation(int populationSize, PopulationItem populationItem) {

        PopulationItem[] population = new PopulationItem[populationSize];

        for (int populationIndex = 0; populationIndex < populationSize; populationIndex++) {

            population[populationIndex] = populationItem.getRandomItem(r);

        }

        return population;

    }

    private PopulationItem rejectionSampling(PopulationItem[] population, double maxFitness, Random r, Object helperObject) {

        PopulationItem selectedItem = population[r.nextInt(population.length)];
        double randomFitness = r.nextDouble() * maxFitness;

        if ((maxFitness - selectedItem.getFitness(helperObject)) > randomFitness) {
            return selectedItem;
        }

        return rejectionSampling(population, maxFitness, r, helperObject);

    }

    public int getRunCount() {
        return runCount;
    }

    public GARuntimeInformation getGaRuntimeInformation() {
        return gaRuntimeInformation;
    }
}
