import java.util.HashSet;

/* 
     * from lecture 7
     * 
     * function GRAPH-SEARCH(problem) returns solution or failure   
     * initialize the frontier using the initial state of problem
     * initialize the visited set to be empty 
     * loop do
     *      if the frontier is empty then return failure
     *      choose a leaf node and remove it from the frontier
     *      if the node contains a goal state 
     *      	then return the corresponding solution 
     *      add the node to the visited set  expand the chosen node
     *      for each child in the children of the chosen node 
     *      	if child is not in the frontier or visited set
     *      		then add child to the frontier 
     *      	
     *      
     *      ****note we dont have cost comparison
     */
    
//all comments are based off above pseudo so it may sound a bit repetitive
//only knows future 
//can only see hn but having the smallest gn doesnt nesc mean that they can see the hn

//all given in instr

public class GreedySearch {
    public StreetMap graph;
    public int limit;
    public String initialLoc;
    public String destLoc;
    public int expansionCount =0;

    public GreedySearch(StreetMap graph, String initialLoc, String destLoc, int limit)
    {
		this.graph = graph;
		this.initialLoc = initialLoc;
		this.destLoc = destLoc;
		this.limit = limit;
    }

    public Node search(boolean rsc)
    {		
    	Location startLoc = graph.findLocation(initialLoc);
    	Location endLoc = graph.findLocation(destLoc);
        Node node = new Node(graph.findLocation(initialLoc));
        SortedFrontier sfrontier = new SortedFrontier(SortBy.h); //initialize the frontier using the initial state of problem
        HashSet<String> visited = new HashSet<>();//initialize the visited set to be empty 
        GoodHeuristic h = new GoodHeuristic(startLoc, endLoc, graph);
        expansionCount = 0;
        
        
        sfrontier.addSorted(node);

        if(rsc == true) {//true case
       	 while(!sfrontier.isEmpty()) { //loop do
       		 
       		 if(node.depth > limit -1) {
       			 return null;
       		 }
       		 
                node = sfrontier.removeTop(); //choose a leaf node and remove it from the frontier
                
                if(node.isDestination(destLoc)) { // if the node contains a goal state
                    return node; //then return the corresponding solution
                }
                String nName= node.loc.name;
                node.expand(h); //expand the chosen node
                expansionCount++; //expand the chosen node count
                visited.add(nName); //add the node to the visited set
                
                for(Node child : node.children) { //for each child in the children of the chosen node 
               	 	 String cName= child.loc.name;
               	 
	               	 if(!(visited.contains(cName) || sfrontier.contains(child))) { //if child is not in the frontier or visited set
	               		 sfrontier.addSorted(child); //then add child to the frontier 
	               		 
	               	 }
                 } 
             }	
        }else {//false case
        	while(!sfrontier.isEmpty()) { //loop do
        		 
        		 if(node.depth > limit -1) {
        			 return null;
        		 }
        		 
                 node = sfrontier.removeTop(); //choose a leaf node and remove it from the frontier

                 
                 if(node.isDestination(destLoc)) { // if the node contains a goal state
                     return node; //then return the corresponding solution
                 }       
                 
                 node.expand(h); //expand the chosen node
                 expansionCount++; //expand the chosen node count
                 sfrontier.addSorted(node.children); // add the node to the visited set 
      
             }	
        }
        return null; //if the frontier is empty then return failure
    }
}