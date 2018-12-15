//
// BackwardChain
//
// This class implements a backward chaining inference procedure. The
// implementation is very skeletal, and the resulting reasoning process is
// not particularly efficient. Knowledge is restricted to the form of
// definite clauses, grouped into a list of positive literals (facts) and
// a list of Horn clause implications (rules). The inference procedure
// maintains a list of goals. On each step, a proof is sought for the
// first goal in this list, starting by an attempt to unify the goal with
// any known fact in the knowledge base. If this fails, the rules are
// examined in the order in which they appear in the knowledge base, searching
// for a consequent that unifies with the goal. Upon successful unification,
// a proof is sought for the conjunction of the rule antecedents. If this
// fails, further rules are considered. Note that this is a strictly
// depth-first approach, so it is incomplete. Note, also, that there is
// no backtracking with regard to matches to facts -- the first match is
// always taken and other potential matches are ignored. This can make
// the algorithm incomplete in another way. In short, the order in which
// facts and rules appear in the knowledge base can have a large influence
// on the behavior of this inference procedure.
//
// In order to use this inference engine, the knowledge base must be
// initialized by a call to "initKB". Queries are then submitted using the
// "ask" method. The "ask" function returns a binding list which includes
// bindings for intermediate variables.
//
// David Noelle -- Tue Oct  9 18:48:57 PDT 2018
//


import java.util.*;


public class BackwardChain {

    public KnowledgeBase kb;

	// Default constructor ...
	public BackwardChain() {
		this.kb = new KnowledgeBase();
	}

	// initKB -- Initialize the knowledge base by interactively requesting
	// file names and reading those files. Return false on error.
	public boolean initKB() {
		return (kb.readKB());
	}
    
	
	
	
	
	//for the literal case and the function case — should be relatively compact
	//an empty binding list is the appropriate return value for unifyin that case.
	
	
    /*
    This method attempts to find a binding list that allows for the unification 
    of the two provided literals, under the constraints of the given binding list. 
    If the two literals can be unified, anew binding list is returned, augmenting
     the given binding list with any additional bindings needed to unify the two literals. 
     If unification is not possible, this method should return“null”.
     */
    

	// unify -- Return the most general unifier for the two provided literals,
	// or null if no unification is possible. The returned binding list
	// should be freshly allocated.
	public BindingList unify(Literal lit1, Literal lit2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!
		if (lit1.pred.equals(lit2.pred)) { // both literals are equal
			
    		return unify(lit1.args, lit2.args, bl); //able to unify
    		
    	}

		return (null); //otherwise if we cant unify return null
	}
    
    /*
     This method attempts to find a binding list that allows for the unification 
     of the two provided terms (which can be constants, variables, or functions),
      under the constraints of the given binding list. If the two terms can be unified, 
      a new binding list is returned, augmenting the given binding list with any 
      additional bindings needed to unify the two terms. If unification is not 
      possible, this method should return “null”.
     */
	

	// unify -- Return the most general unifier for the two provided terms,
	// or null if no unification is possible. The returned binding list
	// should be freshly allocated.
	public BindingList unify(Term t1, Term t2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!		
	
		/*
		 * possible combinations for term
		 *  constant constant
		 *  constant variable
		 *  constant function
		 *   
		 *  variable constant
		 *  variable variable 
		 *  variable function
		 *  
		 *  function constant 
		 *  function variable
		 *  function function
		 */
		
		//MAKE SURE EVERY COMBO ISNT NULL
		
		//Erick Corona helped explain this to me further by explaining the differences of term and constant 
		
		//**************** T1 CONSTANT ******************//
    		if(t1.c != null && t2.c != null && t1.equals(t2)){ //t1 and t2 are constants and t1=t2
    			return new BindingList(bl); //can be unified but already same type so return new binding list
        	}
        	else if ( t1.c != null && t2.v != null) { // t1 is constant and t2 is a variable
        		return unify(t2, t1, bl); //a constant and a variable CAN be unified
    		}
        	else if ( t1.c != null && t2.f != null){ //t1 is constant and t2 is a function
        		return null; //constant and a function CANT be unified
        	}
    	
    	
		//**************** T1 VARIABLE ******************//
    		
    		if(t1.v != null && t2.c != null){ //t1 is a variable and t2 is a constant
    			bl.addBinding(t1.v, t2); //a variable and a constant can bound 
    			return new BindingList(bl); //return binding list
        	}
        	else if (t1.v != null && t2.v != null) {//t1 is a variable and t2 is a variable
        		bl.addBinding(t1.v, t2); //a variable and a variable can bound 
    			return new BindingList(bl);	//return binding list
    		}
        	else if(t1.v != null && t2.f != null && !(t2.subst(bl).allVariables().contains(t1.v))){//t1 is a variable and t2 is a function
        		// subst -- Return a new Term object that is the result of applying the given binding list to this term. 
        		// so we want to return a new term that takes in the bindinglist of a set of all the variables in this term that have the first term as a variable. 
        		bl.addBinding(t1.v, t2);//a variable and a function can bound 
    			return new BindingList(bl);//return binding list
    			
        	}
    	
		//**************** T1 FUNCTION ******************//

    
    		if(t1.f != null && t2.c != null){ //t1 is a function and t2 is a constant
    			return null; //can't do much with a function and constant (pdf)
        	}
        	else if (t1.f != null && t2.v != null) { //t1 is a function and t2 is a variable
        		return unify(t2, t1, bl);//a function and a variable CAN be unified
    		}
        	else if (t1.f != null && t2.f != null) { //t1 is a function and t2 is a function
        		return unify(t1, t2, bl);//can be unified same type
        	}

		return null; // no case so return null
	}
    
