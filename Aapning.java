class Aapning extends HvitRute {


  public Aapning(int kolonne, int rad){
    super(kolonne, rad);
    status = ".";
  }

  public void gaa(String kolonneRad){ //gaa() skal gå til neste mulige rute
    hvitRuteTeller++;
    for (int nesteRute = 0; nesteRute < naboListe.stoerrelse(); nesteRute++) {
        //bytter ut den hvite ruten vi kom fra med en sort rute i nabolisten til ruten vi skal til
        for (int listeIndeks = 0; listeIndeks<naboListe.hent(nesteRute).naboListe.stoerrelse(); listeIndeks++) {
          if (naboListe.hent(nesteRute).naboListe.hent(listeIndeks).equals(this)){
            SortRute erstatt = new SortRute(this.kolonne, this.rad);
            naboListe.hent(nesteRute).naboListe.sett(listeIndeks, erstatt);
          }
        }
        //vi besøker alle ruter
          naboListe.hent(nesteRute).gaa(kolonneRad + ""+ naboListe.hent(nesteRute).kolonne
          + " "+ naboListe.hent(nesteRute).rad + "@");

        }
        hvitRuteTeller = 0;
        rutensLabyrint.utveiListe.leggTil(kolonneRad);
        return;
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
