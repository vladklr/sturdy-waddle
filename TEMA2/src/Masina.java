import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Masina {

    public Scanner scanner = new Scanner(System.in);

    int masinaLatN;
    int masinaLatS;
    int masinaLongV;
    int masinaLongE;

    public ArrayList<Parcare> parcari = new ArrayList<>();
    public int index = 0;
    public double rezultat = 0;

    Masina() throws IOException {
        Zona ZonaA = new Zona ("ZonaA", 0);
        Parcare P1 = new Parcare("P1", 4, 0);
        Parcare P2 = new Parcare("P2", 8, 1);
        Parcare P3 = new Parcare("P3", 12, 2);
        Zona ZonaB = new Zona ("ZonaB", 16);
        Parcare P4 = new Parcare("P4", 20, 3);
        Parcare P5 = new Parcare("P5", 24, 4);
        Parcare P6 = new Parcare("P6", 28, 5);
        Parcare P7 = new Parcare("P7", 32, 6);
        Zona ZonaC = new Zona ("ZonaC", 36);
        Parcare P8 = new Parcare("P8", 40, 7);
        Parcare P9 = new Parcare("P9", 44, 8);
        Parcare P10 = new Parcare("P10", 48, 9);
        parcari.addAll(Arrays.asList(P1, P2, P3, P4, P5, P6, P7, P8, P9, P10));

        System.out.println("Introduceti coordonatele: ");
        System.out.println("LatN: ");
        masinaLatN = scanner.nextInt();
        System.out.println("LatS: ");
        masinaLatS = scanner.nextInt();
        System.out.println("LongV: ");
        masinaLongV = scanner.nextInt();
        System.out.println("LongE: ");
        masinaLongE = scanner.nextInt();

        double temp;
        int i = 0;
        boolean x = true;
        for (Parcare p : parcari) {
            temp = calculeazaDistanta(p);
            if(x == true)
                rezultat = calculeazaDistanta(p);
            else if (temp < rezultat) {
                rezultat = temp;
                index = i;
            }
            x = false;
            i++;
        }

        afisare();


    }

    public void afisare() {
        int nrlocuri = parcari.get(index).getLocuri();
        int nrlocuriocupate = parcari.get(index).getListaOre().get(index).size();

        int nrlocuridisponibile = nrlocuri - nrlocuriocupate;
        System.out.println();
        System.out.println("CERINTA 1: ");
        System.out.println("----------------");
        System.out.println("Cea mai apropiata parcare este " + parcari.get(index).getDenumire());
        System.out.println("Parcarea are " + parcari.get(index).getLocuri() + " locuri");
        System.out.print("Coordonatele geografice sunt: N-" + parcari.get(index).getLatN() + " S-");
        System.out.print(parcari.get(index).getLatS() + " V-" + parcari.get(index).getLongV() + " E-");
        System.out.println(parcari.get(index).getLongE());
        System.out.print("Orele la care se elibereaza din locurile ocupate sunt urmatoarele: ");
        System.out.println(parcari.get(index).getListaOre().get(index));
        if(nrlocuridisponibile > 0)
            System.out.println("Parcarea are " + (nrlocuridisponibile) + " locuri disponibile");
        else
            System.out.println("Parcarea nu are niciun loc disponibil");

        cmmLocuriLibere();

        if(parcari.get(0).getFisier().equals(parcari.get(0).getFisierSituatia1()))
            primaOra();
        else {
            System.out.println();
            System.out.println("CERINTA 3: ");
            System.out.println("----------------");
            System.out.println("Exista locuri disponibile");
        }

    }

    public void cmmLocuriLibere() {
        int locurilibere = 0;
        int temp;
        int i = 0;
        int index_locuriLibere = 0;
        for (Parcare p : parcari) {
            temp = p.getLocuri() - p.getListaOre().get(i).size();
            if(locurilibere < temp) {
                locurilibere = temp;
                index_locuriLibere = i;
            }
            i++;
        }
        System.out.println();
        System.out.println("CERINTA 2: ");
        System.out.println("----------------");
        System.out.print("Parcarea cu cele mai multe locuri libere este " + parcari.get(index_locuriLibere).getDenumire());
        System.out.println(" cu " + locurilibere + " locuri libere.");
        System.out.print("Coordonatele geografice sunt: N-" + parcari.get(index_locuriLibere).getLatN() + " S-");
        System.out.print(parcari.get(index_locuriLibere).getLatS() + " V-" + parcari.get(index_locuriLibere).getLongV() + " E-");
        System.out.println(parcari.get(index_locuriLibere).getLongE());


    }

    public void primaOra() {
        String ora = "23:59";
        String temp;
        int index_primaOra = 0;
        for (int i = 0; i < parcari.get(0).getListaOre().size(); i++) {
            for (int j = 0; j < parcari.get(0).getListaOre().get(i).size(); j++) {
                temp = (String) parcari.get(0).getListaOre().get(i).get(j);
                LocalTime localTime1 = LocalTime.parse(temp);
                LocalTime localTime2 = LocalTime.parse(ora);
                int comparison = localTime1.compareTo(localTime2);
                if (comparison < 0) {
                    ora = temp;
                    index_primaOra = i;
                }
            }
        }
        System.out.println();
        System.out.println("CERINTA 3: ");
        System.out.println("----------------");
        System.out.print("Prima ora la care se elibereaza un loc este la " + ora + " in parcarea ");
        System.out.println(parcari.get(index_primaOra).getDenumire());
        System.out.print("Coordonatele geografice sunt: N-" + parcari.get(index_primaOra).getLatN() + " S-");
        System.out.print(parcari.get(index_primaOra).getLatS() + " V-" + parcari.get(index_primaOra).getLongV() + " E-");
        System.out.println(parcari.get(index_primaOra).getLongE());
    }

    public int getMasinaLatN() {
        return masinaLatN;
    }

    public int getMasinaLatS() {
        return masinaLatS;
    }

    public int getMasinaLongV() {
        return masinaLongV;
    }

    public int getMasinaLongE() {
        return masinaLongE;
    }

    public int getX() {
        return (getMasinaLatN() + getMasinaLatS()) / 2;
    }

    public int getY() {
        return (getMasinaLongE() + getMasinaLongV()) / 2;
    }

    public double calculeazaDistanta(Parcare p) {
        int x = (p.getLatN() + p.getLatS()) / 2;
        int y = (p.getLongE() + p.getLongV()) / 2;
        return Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
    }
}
