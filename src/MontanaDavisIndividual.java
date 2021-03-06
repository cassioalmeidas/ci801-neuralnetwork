import java.util.Random;

import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MontanaDavisIndividual extends MLPIndividual
{
    public static int mutationOperator = -1;
    public static int crossoverOperator = -1;
    
    @Override
    public String toString()
    {
        return "MontanaDavisIndividual [cromo=" + cromo + "]";
    }

    public MontanaDavisIndividual(int numEntrada, int numEscondida, int numSaida)
    {
        super(numEntrada, numEscondida, numSaida);
    }
    
    public MontanaDavisIndividual()
    {
        super();
    }

    public Individual novo()
    {
        MontanaDavisIndividual ind = new MontanaDavisIndividual();
        ind.cromo = new MLPChromossome(new MultiLayerPerceptron(cromo.params[0], cromo.params[1], cromo.params[2], new InicializacaoDistNormal(0,5)));
        return ind;
    }
    
    public MontanaDavisIndividual clone()
    {
        MontanaDavisIndividual ind = new MontanaDavisIndividual();
        ind.cromo = this.cromo.clone();
        return ind;
    }
    
    public void mutate()
    {
        try
        {
            int val = new Random().nextInt(4);
            
            if(mutationOperator != -1)
                val = mutationOperator;
            
            switch(val)
            {
            case 0:
                MontanaDavisOperators.mutationBiasedMutateWeights(this.cromo);
                break;
            case 1:
                MontanaDavisOperators.mutationUnbiasedMutateWeights(this.cromo);
                break;
            case 2:
                MontanaDavisOperators.mutationMutateNodes(this.cromo);
                break;
            case 3:
                MontanaDavisOperators.mutationMutateWeakestNodes(this.cromo);
                break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Individual[] crossOver(Individual other)
    {
        MLPIndividual p1 = this.clone();
        MLPIndividual p2 = ((MLPIndividual) other).clone();
        
        int val = new Random().nextInt(3);
        
        if(crossoverOperator!= -1)
            val = crossoverOperator;
        
        switch(val)
        {
        case 0:
            return MontanaDavisOperators.crossOverWeights(p1.cromo, p2.cromo);
        case 1:
            return MontanaDavisOperators.crossOverNodes(p1.cromo, p2.cromo);
        case 2:
            return MontanaDavisOperators.crossOverFeatures(p1.cromo, p2.cromo);
        }
        return MontanaDavisOperators.crossOverWeights(p1.cromo, p2.cromo);
    }
    /**/
}
