import java.io.IOException;

public class Parcare extends Zona{

    int locuri;
    int nrLocuri;
    public Parcare(String denumire, int nrCoord, int nrLocuri) throws IOException {
        super(denumire, nrCoord);
        this.nrLocuri = nrLocuri;
        atribuieLocuri();
        System.out.println(" , locuri: " + locuri);
    }

    public int getLocuri() {
        return locuri;
    }

    public void atribuieLocuri() {
        locuri = listaLocuri.get(nrLocuri);
    }
}
