//
// GoodHeuristic
//
// This class extends the Heuristic class, providing a reasonable
// implementation of the heuristic function method.  The provided "good"
// heuristic function is admissible.
//
// Rudra Aiyar -- 10/4/18
//

public class GoodHeuristic extends Heuristic {

    // YOU CAN ADD ANYTHING YOU LIKE TO THIS CLASS ... WHATEVER WOULD
    // ASSIST IN THE CALCULATION OF YOUR GOOD HEURISTIC VALUE.

    // heuristicFunction -- Return the appropriate heuristic values for the
    // given search tree node.  Note that the given Node should not be
    // modified within the body of this function.

	StreetMap graph; //bring in graph 
	
	double gSpeed ; //good speed

	public GoodHeuristic(Location initialLoc, Location destLoc, StreetMap graph)
	{
		this.graph = graph; //init graph

		setDestination(destLoc); //init destination from extension heur
		
		gSpeed = getSpeed(initialLoc, destLoc); //retrieve le speed
	}

	private double getSpeed(Location start, Location end)
	{
		
		
		double gSpeed = 0.0; //init
		
		for(Location loct : graph.locations) { //loop location
			
			for(Road road : loct.roads) { //loop roads
				
				double x = road.fromLocation.latitude - road.toLocation.latitude; //calc x
				double y = road.fromLocation.longitude - road.toLocation.longitude; //calc y
				double distanceCalc = Math.sqrt(Math.pow(x,2) + Math.pow(y,2)); //calc length =sqrt(x^2 + y^2)
				double speed = distanceCalc / road.cost; // length/cost =speed
				
				if (speed == 0) {
					return 0; //test calculation
				}else if(speed > gSpeed) {
					
					gSpeed = speed;
				}
			}
		}
		return gSpeed;
	}

	public double heuristicValue(Node thisNode)
	{
		//double hVal = 0.0;
		
		double x = thisNode.loc.latitude - destination.latitude; //calc x
		double y = thisNode.loc.longitude - destination.longitude; //calc y
		double distanceCalc = Math.sqrt(Math.pow(x,2) + Math.pow(y,2)); //calc length =sqrt(x^2 + y^2)
		
		double hVal = distanceCalc / gSpeed; 
		
		return (hVal); //yay
		
	}
}

