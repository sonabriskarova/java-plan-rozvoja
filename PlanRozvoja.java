package sk.upjs.finalTerm2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PlanRozvoja {

	private List<Aktivita> aktivity;
	
	public PlanRozvoja() {
		this.aktivity = new ArrayList<Aktivita>();
	}
	
	public void pridaj(Aktivita aktivita) {
		this.aktivity.add(aktivita);
	}
	
	// statická metóda, ktorá z uvedeného súboru prečíta zoznam aktivít, pričom v každom riadku bude popis jednej aktivity.
	public static PlanRozvoja zoSuboru(String nazovSuboru) {
		File subor = new File(nazovSuboru);
		PlanRozvoja planRozvoja = new PlanRozvoja();
		try (Scanner sc = new Scanner(subor)) {
			sc.useDelimiter("\t");
			while(sc.hasNextLine()) {
				String popis = sc.nextLine();
				Aktivita aktivita = Aktivita.zoStringu(popis);
				planRozvoja.pridaj(aktivita);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("problem");
		}
		return planRozvoja;
	}
	
	// uloží všetky aktivity zo zoznamu aktivit do súboru v tvare, ktorý vie spracovať metóda zoSuboru(String nazovSuboru)
	public void uloz(String nazovSuboru)  {
		File subor = new File(nazovSuboru);
		try (PrintWriter pw = new PrintWriter(subor)) {
			for (final Aktivita aktivita : this.aktivity) {
				String p;
				if (aktivita.getAplikacia() == null) {
					p = aktivita.getNazov() + "\t" + aktivita.getDatum() + "\t" + aktivita.getCasZaciatku() + "\t" + aktivita.getDlzka() 
					+ "\t" + aktivita.getPridruzenie() + "\t" + aktivita.getTyp();
				} 
				else {
					p = aktivita.getNazov() + "\t" + aktivita.getDatum() + "\t" + aktivita.getCasZaciatku() + "\t" + aktivita.getDlzka() 
					+ "\t" + aktivita.getPridruzenie() + "\t" + aktivita.getTyp() + "\t" + aktivita.getAplikacia() + "\t" + aktivita.getDetail();
				}
				pw.println(p);
			} 
		} catch (FileNotFoundException e) {
			System.out.println("problem");
		}
	}
	
	public String toString() {
		System.out.println("Plan rozvoja:");
		for (Aktivita aktivita : this.aktivity) {
			System.out.println(aktivita.toString());
		}
		return "";
	}
	
	// vráti koľko celých hodín sa strávilo aktivitami (1 bod)
	public int vratCas() {
		int min = 0;
		for (Aktivita aktivita : this.aktivity) {
			min += aktivita.getDlzka();
		}
		return min/60;
	}
	
	// vráti koľko minút trvala najdlhšia aktivita (1 bod).
	public int najdlhsiaAktivita() {
		List<Integer> dlzky = new ArrayList<Integer>();

		for (Aktivita aktivita : this.aktivity) {
			dlzky.add(aktivita.getDlzka());
		}
		int maxDlzka = Collections.max(dlzky);
		return maxDlzka;
	}
	
	// vráti názvy všetkých aplikácii, ktoré boli použité, vo vrátenom zozname sa môže každá aplikácia nachádzať najviac raz (2 body).
	List<String> vratPouzivaneAplikacie() {
		Set<String> apky = new HashSet<String>();
		List<String> aplikacie = new ArrayList<String>();
		for (Aktivita aktivita : this.aktivity) {
			if (!(aktivita.getAplikacia() == null)) {
				apky.add(aktivita.getAplikacia());
			}
		}
		for (String s : apky) {
			aplikacie.add(s);
		}
		return aplikacie;
	}
	
	// vráti názvy všetkých aktivít, ktorým sa venoval, vo vrátenom zozname sa môže každý názov vyskytovať najviac raz
	public List<String> vratAktivityPodlaTypu(String typ)  {
		Set<String> typy = new HashSet<String>();
		List<String> typy2 = new ArrayList<String>();
		for (Aktivita aktivita : this.aktivity) {
			if (aktivita.getTyp().toLowerCase().equals(typ.toLowerCase())) {
				typy.add(aktivita.getTyp());
			}
		}
		for (String s : typy) {
			typy2.add(s);
		}
		return typy2;
	}
	
	// vráti čas koľko minút sa venoval aktivitám 
	// počas dňa zadaného parametrom. Predpokladajte, že žiadna aktivita nepresahuje polnoc.
	public int casAktivitPocasDna(String datum) {
		int cas = 0;
		for (Aktivita aktivita : this.aktivity) {
			if (aktivita.getDatum().equals(datum)) {
				cas += aktivita.getDlzka();
			}
		}
		return cas;
	}
	
	// vráti či aplikácia, sa používa pri vykonávaní aktivity zadanej parametrom (2 body)
	public boolean skontrolujAplikaciu(String aplikacia, String nazovAktivity)  {
		int count = 0;
		for (Aktivita aktivita : this.aktivity) {
			if (aktivita.getNazov().toLowerCase().equals(nazovAktivity.toLowerCase()) && aktivita.getAplikacia().toLowerCase().equals(aplikacia.toLowerCase())) {
				count += 1;
			}
	}
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// vráti zoznam názvov aktivít ktoré sa vykonali práve raz (3 body)
	public List<String> nepopularneAktivity() {
		Set<String> aktivity1 = new HashSet<String>();
		//Map<String, Integer> pocty = new HashMap<String, Integer>();
		List<String> nazvy = new ArrayList<String>();
		int count = 0;
		for (Aktivita aktivita : this.aktivity) {
			aktivity1.add(aktivita.getNazov().toLowerCase());
		}
		for (String s : aktivity1) {
			for (Aktivita aktivita : this.aktivity) {
				if (aktivita.getNazov().toLowerCase().equals(s)) {
					count +=1;
				}
			}
			if (count == 1) {
				nazvy.add(s);
			}
			count = 0;
		}
		return nazvy;
	}
	
	// vráti zoznam, v ktorom sú všetky názvy aktivít ktoré vždy boli vykonávané ako pridružené. Každý názov sa nachádza v liste nanajvýš raz (4)
	// ani raz neboli vykonane ako hlavna aktivita??
	public List<String> vratZoznamPridruzenychAktivit() {
		Set<String> pridruzene = new HashSet<String>();
		List<String> ok = new ArrayList<String>();
		int p = 0;
		for (Aktivita aktivita : this.aktivity) {
			if (!aktivita.getPridruzenie().toLowerCase().equals("ziadna")) {
				pridruzene.add(aktivita.getPridruzenie().toLowerCase());
			}
		}
		for (String s : pridruzene) {
			for (Aktivita aktivita : this.aktivity) {
				if (aktivita.getNazov().toLowerCase().equals(s)) {
					p +=1;
				}
			}
			if (p==0) {
				ok.add(s);
			}
		}
		return ok;
	}
	
	// vráti mapu, kde je každému typu aktivity 
	// "fyzicka", "mentalna" alebo ďalšie typy priradené koľko percent celkového času sa ňou strávilo (4
	// body). +1 bod za zaokrúhlenie percent na 2 desatinné miesta.
	public Map<String, Double> percentoTypov() {
		Map<String, Double> percenta = new HashMap<String, Double>();
		Set<String> typy = new HashSet<String>();
		double count = 0;
		double celkovo = 0;
		double percento = 0;
		for (Aktivita aktivita : this.aktivity) {
			typy.add(aktivita.getTyp().toLowerCase());
			celkovo += 1;
		}
		for (String s : typy) {
			count = 0;
			for (Aktivita aktivita : this.aktivity) {
				if (aktivita.getTyp().toLowerCase().equals(s)) {
					count += 1;
				}
			}
			percento = count/celkovo;
			percenta.put(s, (percento*100));
		}
		return percenta;
	}
	
	// vráti pole veľkosti 24, každý index zodpovedá hodine 
	// počas dňa. Vo vrátenom poli na pozícii i je koľko minút sa dokopy venoval aktivitám počas danej 
	// hodiny, pre jednoduchosť celú aktivitu zapíšte do hodiny kedy začala. (4 body) +3 body ak aktivite 
	// ktorá je na prelome hodín rozdelíte minúty správne k jednotlivým hodinám. Predpokladajte, že 
	// aktivity netrvajú dlhšie ako hodinu a aktivity nepresahujú polnoc. +1 bod ak aktivity presahujú 
	// polnoc a správne sa ich čas rozpočíta
	public int[] histogramPoHodinach() {
		int[] histogram = new int[24];
		int hodina = 0;
		int minuta = 0;
		int count = 0;
		for (Aktivita aktivita : this.aktivity) {
			hodina = Integer.parseInt(aktivita.getCasZaciatku().substring(0,2));
			minuta = Integer.parseInt(aktivita.getCasZaciatku().substring(3,5));
			int dlzka = aktivita.getDlzka();
			if (dlzka <= 60 - minuta) {
				histogram[hodina] += aktivita.getDlzka();
			}
			System.out.println(minuta);
			if (dlzka > 60 - minuta) {
				histogram[hodina] = (60 - minuta);
				int hod = (dlzka - histogram[hodina]) / 60;
				int zvysne = (dlzka - histogram[hodina]) % 60;
				System.out.println(zvysne);
				for (int i = 1; i <= hod; i++) {
					histogram[hodina + i] = 60;
				}
				histogram[hodina + hod + 1] = zvysne;
			}
			count = 0;
		}
		return histogram;
	}
	
	// metóda  vráti plán rozvoja za obdobie určené parametrami (vrátane dní určených parametrami)
	public PlanRozvoja vratPlanRozvojaZaObdobie(String odDatumu, String poDatum) {
		PlanRozvoja pr = new PlanRozvoja();
		for (Aktivita aktivita : this.aktivity) {
			
		}

	}
	
	// vráti mapu, kde je každej 
	// aplikácii priradené aké celkové množstvo času v minútach bola použitá (4 body)
	public Map<String, Integer> najpouzivanejsieAplikacie() {
		Map<String, Integer> mapa = new HashMap<String, Integer>();
		Set<String> apky = new HashSet<String>();
		int cas = 0;
		for (Aktivita aktivita : this.aktivity) {
			apky.add(aktivita.getAplikacia().toLowerCase());
		}
		for (String s : apky) {
			cas = 0;
			for (Aktivita aktivita : this.aktivity) {
				if (aktivita.getAplikacia().toLowerCase().equals(s)){
					cas += aktivita.getDlzka();
				}
			}
			mapa.put(s, cas);
		}
		return mapa;
	}
	
	// vráti zoznam piatich aplikácii ktoré sa využívali najviac 
	// minút pri rôznych aktivitách. (3 bodov). Pozn. Bez korektného riešenia predchádzajúcej úlohy 
	// nemôžete riešiť túto
	List<String> top5Aplikacii() {
		
	}
	
	// aplikáciu nazveme "dennou 
	// výukovou" ak na každý deň nám dá inú činnosť. Napr. každý deň nám vyberie iný recept ktorý 
	// budeme variť. Metóda vráti true ak aplikácia zadaná parametrom má vždy iný detail (
	public boolean dennaVyukovaAplikacia(String nazovAplikacie) {
		Set<String> detaily = new HashSet<String>();
		int pocetD = 0;
		int count = 0;
		for (Aktivita aktivita : this.aktivity) {
			detaily.add(aktivita.getDetail().toLowerCase());
		}
		pocetD = detaily.size();
		for (String s:detaily) {
			for (Aktivita aktivita : this.aktivity) {
				if (aktivita.getAplikacia().equals(nazovAplikacie) && aktivita.getDetail().toLowerCase().equals(s)) {
					count +=1;
				}
			}
		}
		if (count == pocetD) {
			return true;
		}
		else {
			return false;
		}
		
	}
}
