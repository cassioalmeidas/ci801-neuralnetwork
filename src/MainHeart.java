import com.davisan.ia.DataSetHeart;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.TestarSaidaMLP;
import com.davisan.ia.Torneio;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MainHeart
{
    public static void main(String[] args)
    {
        try
        {
            int popsize = 50;
            int geracoes = 500;
            Torneio.TamTorneio = 4;
            GeneticAlgorithm.propMutacao = 0.1;
            GeneticAlgorithm.propCrossover = 0.9;
            MLPIndividual.dataset = new DataSetHeart("bases/heart.dat", 13, 2);
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 130;
            MLPIndividual.dataset.faixaValidacao[0] = 130;
            MLPIndividual.dataset.faixaValidacao[1] = 200;
            MLPIndividual.dataset.faixaTeste[0] = 200;
            MLPIndividual.dataset.faixaTeste[1] = 270;
            
            GeneticAlgorithm.Evolve(new MLPIndividual(13,5,2), popsize, geracoes);
            System.out.println("fim! " + GeneticAlgorithm.best.toString());
            MultiLayerPerceptron bestMLP = ((MLPIndividual)GeneticAlgorithm.best).createtMLP();
                        
            int difs = TestarSaidaMLP.Testar(bestMLP, MLPIndividual.dataset, MLPIndividual.dataset.faixaValidacao[0], MLPIndividual.dataset.faixaValidacao[1]);
            System.out.println("validacao diferentes = " + difs + " Acerto = " + (1 - 1.0*difs/(MLPIndividual.dataset.faixaValidacao[1]-MLPIndividual.dataset.faixaValidacao[0])));
            
            difs = TestarSaidaMLP.Testar(bestMLP, MLPIndividual.dataset, MLPIndividual.dataset.faixaTeste[0], MLPIndividual.dataset.faixaTeste[1]);
            System.out.println("teste diferentes = " + difs + " Acerto = " + (1 - 1.0*difs/(MLPIndividual.dataset.faixaTeste[1]-MLPIndividual.dataset.faixaTeste[0])));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
