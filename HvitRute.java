
public class HvitRute extends Rute {


  public HvitRute(int kolonne, int rad){
    super(kolonne, rad);
    status = ".";
    //System.out.println(skrivUtNaboliste());
  }

  public void gaa(String kolonneRad){ //gaa() skal gå til neste rute
    for (int nesteRute = 0; nesteRute < naboListe.stoerrelse(); nesteRute++) {
        hvitRuteTeller++;
        if (naboListe.hent(nesteRute).hvitRuteTeller > 0){ //sjekker at vi ikke går til en rute vi allerede har besøkt
          return;
        }
        //bytter ut den hvite ruten vi kom fra med en sort rute i nabolisten til ruten vi skal til
        for (int listeIndeks = 0; listeIndeks<naboListe.hent(nesteRute).naboListe.stoerrelse(); listeIndeks++) {
          if (naboListe.hent(nesteRute).naboListe.hent(listeIndeks).equals(this)){
            SortRute erstatt = new SortRute(this.kolonne, this.rad);
            naboListe.hent(nesteRute).naboListe.sett(listeIndeks, erstatt);
          }
        }
        //
          naboListe.hent(nesteRute).gaa(kolonneRad + ""+ naboListe.hent(nesteRute).kolonne
          + " "+ naboListe.hent(nesteRute).rad + "@");
      }
      //tilbakestiller hvitruteteller
      hvitRuteTeller = 0;
    }

  @Override
  public char tilTegn(){
    char tegn = status.charAt(0);
    return tegn;
  }


  @Override
  public String toString() {
    return status;
    }
}
