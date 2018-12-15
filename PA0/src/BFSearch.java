
	
//https://en.wikipedia.org/wiki/Breadth-first_search
//https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/

	import java.util.HashSet;
	public class BFSearch 
	{
		public Map graph; //map object
		public String startingLocation;    //starting Location string
		public String destinationLocation; //destination Location string
		public int depthLimit; //depth depthLimit for search
		public int expansionCount; //contains number of bfsNode expansions on most recent call

		
		//constructor for arguments and assignments
		public BFSearch(Map graph, String startingLocation, String destinationLocation, int depthLimit) 
		{
			this.graph = graph;
			this.startingLocation = startingLocation;
			this.destinationLocation = destinationLocation;	
			this.depthLimit = depthLimit; 

		}
		//*****FIFO******
		//BFS with boolean argument binary which can either be true or false
		//**Do not add repeated states to the frontier!** which we can tell if it is true or false
	    public Node search (boolean binary) {
	        expansionCount = 0; 
	        Node bfsNode = new Node(graph.findLocation(startingLocation)); //set new bfsNode location to starting point

	        //if bfsNode is already at destination return bfsNode otherwise check
	        if (bfsNode.loc.name != destinationLocation){ 
	            Frontier currChild = new Frontier(); // new frontier for current nod child 
	            currChild.addToBottom(bfsNode); // add starting location bfsNode to currChild

	            // if the binary is true then remove first in bfsNode and find children of bfsNode (expansion++) until bfsNode is at final destination then return bfsNode
	            //if binary is false then return and remove first bfsNode, find children and return current bfsNode
	            //if true or false results in a failure return null 
	            if (binary == true) {
	            	//true case
	                HashSet<String> visited = new HashSet<>();// storing visited bfsNodes
	                while (!currChild.isEmpty() && bfsNode.depth < depthLimit - 1) {
	                    bfsNode = currChild.removeTop();   

	                    if (bfsNode.isDestination(destinationLocation)) {
	                        return bfsNode;  
	                    } else {
	                        visited.add(bfsNode.loc.name);    
	                        bfsNode.expand();  
	                        expansionCount++; //expand   

	                        for (Node i : bfsNode.children) {  
	                            if (!visited.contains(i.loc.name) && !currChild.contains(i)) {  // state check
	                                currChild.addToBottom(i);    // add new bfsNode into currChild to the bottom of the frontier list.
	                            }
	                        }
	                    }
	                }
	                return null; //exception case if it fails
	            } else {
	            	//false case
	                while (!currChild.isEmpty() && bfsNode.depth < depthLimit - 1) {
	                    bfsNode = currChild.removeTop();    
	                    if (bfsNode.isDestination(destinationLocation)) {
	                        return bfsNode;    
	                    } else {
	                        bfsNode.expand(); 
	                        expansionCount++;   
	                        currChild.addToBottom(bfsNode.children); // add all new expanded bfsNodes to currchild's bfsNodes
	                    }
	                }
	                return null;    //exception case if it fails
	            }
	        
	        } else {
	        	//if dest = to start then you're done so just return bfsNode
	        	return bfsNode; 
	        }
	    }
	}