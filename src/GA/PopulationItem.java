package GA;

import java.util.Random;

public interface PopulationItem<T> {

    double getFitness(T t);

    PopulationItem proCreate(PopulationItem parentTwo, double mutationRate, Random r);

    PopulationItem getRandomItem(Random r);

    boolean isComplete();

    String getString();
}
