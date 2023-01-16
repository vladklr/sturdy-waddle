import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zona {

    String denumire;
    int nrCoord;

    int LatN;
    int LatS;
    int LongV;
    int LongE;

    ArrayList<ArrayList> listaOre = new ArrayList<>();
    ArrayList<String> ore;


    public ArrayList<ArrayList> getListaOre() {
        return listaOre;
    }

    public Zona(String denumire, int nrCoord) throws IOException {
        this.denumire = denumire;
        this.nrCoord = nrCoord;
        citireFisier();
        citireFisier2();
        for(int i = 0; i < linii.size() - 1; i++) {
            String txt = linii.get(i);
            Pattern p3 = Pattern.compile("\\d{1,2}:\\d{2}");
            Matcher m3 = p3.matcher(txt);
            ore = new ArrayList<>();
            while(m3.find()) {
                ore.add(m3.group());
            }
            listaOre.add(ore);
            ///System.out.println(listaOre.get(i));
        }
        atribuieCoordonate();
    }
    public ArrayList<Integer> coordonate = new ArrayList<>();
    public ArrayList<Integer> listaLocuri = new ArrayList<>();

    public void citireFisier() throws IOException {
        Path fileName = Path.of("/Users/georgianamindruta/IdeaProjects/TEMA2/src/parcari.txt");
        String str = Files.readString(fileName);
        Pattern p = Pattern.compile("([0-9]){3}");
        Matcher m = p.matcher(str);
        Pattern p2 = Pattern.compile("(\\d+)(?=\\s*LOC)");
        Matcher m2 = p2.matcher(str);
        while(m.find())
            coordonate.add(Integer.valueOf(m.group()));
        while(m2.find())
            listaLocuri.add(Integer.valueOf(m2.group()));
        }

        ArrayList<String> linii = new ArrayList<>();

    public String fisierSituatia1 = "/Users/georgianamindruta/IdeaProjects/TEMA2/src/situatieParcari1_ora10.00.txt";
    public String fisierSituatia2 = "/Users/georgianamindruta/IdeaProjects/TEMA2/src/situatieParcari2_ora10.00.txt";
    public String fisierSituatia3 = "/Users/georgianamindruta/IdeaProjects/TEMA2/src/situatieParcari3_ora10.00.txt";
    public String fisier = fisierSituatia2;

    public String getFisierSituatia1() {
        return fisierSituatia1;
    }

    public String getFisier() {
        return fisier;
    }

    public void citireFisier2() throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fisier));
            String line = reader.readLine();
            linii.add(line);
            while (line != null) {
                line = reader.readLine();
                linii.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNrCoord() {
        return nrCoord;
    }

    public void setNrCoord(int nrCoord) {
        this.nrCoord = nrCoord;
    }

    public int getLatN() {
        return LatN;
    }

    public void setLatN(int latN) {
        LatN = latN;
    }

    public int getLatS() {
        return LatS;
    }

    public void setLatS(int latS) {
        LatS = latS;
    }

    public int getLongV() {
        return LongV;
    }

    public void setLongV(int longV) {
        LongV = longV;
    }

    public int getLongE() {
        return LongE;
    }

    public void setLongE(int longE) {
        LongE = longE;
    }

    public void atribuieCoordonate() {
            LatN = coordonate.get(nrCoord);
            LatS = coordonate.get(nrCoord + 1);
            LongV = coordonate.get(nrCoord + 2);
            LongE = coordonate.get(nrCoord + 3);
            if(getDenumire().equals("ZonaA") || getDenumire().equals("ZonaB") || getDenumire().equals("ZonaC"))
                System.out.println(denumire + ": " + LatN + " " + LatS + " " + LongV + " " + LongE);
            else {
                System.out.print(denumire + ": " + LatN + " " + LatS + " " + LongV + " " + LongE);
            }


        }

    }
