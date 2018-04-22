import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class AFD {
	private Vector<String> multimeFinita; // multimea starilor
	private String alfabet;
	private Vector<String> stariFinale;
	private String stareInitiala;
	private Vector<Tranzitii> lista;

	public AFD() {
		multimeFinita = new Vector<>();
		stariFinale = new Vector<>();
		lista = new Vector<>();
	}
	
	public AFD(Vector<String> multimeFinita, String alfabet, String stareInitiala, Vector<String> stariFinale, Vector<Tranzitii> lista) {
		this.multimeFinita = multimeFinita;
		this.alfabet = alfabet;
		this.stareInitiala = stareInitiala;
		this.stariFinale = stariFinale;
		this.lista = lista;
	}

	
	public Vector<String> getMultimeFinita() {
		return multimeFinita;
	}

	public void setMultimeFinita(Vector<String> multimeFinita) {
		this.multimeFinita = multimeFinita;
	}

	public String getAlfabet() {
		return alfabet;
	}

	public void setAlfabet(String alfabet) {
		this.alfabet = alfabet;
	}

	public Vector<String> getStariFinale() {
		return stariFinale;
	}

	public void setStariFinale(Vector<String> stariFinale) {
		this.stariFinale = stariFinale;
	}

	public String getStareInitiala() {
		return stareInitiala;
	}

	public void setStareInitiala(String stareInitiala) {
		this.stareInitiala = stareInitiala;
	}

	public String toString() {
		return "(" + multimeFinita + alfabet + stareInitiala + stariFinale + lista + ")";
	}

	public Tranzitii selectareTranzitie(String aux, String sir) {
		Vector<Tranzitii> tranzitiiAplicabile = new Vector<Tranzitii>();

		for (Tranzitii index : lista) {
			if (aux.contains(index.getStanga()))
				tranzitiiAplicabile.add(index);
		}

		if (tranzitiiAplicabile.size() > 0) {

			for (Tranzitii curent : tranzitiiAplicabile) {
				if (aux.contains(curent.getStanga()) && (curent.getSimbol().matches(primaLitera(sir)))) {
					System.out.println(" regaula gasita ");
					return curent;
				}
			}

			for (Tranzitii curent : tranzitiiAplicabile) {
				if (!(curent.getSimbol().matches(primaLitera(sir))) && aux.contains(curent.getStanga())) {
					System.out.println(" s-a identificat un blocaj ");
					return null;
				}
			}

		} else {
			return null;
		}
		return null;
	}

	public void start() {
		Scanner scanner = new Scanner(System.in);
		String start = stareInitiala;
		System.out.println(" insert the word ");
		String sir = scanner.nextLine();
		if (sir.matches("lambda") == true && eCuvantAcceptat(start)) {
			System.out.println(" Automatul accepta cuvantul ");
		} else {
			boolean ok = false;
			char veriifcDacaAmIntratCelPutinOData = ' ';
			if (verificCuvantIntrodus(sir)) {
				while (ok == false && sir.matches("") == false) {
					Tranzitii curenta = selectareTranzitie(start, sir);
					if (curenta == null) {
						System.out.println(" am ajuns la final ");
						ok = true;
					} else {
						curenta.toString();
						System.out.println("am intrat");
						start = start.replaceFirst(start, curenta.getDreapta());
						String sir1 = sir.substring(0, 1);
						sir = sir.replaceFirst(sir1, "");
						System.out.println(" sirul " + sir + ",  simbolul actual " + start);
						veriifcDacaAmIntratCelPutinOData = 't';
					}
				}

			} else {
				System.out.println(" s-a depistat o eroare ");
			}

			try {
				if (eCuvantAcceptat(start) && veriifcDacaAmIntratCelPutinOData == 't')
					System.out.println(" Automatul accepta cuvantul ");
				else
					System.out.println(" Automatul nu accepta cuvantul ");
			} catch (Exception e) {
				System.out.println(" Cuvantul introdus este gresit ");
			}
		}
	}

	public String primaLitera(String sir) {
		if (sir != "")
			return sir.charAt(0) + "";
		return null;

	}

	public boolean eCuvantAcceptat(String aux) {
		if (stariFinale.indexOf(aux) != -1)
			return true;
		else
			return false;
	}

	public boolean verificStaridefinitecorect(String aux) {
		for (int i = 0; i < aux.length(); i++)

			if (multimeFinita.indexOf(aux.charAt(i)) == -1)
				return false;
		return true;

	}

	public boolean verificCorectitudineaTranzitiilor(Tranzitii aux) {
		if (multimeFinita.contains(aux.getDreapta()) && multimeFinita.contains(aux.getStanga()))
			return true;
		return false;
	}

	public boolean verificCuvantIntrodus(String aux) {
		if (aux.matches("lambda")) {
			return true;
		}
		for (int i = 0; i < aux.length(); i++) {
			if (alfabet.indexOf(aux.charAt(i)) == -1)
				return false;
		}
		return true;
	}
	
	public void afisareInFisier()
	{
		try {
			PrintWriter pw =new PrintWriter("src/AFD.txt");
			pw.println(this);
			pw.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
