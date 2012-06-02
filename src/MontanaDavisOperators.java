import java.util.Random;

import com.davisan.ia.DataSetCancer;
import com.davisan.ia.DataSetGlass;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.core.DataSet;
import com.davisan.ia.core.FitnessMLP;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MontanaDavisOperators
{
    public static double desvp = 5.0;
    //public static DataSet dataset = null;

    
    /***       
     *   
     *   CROSSOVER-FEATURES: Different nodes in a neural network perform different roles. 
     *   For a  fully connected, layered network, the role which a given node can play depends only on which layer it is in and not on 
     *   its position in that  layer. In fact, we can exchange the role of  two nodes A and  B in the same layer of a network as  follows. 
     *   Loop over all  nodes C connected (by either an ingoing or outgoing  l i n k)  to A (and thus also to B). 
     *   Exchange the weight on the  link  between C and A wi th that on the link between C and B.  
     *   Ignoring the internal structure, the new network is  identical to the  old network, i.e. given the same inputs they  will  
     *   produce the same outputs.   
     */
    public static Individual[] crossOverFeatures(MLPChromossome p1, MLPChromossome p2)
    {
        MontanaDavisIndividual[] result = new MontanaDavisIndividual[2];
        MLPChromossome c1 = p1.clone();
        MLPChromossome c2 = p2.clone();
        
        int totalnodes = p1.params[1];
        for(int x = 0; x < totalnodes; ++x)
        {
            
        }
        
        
        result[0] = new MontanaDavisIndividual();
        result[1] = new MontanaDavisIndividual();
        result[0].cromo = c1;
        result[1].cromo = c2;
        
        return result;
    }
    
    public static Individual[] crossOverNodes(MLPChromossome p1, MLPChromossome p2)
    {
        MontanaDavisIndividual[] result = new MontanaDavisIndividual[2];
        MLPChromossome c1 = p1.clone();
        MLPChromossome c2 = p2.clone();
        
        
        int totalnodes = p1.params[1]+p1.params[2];
        for(int x = 0; x < totalnodes; ++x)
        {
            int base;
            
            if(GeneticAlgorithm.propCrossover < new Random().nextDouble())
                continue;
            
            MLPChromossome pai = (new Random().nextInt(2) == 0)? p1 : p2;
            if(x < p1.params[1])
            {
                base = x*(p1.params[0]+1);
                
                for(int i=base; i <= base+p1.params[0]; ++i)
                {
                    c1.genes[i] = pai.genes[i];
                }
            }
            else
            {
                int y = x - p1.params[1];
                base = (y*(p1.params[1]+1) + (p1.params[0]+1)*p1.params[1]);
                for(int i=base; i <= base+p1.params[1]; ++i)
                {
                    c1.genes[i] = pai.genes[i];
                }
            }           
        }
        
        for(int x = 0; x < totalnodes; ++x)
        {
            int base;
            
            if(GeneticAlgorithm.propCrossover < new Random().nextDouble())
                continue;
            
            MLPChromossome pai = (new Random().nextInt(2) == 0)? p1 : p2;
            if(x < p1.params[1])
            {
                base = x*(p1.params[0]+1);
                
                for(int i=base; i <= base+p1.params[0]; ++i)
                {
                    c2.genes[i] = pai.genes[i];
                }
            }
            else
            {
                int y = x - p1.params[1];
                base = (y*(p1.params[1]+1) + (p1.params[0]+1)*p1.params[1]);
                for(int i=base; i <= base+p1.params[1]; ++i)
                {
                    c2.genes[i] = pai.genes[i];
                }
            }           
        }
        
        
        result[0] = new MontanaDavisIndividual();
        result[1] = new MontanaDavisIndividual();
        result[0].cromo = c1;
        result[1].cromo = c2;
        
        return result;
    }
    
    public static Individual[] crossOverWeights(MLPChromossome p1, MLPChromossome p2)
    {
        MontanaDavisIndividual[] result = new MontanaDavisIndividual[2];
        MLPChromossome c1 = p1.clone();
        MLPChromossome c2 = p2.clone();
        
        Random rand = new Random();
        
        for(int i=0; i < c1.genes.length; ++i)
        {
            c1.genes[i] = (rand.nextInt(2) == 0)? p1.genes[i] : p2.genes[i];
        }
        for(int i=0; i < c1.genes.length; ++i)
        {
            c2.genes[i] = (rand.nextInt(2) == 0)? p1.genes[i] : p2.genes[i];
        }
       
        result[0] = new MontanaDavisIndividual();
        result[1] = new MontanaDavisIndividual();
        result[0].cromo = c1;
        result[1].cromo = c2;
        
        return result;
    }
    
    public static void mutationUnbiasedMutateWeights(MLPChromossome ind)
    {
        for(int i=0; i < ind.genes.length; ++i)
        {
            if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
            {
                ind.genes[i] = desvp *(new Random().nextGaussian());
            }
        }
        
    }
    
    public static void mutationBiasedMutateWeights(MLPChromossome ind)
    {
        for(int i=0; i < ind.genes.length; ++i)
        {
            if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
            {
                ind.genes[i] += desvp *(new Random().nextGaussian());
            }
        }
        
    }
    
    public static void mutationMutateNodes(MLPChromossome ind)
    {
        int n = 2;
        int baseindex = (ind.params[0]+1)*ind.params[1];
        for(int i=0; i < n; ++i)
        {
            int base = baseindex + new Random().nextInt(ind.params[2])*(ind.params[1]+1);
            for(int j = base; j <= base+ind.params[1]; ++j)
            {
                ind.genes[j] += desvp *(new Random().nextGaussian());
            }
        }
    }
    
    public static void mutationMutateWeakestNodes(MLPChromossome ind) throws Exception
    {        
        MLPChromossome weakest = null;
        double piorForca = Double.MIN_VALUE;
        int xbase = 0;
        
        double indfitness = FitnessMLP.fitness(ind.createMLPFromGenes(), MLPIndividual.dataset);
        
        int totalnodes = ind.params[1]+ind.params[2];
        for(int x = 0; x < totalnodes; ++x)
        {
            MLPChromossome novoCromo = ind.clone();
            int base;
            
            if(x < ind.params[1])
            {
                base = x*(ind.params[0]+1);
                
                for(int i=base; i <= base+ind.params[0]; ++i)
                {
                    novoCromo.genes[i] = 0;
                }
            }
            else
            {
                int y = x - ind.params[1];
                base = (y*(ind.params[1]+1) + (ind.params[0]+1)*ind.params[1]);
                for(int i=base; i <= base+ind.params[1]; ++i)
                {
                    novoCromo.genes[i] = 0;
                }
            }
            double fitness = FitnessMLP.fitness(novoCromo.createMLPFromGenes(), MLPIndividual.dataset);
            double forca = indfitness - fitness;
            if(weakest == null || Math.abs(forca) < Math.abs(piorForca))
            {
                piorForca = forca;
                weakest = novoCromo.clone();
                xbase = x;
            }
            
        }
        
        // aplica operador no pior n�
        if(xbase < ind.params[1])
        {
            int base = xbase*(ind.params[0]+1);
            
            // aplicar nas entradas
            for(int i=base; i <= base+ind.params[0]; ++i)
            {
                if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
                {
                    if(piorForca < 0)
                        ind.genes[i] = desvp *(new Random().nextGaussian());
                    else
                        ind.genes[i] += desvp *(new Random().nextGaussian());
                }
            }

            // aplicar nas saidas
            int offset = (ind.params[0]+1)*ind.params[1];
            int maxindex = ind.genes.length;
            for(int i = base+offset; i < maxindex; i += ind.params[1]+1)
            {
                if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
                {
                    if(piorForca < 0)
                    ind.genes[i] = desvp *(new Random().nextGaussian());
                    else
                        ind.genes[i] += desvp *(new Random().nextGaussian());
                }
            }

        }
        else
        {
            int y = xbase - ind.params[1];
            int base = (y*(ind.params[1]+1) + (ind.params[0]+1)*ind.params[1]);
            for(int i=base; i <= base+ind.params[1]; ++i)
            {
                if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
                {
                    if(piorForca < 0)
                        ind.genes[i] = desvp *(new Random().nextGaussian());
                    else
                        ind.genes[i] += desvp *(new Random().nextGaussian());
                }
            }
        }
    }
    

    public static void main(String[] args)
    {
        try
        {
            MLPChromossome ind = new MLPChromossome(new MultiLayerPerceptron(9, 10, 2, new InicializacaoDistNormal(0, 5)));
            
            MLPIndividual.dataset = new DataSetCancer("bases/cancer.data", 9, 2);
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 350;
            MLPIndividual.dataset.faixaValidacao[0] = 350;
            MLPIndividual.dataset.faixaValidacao[1] = 525;
            MLPIndividual.dataset.faixaTeste[0] = 525;
            MLPIndividual.dataset.faixaTeste[1] = 699;
            
            /*
            dataset = new DataSetGlass("bases/glass.data", 9, 7);
            dataset.faixaTreinamento[0] = 0;
            dataset.faixaTreinamento[1] = 214;
            dataset.faixaValidacao[0] = 114;
            dataset.faixaValidacao[1] = 164;
            dataset.faixaTeste[0] = 164;
            dataset.faixaTeste[1] = 214;
            */
           
            mutationMutateWeakestNodes(ind);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
