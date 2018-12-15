//https://en.wikipedia.org/wiki/Depth-first_search
//https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/


import java.util.HashSet;

public class DFSearch {

	public Map graph; //map object
	public String startingLocation;    //starting Location string
	public String destinationLocation; //destination Location string
	public int depthLimit; //depth depthLimit for search
	public int expansionCount; //contains number of dfsNode expansions on most recent call


	//constructor for arguments and assignments
    public DFSearch(Map graph, String startingLocation, String destinationLocation, int depthLimit){
        this.graph = graph;
        this.startingLocation = startingLocation;
        this.destinationLocation = destinationLocation;
        this.depthLimit = depthLimit;
    }
    //****FILO*****
	//DFS with boolean argument binary which can either be true or false
    public  Node search(boolean binary) {
        expansionCount = 0; 
        Node dfsNode = new Node(graph.findLocation(startingLocation));  //set new dfsNode location to starting point

        if (dfsNode.loc.name != destinationLocation){
            
            Frontier currChild = new Frontier(); // new frontier for current nod child 
            currChild.addToBottom(dfsNode); // add parent dfsNode to the currChild
            
            
            // if the binary is true then remove last in dfsNode and find children of dfsNode (expansion++) until dfsNode is at final destination then return dfsNode
            //if binary is false then return and remove last in dfsNode, find children and return current dfsNode
            //if true or false results in a failure return null 
            if (binary == true) {
            	
            	//true case
                HashSet<String> visited = new HashSet<>();    // storing visited dfsNodes
                while (!currChild.isEmpty() && dfsNode.depth < depthLimit - 1) {
                    dfsNode = currChild.removeTop();   

                    if (dfsNode.isDestination(destinationLocation)) {
                        return dfsNode;   
                    } else {
                        visited.add(dfsNode.loc.name); 
                        dfsNode.expand(); 
                        expansionCount++;   // expand

                        for (Node i : dfsNode.children) {
                            if (!visited.contains(i.loc.name) && !currChild.contains(i)) {  // state check
                                currChild.addToTop(i);   // add new dfsNode to the front of currChild
                            }
                        }
                    }
                }
                return null;    // failed
            } else {
            	
            	//false case
                while (!currChild.isEmpty() && dfsNode.depth < depthLimit - 1) {
                    dfsNode = currChild.removeTop();    // return and remove the very top dfsNode in the currChild (FILO)
                    if (dfsNode.loc.name == destinationLocation) {
                        return dfsNode;    
                    } else {
                        dfsNode.expand();  
                        expansionCount++;   
                        currChild.addToTop(dfsNode.children);    // add all children dfsNode of current dfsNode to currChild
                    }
                }
                return null; // failed
            }
        } else {
        	return dfsNode;    // startingLocation = destinationLocation 
        }
    }
 }