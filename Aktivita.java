package sk.upjs.finalTerm2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aktivita {
	/*
	nazov (názov aktivitý ktorej sa venoval napr. cvičenie lýtok, učenie sa Španielčiny, jazda na 
			rotopede alebo iné)
			 datum (dátum, kedy sa aktivite venoval, vo formáte DD.MM.RRRR, napr. "05.12.2021" –
			v dátume sú vždy úvodné nuly doplnené tak, aby deň aj mesiac boli dvojciferné),
			 casZaciatku (čas, kedy začal aktivitu, vo formáte HH:MM, napr. "13:05", v čase sú vždy 
			úvodné nuly doplnené tak, aby sa každá časť časového reťazca skladala z 2 cifier, resp. bola 
			dvojciferná),
			 dlzka (čas, koľko minút sa venoval aktivite),
			 pridruzenie (činnosť ku ktorej pridal samozdokonaľovaciu aktivitu napr. umývanie zubov, 
			cesta do obchodu alebo „žiadna“ ak sa venoval iba aktivite)
			 typ ("fyzicka", "mentalna", "oddychova", "strava" alebo iné podľa toho o aký typ ide)
			 aplikacia (názov mobilnej aplikácie ktorú použil napr. na cvičenie, alebo kurz varenia)
			 detail (detail z aplikácie napríklad názov cvičenia, level cvičenia, recept ktorý varil alebo iné) */
	
	
	private String nazov;
	private String datum;
	private String casZaciatku;
	private int dlzka;
	private String pridruzenie;
	private String typ;
	private String aplikacia;
	private String detail;
	
	// s pouzitim mobilnej aplikacie
	public Aktivita(String nazov, String datum, String casZaciatku, int dlzka, String pridruzenie, String typ, String aplikacia, String detail) {
		this.nazov = nazov;
		this.datum = datum;
		this.casZaciatku = casZaciatku;
		this.dlzka = dlzka;
		this.pridruzenie = pridruzenie;
		this.typ = typ;
		this.aplikacia = aplikacia;
		this.detail = detail;
	}
	
	// bez pouzitia mobilnej aplikacie
	public Aktivita(String nazov, String datum, String casZaciatku, int dlzka, String pridruzenie, String typ) {
		this.nazov = nazov;
		this.datum = datum;
		this.casZaciatku = casZaciatku;
		this.dlzka = dlzka;
		this.pridruzenie = pridruzenie;
		this.typ = typ;
	}
	
	public String getNazov() {
		return nazov;
	}
	public void setNazov(String nazov) {
		this.nazov = nazov;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getCasZaciatku() {
		return casZaciatku;
	}
	public void setCasZaciatku(String casZaciatku) {
		this.casZaciatku = casZaciatku;
	}
	public int getDlzka() {
		return dlzka;
	}
	public void setDlzka(int dlzka) {
		this.dlzka = dlzka;
	}
	public String getPridruzenie() {
		return pridruzenie;
	}
	public void setPridruzenie(String pridruzenie) {
		this.pridruzenie = pridruzenie;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getAplikacia() {
		return aplikacia;
	}
	public void setAplikacia(String aplikacia) {
		this.aplikacia = aplikacia;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public static Aktivita zoStringu(String popis)  {
		final Scanner sc = new Scanner(popis);
		sc.useDelimiter("\t");
		int count = 0;
		List<String> list = new ArrayList<String>();
		while (sc.hasNext()) {
			list.add(sc.next());
		}
		Aktivita aktivita = null;
		final int info3 = Integer.parseInt(list.get(3));
		if (list.size() == 8) {
			aktivita = new Aktivita(list.get(0), list.get(1), list.get(2), info3, list.get(4), list.get(5), list.get(6), list.get(7));
		}
		if (list.size() == 6) {
			aktivita = new Aktivita(list.get(0), list.get(1), list.get(2), info3, list.get(4), list.get(5));
		}
		return aktivita;
	}
	
	// dlzka, pridruzenie, typ
	public String toString() {
		if (this.aplikacia == null) {
			return "Aktivita[nazov= " + nazov + ",datum= " + datum + ",casZaciatku= " + casZaciatku + ",dlzka= " + dlzka + ",pridruzenie= " + pridruzenie + ",typ= " + typ +"]";
		}
		else {
			return "Aktivita[nazov= " + nazov + ",datum= " + datum + ",casZaciatku= " + casZaciatku + ",dlzka= " + dlzka + ",pridruzenie= " + pridruzenie + ",typ= " + typ 
					+ ",aplikacia= " + aplikacia + ", detail= " + detail + "]";
		}
	}
	
		
		// vráti čas kedy skončila aktivita v rovnakom tvare ako čas zaciatku
		public String vratCasKonca() {
			int hodiny = Integer.parseInt(this.casZaciatku.substring(0,2));
			int minuty = Integer.parseInt(this.casZaciatku.substring(3,5));
			int vysledneM = minuty + this.dlzka;
			String min = "";
			String novy = "";
			if (vysledneM >= 60) {
				vysledneM =vysledneM - 60;
				hodiny += 1;
			}
			if (vysledneM < 10) {
				min = "0" + String.valueOf(vysledneM);
				novy = String.valueOf(hodiny) + ":" + min;
			}
			else {
				novy = String.valueOf(hodiny) + ":" + String.valueOf(vysledneM);
			}
			return novy;
		
	}

}
