//package optimalisering;
import java.util.Random; 


public class Oblig1 {
	public static void main(String[]args)
	{

		int numCities = 500;

		int[][] matrix = generateMatrix(numCities);
	
		int[] route = iterativeMethod(numCities, matrix);

		System.out.println("\nCost before improvement is: " + calculateCost(route, matrix, numCities));
		
		int[] improvedRoute = greedyImprovement(route, matrix, numCities);
		
		System.out.println("Improved cost is: " + calculateCost(improvedRoute, matrix, numCities));


	}

	public static int[] greedyImprovement(int[] route, int[][] matrix, int numCities)
	{
		
		Random ran = new Random();
		int[] tempRoute = new int[route.length];

		//create a tempRoute table and initiate it with same values as route
		for(int i = 0; i < route.length; i++)
		{
			tempRoute[i] = route[i];
		}

		//create a variable that will be updated if the route is updated
		int notUpdated = 0;

		//loop attempts to find improved route, until the route has not been updated set number of times, now being 5000
		while(notUpdated < 5000)
		{
			//pick two random cities between 1 and numcities-1
			int city1 = ran.nextInt((numCities-1)-1+1)+1; 
			int city2 = ran.nextInt((numCities-1)-1+1)+1; 

			//swap the cities around
			int temp = tempRoute[city1];
			tempRoute[city1] = tempRoute[city2];
			tempRoute[city2] = temp;

			//calculate the cost of the route previously generated
			int cost = calculateCost(route, matrix, numCities);
			//calculate the cost of the new route with cities swapped
			int newCost = calculateCost(tempRoute, matrix, numCities);
			if(newCost < cost)
			{
				for(int k = 0; k < route.length; k++)
				{
					//if the results are better with the new route, we make our old route to become the new route
					route[k] = tempRoute[k];
				}
				//as the route is updated, we must update the variable that keeps track
				notUpdated=0;
			}
			else
				notUpdated++;

		}
		return route;
	}

	

	public static int[] greedyMethod(int numCities, int[][] matrix)
	{
		int[] route = new int[numCities+ 1];
		int[] visitedCities = new int[numCities];

		for(int j = 0; j < numCities; j++)
		{
			visitedCities[j] = 0; //no cities have been visited to start with
		}

		Random ran = new Random();
		int startingPoint = ran.nextInt(numCities);
		
		//Declare startingPoint city visited 
		route[0] = startingPoint;
		route[numCities] = startingPoint;

		visitedCities[startingPoint] = 1; //mark city as visited
		
		int currentCity = startingPoint; //create new variable for less confusion later in method
		int incrementer = 1; //route-index
		int nearestCity = 0; 

		while(checkIfAllIsVisited(visitedCities)!= 1) //runs until all cities are visited
		{
			int min = 11; //setting min to a larger value to get a starting point value

			for(int i = 0; i < numCities; i++) //iterates through column to find lowest value
			{

				if(matrix[currentCity][i] < min && matrix[currentCity][i] != 0 && visitedCities[i] != 1)
				{
					min = matrix[currentCity][i];
					nearestCity = i;
				}
			}
			visitedCities[nearestCity]=1; //mark city as visited
			route[incrementer] = nearestCity; //add city to route
			incrementer++; //increase route-index
			currentCity = nearestCity; //move on to the nearest city for next round

		}

		return route;
	}

	public static int[] iterativeMethod(int numCities, int[][] matrix)
	{
		int[] route = new int[numCities +1];
		int[] improvedRoute = new int[numCities+1];

		int[] visitedCities = new int[numCities];
		Random ran = new Random();

		int cost;
		int improvedCost=99999; //set very high


		int i = 0;

		while(i <= 40) //it will iterate a set number of times, usually we can set this to i < 5 which would provide similar results to 40. 
		{		
			for(int j = 0; j < numCities; j++)
			{
				visitedCities[j] = 0; //no cities have been visited to start with
			}

			int startingPoint = ran.nextInt(numCities);
			route[0] = startingPoint;
			route[numCities] = startingPoint;
			visitedCities[startingPoint] = 1;

			int j = 1; 
			while(checkIfAllIsVisited(visitedCities)!= 1)
			{
				int city = (int)(Math.random() * numCities) + 0;

				if(visitedCities[city] != 1)
				{
					route[j] = city;
					visitedCities[city] = 1;
					j++;
				}

			}
			cost = calculateCost(route, matrix, numCities);

			if(cost != 0 && cost < improvedCost) //will always set improvedRoute = route the first iteration
				{
					improvedCost = cost;
					for(int k = 0; k < route.length; k++)
					{
						improvedRoute[k] = route[k];
					}
				}

			i++;
		}


		return improvedRoute;
	}

	public static int[] randomMethod(int numCities)
	{
		int route[] = new int[numCities+1];
		
		/*Filling in incrementing values in route-array*/
		for(int i = 0; i < route.length; i++)
		{
			route[i] = i;
		}
		
		/*Shuffles the values in the array around to randomise the route*/
		for(int j = 0; j < route.length-1; j++) 
		{
			
			int city = (j) + (int)(Math.random() * ((route.length-1) - j));
			int temp = route[city];
			route[city] = route[j];
			route[j] = temp;
		}
		/*Making sure to return to the starting-point*/
		route[route.length-1] = route[0]; 
		return route;

	}
	
	/*Method that calculates the cost of the route*/
	public static int calculateCost(int[] route, int[][] matrix, int numCities)
	{
		int cost = 0;
		for(int i = 0; i < numCities; i++)
		{
			int city1 = route[i];
			int city2 = route[i+1];
			cost = cost + matrix[city1][city2];
		}
		return cost;
	}

	/*Method that will check if all the cities have been visited or not*/
	public static int checkIfAllIsVisited(int[] cities)
	{
		int counter = 0;
		for(int i = 0; i < cities.length; i++)
		{
			if(cities[i] == 1)
				counter++;
		}
		if(counter == cities.length)
			return 1; //returns 1 if all cities are visited
		else
			return 0; //returns zero if not all cities are visited
	}
	
	/*A printing method used for testing, to print the matrix created*/
	public static void printMatrix(int[][] matrix, int numCities)
	{
		for(int i = 0; i < numCities; i++)
		{
			for(int j = 0; j < numCities; j++)
			{
				if(j %numCities == 0)
					System.out.println("\n");
				System.out.print(matrix[i][j] + " ");
			}
		}
	}
	
	/*Method that generates a matrix with random values, from 1 to 10*/
	public static int[][] generateMatrix(int numCities)
	{
		Random ran = new Random();
		int[][] matrix = new int[numCities][numCities];
		for(int i = 0; i < numCities; i++)
		{
			
			for(int j = 0; j<numCities; j++)
			{
				if(i==j)
				{
					matrix[i][i]=0; //ex. Travelling from city A -> city A costs nothing
					
				}
				else
				{
					matrix[i][j]= ran.nextInt(10)+1;
					matrix[j][i] = matrix[i][j];
				}
			}
		}
		return matrix;
	}
}
