package GA;

import java.util.Random;

import static GA.Utils.numberOfItems;

public class WeightsPopulationItem<T> implements PopulationItem<T> {

    private final double[][][] weights;
    private FitnessFunction fitnessFunction;

    public WeightsPopulationItem(double[][][] weights, FitnessFunction fitnessFunction) {
        this.weights = weights;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public double getFitness(T t) {
        return fitnessFunction.run(t);
    }

    @Override
    public PopulationItem proCreate(PopulationItem parentTwo, double mutationRate, Random r) {

        int splitMatrixAtChar = r.nextInt(numberOfItems(weights) - 1);

        double[][][] weightsToPickFrom = weights;
        double[][][] returnWeights = Utils.copy(weights);

        int count = 0;
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {

                    if (count == splitMatrixAtChar) {
                        weightsToPickFrom = ((WeightsPopulationItem)parentTwo).weights;
                    }

                    if (r.nextDouble() < mutationRate) {
                        returnWeights[i][j][k] = r.nextDouble();
                    } else {
                        returnWeights[i][j][k] = weightsToPickFrom[i][j][k];
                    }

                    count++;
                }
            }
        }

        return new WeightsPopulationItem(returnWeights, fitnessFunction);
    }

    @Override
    public PopulationItem getRandomItem(Random r) {

        double[][][] returnWeights = Utils.copy(weights);

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {

                    returnWeights[i][j][k] = r.nextDouble();

                }
            }
        }

        return new WeightsPopulationItem(returnWeights, fitnessFunction);
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public String getString() {
        return "";
    }

    public double[][][] getWeights() {
        return weights;
    }

    public interface FitnessFunction<T>  {
        double run(T t);
    }
}
