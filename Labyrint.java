import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

class Labyrint {
  static int kolonne;
  static int rad;
  static Rute[][] labyrint;
  static Rute[][] ruteArray;
  static Lenkeliste aapningArray = new Lenkeliste<Aapning>();
  Lenkeliste<String> utveiListe = new Lenkeliste<String>();
  int antallVeier;

  private Labyrint(Rute[][] ruteArray, int rader, int kolonner){
      labyrint = ruteArray;
      kolonne = kolonner;
      rad = rader;
      for (int i = 0; i<ruteArray.length; i++){
        for (int x = 0; x<ruteArray[i].length; x++){
          ruteArray[i][x].denneLabyrinten(this); //med denne løkken legger jeg til laabyrinten i alle rutene
      }
    }
  }

  public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
    Scanner nyLabyrint = new Scanner(fil);
    String foersteLinje = nyLabyrint.nextLine(); //leser av førstelinjen (som er tall for rad/kolonne)
    String[] radKol = foersteLinje.split(" "); //deler på mellomrommet

    rad = Integer.parseInt(radKol[0]); //henter ut første to siffre, og gjør disse til tall
    kolonne = Integer.parseInt(radKol[1]); //gjør det samme med de neste to.
    ruteArray = new Rute[rad][kolonne]; //oppretter et rutearray med antall kolonner og rader lik antallet som ble oppgitt i filen

    int radTeller = 0;
    while (nyLabyrint.hasNextLine()){ //kjører så lenge det er linjer å lese av
      String linje = nyLabyrint.nextLine(); //sier at linje = neste linje
      char[] linje_tegn = linje.toCharArray(); //gjør om alle bokstavene i linjen til karakterer, og fyller et array med dem

      for (int kolonneTeller = 0; kolonneTeller < kolonne; kolonneTeller++) {
        if (linje_tegn[kolonneTeller] == '.'){
          if (kolonneTeller == kolonne-1 || kolonneTeller == 0 || radTeller == 0 || radTeller == rad-1){
            Aapning nyAapning = new Aapning(kolonneTeller, radTeller);
            ruteArray[radTeller][kolonneTeller] = nyAapning;
            aapningArray.leggTil(nyAapning);
          }
          else{
            HvitRute nyHvitRute = new HvitRute(kolonneTeller, radTeller); //oppretter hvite rute hvis karakteren er '.'
            ruteArray[radTeller][kolonneTeller] = nyHvitRute; //legger ruten deretter til i rutearrayet
          }
        }
        else {
          SortRute nySortRute = new SortRute(kolonneTeller, radTeller);
          ruteArray[radTeller][kolonneTeller] = nySortRute;
          }
        }
      radTeller++;
      }
      kallPaaNaboer();
      Labyrint ny = new Labyrint(ruteArray, rad, kolonne); //oppretter labyrinten med rutearray, kolonner og rader
      //System.out.println(ny);
      return ny;
    }

  public static void kallPaaNaboer(){
    for (int arrayRad = 0; arrayRad < ruteArray.length; arrayRad++){
      for (int arrayKol = 0; arrayKol < ruteArray[arrayRad].length; arrayKol++){
        int indeksTeller = 0;
        for (int r= -1; r < 2; r++){
          for (int k= -1; k < 2; k++){ //nøstet løkke som sikrer at vi leter igjennom alle indeksene
            boolean gyldig = true; //avgjør om vi skal legge til ruten i nabolisten eller ikke
            if (k == r || k+r==0){
              gyldig = false;
            }
            if (arrayRad+r >= ruteArray.length || arrayKol+k < 0 || arrayRad+r < 0 ||
                arrayKol+k >= ruteArray[arrayRad].length){ //passer på at vi ikke er utenfor labyrinten
              gyldig = false;
            }
            if (gyldig == true) {
              ruteArray[arrayRad][arrayKol].naboListe.leggTil(ruteArray[arrayRad+r][arrayKol+k]);
              indeksTeller++;
            }
          }
        }
      }
    }
  }

  public Liste finnUtveiFra(int k, int r){
    labyrint[r][k].finnUtvei();
    return utveiListe;
  }

  @Override
  public String toString() {
    return " -- Dette er vår labyrint -- \nKolonner: "+kolonne + " --- Rader: " + rad;
  }

}
