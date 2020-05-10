import java.util.stream.IntStream;


abstract class Rute {
  int kolonne;
  int rad;
  String status;
  Labyrint rutensLabyrint;
  int hvitRuteTeller;
  Lenkeliste<Rute> naboListe = new Lenkeliste<Rute>();;



  public Rute(int kolonne, int rad) {
    this.kolonne = kolonne;
    this.rad = rad;
  }

  public void gaa(String kolonneRad){ //gaa() skal gå til neste mulige rute
      return;
    }

  public void finnUtvei() { //kaller på gaa() for å finne utveien.
    gaa(""+ kolonne + " "+ rad + "@");
  }

  public void printNaboListe(){
    for (int i = 0 ; i < naboListe.stoerrelse(); i++){
      System.out.print(naboListe.hent(i).tilTegn());
    }
    System.out.println("");
  }

  public void denneLabyrinten(Labyrint denne){
    rutensLabyrint = denne;
  }

  public abstract char tilTegn();


  @Override
  public String toString() {
    return status;
  }

}
