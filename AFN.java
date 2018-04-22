

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class AFN {

	private Vector<String> stari;
	private String sigma;
	private String stareInitiala;
	private Vector<String> stariFinale;
	private Vector<Tranzitii> delta;

	public AFN(Vector<String> stari, String sigma, String streInitiala, Vector<String> stariFinale, Vector<Tranzitii> delta) {
		this.stari = stari;
		this.sigma = sigma;
		this.stareInitiala = streInitiala;
		this.stariFinale = stariFinale;
		this.delta = delta;
	}

	public AFN() {
		stari = new Vector<>();
		stariFinale = new Vector<>();
		delta = new Vector<>();
	}

	public Vector<String> getStari() {
		return stari;
	}

	public void setStari(Vector<String> stari) {
		this.stari = stari;
	}

	public String getsigma() {
		return sigma;
	}

	public void setsigma(String sigma) {
		this.sigma = sigma;
	}

	public String getStreInitiala() {
		return stareInitiala;
	}

	public void setStreInitiala(String streInitiala) {
		this.stareInitiala = streInitiala;
	}

	public Vector<String> getStariFinale() {
		return stariFinale;
	}

	public void setStariFinale(Vector<String> stariFinale) {
		this.stariFinale = stariFinale;
	}

	public Vector<Tranzitii> getDelta() {
		return delta;
	}

	public void setDelta(Vector<Tranzitii> delta) {
		this.delta = delta;
	}

	public void citire() {
		try {
		
			File myFile = new File("src/AFN.txt");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(myFile);
			
			String linie = scanner.nextLine();
			String[] adaugare = linie.split(",");
			for(String stare : adaugare)
				stari.add(stare);
			
			sigma = scanner.nextLine();
		
			linie = scanner.nextLine();
			adaugare = linie.split(",");
			for(String stare : adaugare)
				stariFinale.add(stare);
			
			stareInitiala = scanner.nextLine();
			
			while (scanner.hasNextLine()) {
				linie = scanner.nextLine();
				String[] split = linie.split(",");
				delta.add(new Tranzitii(split[0], split[1], split[2]));
			}
		} catch (Exception e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	public String toString() 
	{
		return "Q:" + stari.toString() + ", alfabet: " + sigma + ", q0: " + stareInitiala  + ", stari finale: " +  stariFinale.toString() + "\n" + "tranzitii:\n" + delta.toString();
}

	public Vector<String> selectareStare(Vector<String> aux, String sir) {
		Vector<String> stariposibile = new Vector<>();

		for (String stare : aux) {
			for (Tranzitii tranzitie : delta) {
				if (tranzitie.getStanga().equals(stare) && tranzitie.getSimbol().equals(sir)) {
					stariposibile.add(tranzitie.getDreapta());
				}
			}
		}
		return stariposibile;
	}

	public void start() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nInsert the word: ");
		String sir = scanner.nextLine();
		if (sir.matches("lambda") == true)
		{
			if(stariFinale.contains(stareInitiala))
				System.out.println("\nAutomatul accepta cuvantul lambda.");
			else
				System.out.println("\nAutomatul NU accepta cuvantul lambda.");
		} 
		else 
		{
			Vector<String> stareCurenta = new Vector<>();
			stareCurenta.add(stareInitiala);
			boolean ok = false;
			if (verificCuvantIntrodus(sir)==true) {
				while (ok == false && sir.matches("") == false) 
				{
					stareCurenta = selectareStare(stareCurenta, sir.charAt(0) + "");
					if (stareCurenta.size() == 0)
						ok = true;
					else
						sir = sir.replaceFirst(sir.charAt(0) + "", "");
				}
				if (sir.equals("") == false)
					System.out.println("\nBlocaj!");
				else if (verificareStareFinala(stareCurenta) == false)
					System.out.println("\nAutomatul NU accepta cuvantul.");
				else 
					System.out.println("\nAutomatul accepta cuvantul.");
				
			}
			else
				System.out.println("Cuvantul nu este bun!");
		}
	}

	public boolean verificCuvantIntrodus(String aux) {
		if (aux.matches("lambda")) {
			return true;
		}
		for (int i = 0; i < aux.length(); i++) {
			if (sigma.indexOf(aux.charAt(i)) == -1)
				return false;
		}
		return true;

	}

	public boolean verificareStareFinala(Vector<String> stari) {
		for (String aux : stari) {
			if (stariFinale.indexOf(aux) != -1)
				return true;
		}
		return false;
	}

}
