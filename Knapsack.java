import java.util.Random;
import java.util.Arrays;
public class Oblig2
{
	public static void main(String[] args)
	{

		int numItems = 100;


		int[] cost = new int[numItems];
		initiateArray(cost, 1);
		int[] weight = new int[numItems];
		initiateArray(weight, 0);


		int[] knapsack = initialSolution(weight, numItems);
		System.out.println("Old weight: " + calculateWeight(knapsack, weight));
		System.out.println("Old cost: " + calculateCost(knapsack, cost));

		improvementMethod(knapsack, cost, weight);
		System.out.println("New weight: " + calculateWeight(knapsack, weight));
		System.out.println("New cost: " + calculateCost(knapsack, cost));


	public static int[] improvementMethod(int[] knapsack, int[] cost, int[] weight)
	{
		int oldCost = calculateCost(knapsack, cost);
		
		int numStolenItems = 0;
		int[] temp = new int[knapsack.length];


		for(int i = 0; i < knapsack.length; i++)
		{
			if(knapsack[i] != 0)
				numStolenItems++; //counts the number of stolen items

			temp[i] = knapsack[i]; //copy over same values
		}

		int newCost = 0;
		int maxWeight = 30;
		int counter = 0; //counter will reset if theres improvement and loop will exit if no improvement is made within ten iterations
		while(counter < 100)
		{
			int stolenItem = (int)(Math.random() * ( (numStolenItems-1)  + 1) + 0); //pick an item from knapsack
			int notStolenItem = (int)(Math.random() * ( (cost.length-1)  + 1) + 0); //pick an item that hasnt been stolen
			if(knapsack[notStolenItem] != 1)
			{
				temp[stolenItem] = 0;
				temp[notStolenItem] = 1;

				newCost = calculateCost(temp, cost);
				int currentWeight = calculateWeight(temp, weight);

				if(newCost > oldCost && currentWeight <= maxWeight)
				{
					for(int j = 0; j < knapsack.length; j++)
					{
						knapsack[j] = temp[j];
					}
					counter = 0;
				}
				else //result was not better
				{
					temp[stolenItem] = 1;
					temp[notStolenItem] = 0;
					counter++;
				}
			}
			
		}

		return knapsack;
	}
	public static int[] initialSolution(int[] weight, int numItems)
	{
		int[] knapsack = new int[numItems];
		Arrays.sort(weight);
		//initiates the knapsack to be empty
		for(int i = 0; i < knapsack.length; i++)
			knapsack[i]=0;


		int a = 0;
		int maxWeight = 30;
		int currentWeight = 0;
	
		while(currentWeight < maxWeight)
		{
			currentWeight = currentWeight + weight[a];
			if(currentWeight > maxWeight)
			{
				currentWeight = currentWeight - weight[a];
				break;
			}
			else
			{
				knapsack[a] = 1;
			}
			a++;
		}

		return knapsack;

	}

	public static int calculateWeight(int[] knapsack, int[] weight)
	{
		int totalWeight = 0;
		for(int i = 0; i < knapsack.length; i++)
		{
			if(knapsack[i]!=0)
			{
				totalWeight=totalWeight + weight[i];
			}
		}
		return totalWeight;	
	}

	public static int calculateCost(int[] knapsack, int[] cost)
	{
		int totalCost = 0;
		for(int i = 0; i < knapsack.length; i++)
		{
			if(knapsack[i]!=0)
			{
				totalCost=totalCost + cost[i];
			}
		}
		return totalCost;
	}
	
	public static int[] initiateArray(int[] array, int option)
	{
		int max, min; //max/min weight/cost, depending on option 

		if(option == 1) //kostnad
		{
			 max = 100;
			 min = 50;
		}
		else //vekt
		{
			max = 20;
			min = 5;
		}

		for(int i = 0; i < array.length; i++)
		{
			array[i] = (int)(Math.random() * ( max - min + 1) + min);
		}

		return array;
	}
}