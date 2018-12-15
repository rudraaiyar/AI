
import java.util.HashSet;
import java.util.Set;


/*
 * lecture 7
 * function GRAPH-SEARCH(problem) returns solution or failure
 *    initialize the frontier using the initial state of problem
 *    initialize the explored set to be empty 
 *    loop do
 *         if the frontier is empty then return failure
 *         choose a leaf node and remove it from the frontier 
 *         if the node contains a goal state
 *         	 then return the corresponding solution 
 *         add the node to the explored set
 *         expand the chosen node
 *         for each child in the children of the chosen node
 *         	 if child is not in the frontier or explored set then
 *         	 	add child to the frontier
 *         	else if child is in frontier but with a higher cost then 
 *         		remove the node with the higher cost from the frontier 
 *         		add child to the frontier
 *         
 */

public class UniformCostSearch {
	
	//all given in instr

	public StreetMap graph;
    public String initialLoc = "";
    public String destLoc = "";
    public int limit = 0;
    public int expansionCount = 0;

    public UniformCostSearch(StreetMap graph, String initialLoc, String destLoc, int limit) {
    	 this.graph = graph; 
         this.initialLoc = initialLoc;  
         this.destLoc = destLoc;  
         this.limit = limit; 
    }
    
    public Node search(boolean rsc) {
    	SortedFrontier sfrontier = new SortedFrontier(SortBy.g); //initialize the frontier using the initial state of problem
        Node node = new Node(graph.findLocation(initialLoc), null); //point node to initialLoc loc
        expansionCount = 0;
        Set<String> visited = new HashSet<>();	//initialize the visited set to be empty 
        Node nodeCost;	

            sfrontier.addSorted(node);	

            if (rsc == true) {	//true case
                while (!sfrontier.isEmpty()) { //loop do
                	
                	if (node.depth > limit -1) {
                		return (null);
                	}
                	
                    node = sfrontier.removeTop();	//choose a leaf node and remove it from the frontier
                    
                    if (node.isDestination(destLoc)) {	//if the node contains a goal state
                        return node;	//then return the corresponding solution 
                        
                    } 
                    	String nName = node.loc.name;

                        visited.add(nName);	//add the node to the explored set
                        node.expand();	//expand the chosen node
                        expansionCount++;	//expand the chosen node count
                        
                        for (Node child : node.children) { //for each child in the children of the chosen node
                        	
                        	String cName = child.loc.name;
                            if (!visited.contains(cName)) {//if child is not in the frontier or explored set then
                  
                                if (sfrontier.contains(child) == true) {
                                	
                                    nodeCost = sfrontier.find(child);
                                    if (child.partialPathCost < nodeCost.partialPathCost) {
                                    	
                                        sfrontier.remove(nodeCost);
                                        sfrontier.addSorted(child); //add child to the frontier

                                    }
                                } else {
                                	
                                    sfrontier.addSorted(child);	//add child to the frontier
                                }
                            }
                        }
                    
                }
                return null; //if the frontier is empty then return failure
                
            } else {	//false case
            	
                while (!sfrontier.isEmpty()) { //loop do
                	node = sfrontier.removeTop();	//choose a leaf node and remove it from the frontier

                	if (node.depth > limit -1) {
                		return (null);
                	}
                	
                    if (node.isDestination(destLoc)) {	//if the node contains a goal state
                        return node; //then return the corresponding solution 
                        
                    } else {	
                    	
                        node.expand();	 //expand the chosen node
                        
                        expansionCount++;	//expand the chosen node count

                        sfrontier.addSorted(node.children);	 //add child to the frontier

                    }
                }
                return null;	//if the frontier is empty then return failure
            }
        }
    
}
