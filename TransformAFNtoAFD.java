import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

public class TransformAFNtoAFD {
	
	public static AFD execute(AFN myAFN)
	{
		String initialState = myAFN.getStreInitiala();
		
		String sigma = myAFN.getsigma();
		
		Vector<String> Q = findOutQ(initialState,sigma,myAFN.getDelta());
		
		Vector<String> F = findOutF(Q, myAFN.getStariFinale());
		
		Vector<Tranzitii> delta = findOutDelta(Q, myAFN.getDelta(),sigma);

		return new AFD(Q, sigma, initialState , F, delta );
	}
	
	private static Vector<String> findOutQ(String initialState, String sigma, Vector<Tranzitii> delta)
	{
		Vector<String> Q = new Vector<>();
		
		HashSet<TreeSet<String>> newStates = new HashSet<>();
		
		TreeSet<String> firstState = new TreeSet<>();
		firstState.add(initialState);
		
		newStates.add(firstState);
		
		newStates = table(sigma, delta, newStates);
		for(TreeSet<String> set : newStates)
		{
			String state="";
			for(int i=0;i<set.size()-1;i++)
				state = state+set.toArray()[i]+",";
			state = state + set.toArray()[set.size()-1];
			Q.add(state);
		}
		Q.sort(new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				
				if(o1.length()<o2.length())
					return -1;
				else if(o1.length()>o2.length())
					return 1;
				else if(o1.compareTo(o2)<0)
					return -1;
				else if(o1.compareTo(o2)>0)
					return 1;
				return 0;
			}});
		
		
		return Q;
	}
	
	private static HashSet<TreeSet<String>> table(String sigma, Vector<Tranzitii> delta, HashSet<TreeSet<String>> newStates)
	{
		HashSet<TreeSet<String>> aux = new HashSet<>();
		for(TreeSet<String> set : newStates)
		{
			for(char c : sigma.toCharArray())
			{
				TreeSet<String> luki = returnPossibleStates(delta, set, c+"");
				aux.add(luki);
			}
		}
		
		boolean ok =false;
		for(TreeSet<String> set : aux)
			if(newStates.contains(set)==false)
			{
				newStates.add(set);
				ok = true;
			}
			
		
		if(ok==false)
			return newStates;
		else 
			return table(sigma, delta, newStates);
		
		
	}
	
	private static TreeSet<String> returnPossibleStates(Vector<Tranzitii> delta, TreeSet<String> currentStates, String symbol)
	{
		TreeSet<String> possibleStates = new TreeSet<>();
		for(String state : currentStates) {
			
			for(Tranzitii transition : delta)
			{
				if(transition.getStanga().equals(state) && transition.getSimbol().equals(symbol+""))
				
					possibleStates.add(transition.getDreapta());
				
			}
		
		}
		return possibleStates;
	}

	private static Vector<String> findOutF(Vector<String> Q, Vector<String> FOld)
	{
		Vector<String> F = new Vector<>();
		for(String qState : Q)
			for(String fState : FOld)
				if(qState.contains(fState))
					F.add(qState);
		return F;
	}
	
	private static Vector<Tranzitii> findOutDelta(Vector<String> Q, Vector<Tranzitii> deltaOld, String sigma)
	{
		Vector<Tranzitii> delta = new Vector<>();
		
		for(String state : Q)
		{
			TreeSet<String> currentStates = new TreeSet<>();
			if(state.indexOf(',')==-1)
				currentStates.add(state);
			else
			{
				String[] array = state.split(",");
				for(String st: array)
					currentStates.add(st);
			}
			
			for(char c : sigma.toCharArray())
			{
				TreeSet<String> states = returnPossibleStates(deltaOld, currentStates, c+"");
				String right = "";
				for(int i=0;i<states.size()-1;i++)
					right = right+states.toArray()[i] + ",";
				right = right + states.toArray()[states.size()-1] ;
				delta.add(new Tranzitii(state,right,c+""));
			}
		}
		return delta;
	}

}