    /*
     This method attempts to find a binding list that allows for the unification 
     of the two provided functions, under the constraints of the given binding list.
     If the two functions can be unified,a new binding list is returned, augmenting 
     the given binding list with any additional bindings needed to unify the two functions.
      If unification is not possible, this method should return“null"
     */

	// unify -- Return the most general unifier for the two provided functions,
	// or null if no unification is possible. The returned binding list
	// should be freshly allocated.
	public BindingList unify(Function f1, Function f2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!
		if(f1.func.equals(f2.func)) { //both functions are equal 
			
			return unify(f1.args, f2.args, bl); //able to unify
			
		}

		return null; //otherwise if we cant unify return null
	}

	// unify -- Return the most general unifier for the two provided lists of
	// terms, or null if no unification is possible. The returned binding
	// list should be freshly allocated.
	public BindingList unify(List<Term> ts1, List<Term> ts2, BindingList bl) {
		if (bl == null)
			return (null);
		if ((ts1.size() == 0) && (ts2.size() == 0))
			// Empty lists match other empty lists ...
			return (new BindingList(bl));
		if ((ts1.size() == 0) || (ts2.size() == 0))
			// Ran out of arguments in one list before reaching the
			// end of the other list, which means the two argument lists
			// can't match ...
			return (null);
		List<Term> terms1 = new LinkedList<Term>();
		List<Term> terms2 = new LinkedList<Term>();
		terms1.addAll(ts1);
		terms2.addAll(ts2);
		Term t1 = terms1.get(0);
		Term t2 = terms2.get(0);
		terms1.remove(0);
		terms2.remove(0);
		return (unify(terms1, terms2, unify(t1, t2, bl)));
	}

	// askFacts -- Examine all of the facts in the knowledge base to
	// determine if any of them unify with the given literal, under the
	// given binding list. If a unification is found, return the
	// corresponding most general unifier. If none is found, return null
	// to indicate failure.
	BindingList askFacts(Literal lit, BindingList bl) {
		BindingList mgu = null; // Most General Unifier
		for (Literal fact : kb.facts) {
			mgu = unify(lit, fact, bl);
			if (mgu != null)
				return (mgu);
		}
		return (null);
	}

	// askFacts -- Examine all of the facts in the knowledge base to
	// determine if any of them unify with the given literal. If a
	// unification is found, return the corresponding most general unifier.
	// If none is found, return null to indicate failure.
	BindingList askFacts(Literal lit) {
		return (askFacts(lit, new BindingList()));
	}

	// ask -- Try to prove the given goal literal, under the constraints of
	// the given binding list, using both the list of known facts and the
	// collection of known rules. Terminate as soon as a proof is found,
	// returning the resulting binding list for that proof. Return null if
	// no proof can be found. The returned binding list should be freshly
	// allocated.
	BindingList ask(Literal goal, BindingList bl) {
		BindingList result = askFacts(goal, bl);
		if (result != null) {
			// The literal can be unified with a known fact ...
			return (result);
		}
		// Need to look at rules ...
		for (Rule candidateRule : kb.rules) {
			if (candidateRule.consequent.pred.equals(goal.pred)) {
				// The rule head uses the same predicate as the goal ...
				// Standardize apart ...
				Rule r = candidateRule.standardizeApart();
				// Check to see if the consequent unifies with the goal ...
				result = unify(goal, r.consequent, bl);
				if (result != null) {
					// This rule might be part of a proof, if we can prove
					// the rule's antecedents ...
					result = ask(r.antecedents, result);
					if (result != null) {
						// The antecedents have been proven, so the goal
						// is proven ...
						return (result);
					}
				}
			}
		}
		// No rule that matches has antecedents that can be proven. Thus,
		// the search fails ...
		return (null);
	}

	// ask -- Try to prove the given goal literal using both the list of
	// known facts and the collection of known rules. Terminate as soon as
	// a proof is found, returning the resulting binding list for that proof.
	// Return null if no proof can be found. The returned binding list
	// should be freshly allocated.
	BindingList ask(Literal goal) {
		return (ask(goal, new BindingList()));
	}

	// ask -- Try to prove the given list of goal literals, under the
	// constraints of the given binding list, using both the list of known
	// facts and the collection of known rules. Terminate as soon as a proof
	// is found, returning the resulting binding list for that proof. Return
	// null if no proof can be found. The returned binding list should be
	// freshly allocated.
	BindingList ask(List<Literal> goals, BindingList bl) {
		if (goals.size() == 0) {
			// All goals have been satisfied ...
			return (bl);
		} else {
			List<Literal> newGoals = new LinkedList<Literal>();
			newGoals.addAll(goals);
			Literal goal = newGoals.get(0);
			newGoals.remove(0);
			BindingList firstBL = ask(goal, bl);
			if (firstBL == null) {
				// Failure to prove one of the goals ...
				return (null);
			} else {
				// Try to prove the remaining goals ...
				return (ask(newGoals, firstBL));
			}
		}
	}

}
