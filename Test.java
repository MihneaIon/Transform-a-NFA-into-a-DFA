
public class Test {

	public static void main(String[] args) {
		
		
		AFN mihnea = new AFN();
		
		AFD afd = TransformAFNtoAFD.execute(mihnea);
		
		afd.afisareInFisier();
		
		
		
		
		
		
		
		
//		AFD myAFD = Util.transformAFNinAFD(mihnea);
//		
//		myAFD.afisareInFisier();
//		
		
	}

}
