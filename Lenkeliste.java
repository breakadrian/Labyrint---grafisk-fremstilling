import java.util.Iterator;

class Lenkeliste<T> implements Liste<T>  { //henter inn grensesnittet Liste

  class Node {
    Node neste; //her sier vi at, foreløpig, er den neste Noden tom
    T data; //her definerer vi "data" som en type av T
    Node(T x) { //her oppretter vi en ny Node som inneholder x som referer til objekt T
      data = x; //vi sier her at data nå referer til x
    }
  }
  protected Node start; //lager en Node som er starten (hodet) til listen vår

  class LenkelisteIterator implements Iterator<T> { // oppgave C2

        private Lenkeliste<T> liste;
        private int posIter;

        public LenkelisteIterator(Lenkeliste<T> nyLL) {
            liste = nyLL;
            posIter = 0;
        }

        public boolean hasNext() {
            return (posIter < liste.stoerrelse());
        }

        public T next() {
            return liste.hent(posIter++);
        }

        public void remove() {
        }
    }

    public Iterator iterator() { // returnerer et nytt lenkelisteiterator-objekt
        return new LenkelisteIterator(this);
    }

    /**
     * Returnerer antall Noder i lenkelisten.
     *
     * @return En int som representerer storelsen til listen.
     */

  @Override
  public void leggTil(int pos, T x) {
    if (pos > stoerrelse() || pos < 0){ //sjekker om noen parametere er utenfor det som er gyldig, og hvis så tilfelles kastes de
      throw new UgyldigListeIndeks(pos);
    }
    if (pos == 0) { //hvis posisjonen vi skal legge noe til i er i indeks 0 trenger vi ikke tenke på hva som er forran, så da kan vi bare sette den nye nodens .neste til å peke på den tidligere starten.
      Node ny_node = new Node(x);
      Node peker = start;
      start = ny_node;
      ny_node.neste = peker;
    }

    else{
    Node ny_node = new Node(x);
    Node peker = start;
//er det ikke indeks 0 må vi finne frem til hvilken node det er snakk om, og for det bruker vi en for-løkke.
//for å løse behovet for to Noder til å holde på verdiene jeg trenger, har jeg lagd to for-løkker
    for (int i = 0; i < pos; i++) {
      peker = peker.neste;
    }
      Node beholder = peker;
      Node nyPeker = start;

      for (int i = 0; i < pos-1; i++) {
        nyPeker = nyPeker.neste;
      }
      nyPeker.neste = ny_node;
      ny_node.neste = beholder;
    }
  }

  public void leggTil(T x) {
    Node ny_node = new Node(x);
    if (start == null) {
      start = ny_node; //er listen tom kan vi bare sette startnoden til å refere den nye noden vi legger til
    }
    else {
      Node siste = start;
      for (int i = 0; i < stoerrelse()-1; i++){
        siste = siste.neste; //hvis ikke så trenger vi igjen å finne noden vi ønsker å forandre.
      }
      siste.neste = ny_node; //så det vi gjør er å sette siste noden sin.neste til å referere til den nye noden
    }
  }

  @Override
  public void sett(int pos, T x) {
    if (pos >= stoerrelse() || pos < 0){ //vi kaster tilfellene som ikke vil gjelde
      throw new UgyldigListeIndeks(pos);
    }
    if (stoerrelse()==0) {
      throw new UgyldigListeIndeks(pos);
    }

    Node peker = start;
    for (int i = 0; i < pos; i++) { //deretter bytter vi ut den valgte nodens innhold med det nye innholdet
      peker = peker.neste;
    }
    peker.data = x;
  }

  public int stoerrelse() {
    Node p = start; //Node p peker her på Node start
    int n = 0; //dette er node-telleren vår
    while (p != null) { //så lenge noden vi er på ikke er tom øker vi telleren med 1
      n++;
      p = p.neste;
    }
    return n; //dette stod det ikke i forelesningen, men vi må da returnere noe i en metode
    //som ikke har void og som skal gi oss lengden(?)
  }

  public T hent(int pos) {
    if (pos >= stoerrelse() || pos < 0){
      throw new UgyldigListeIndeks(pos);
    }
    if (stoerrelse()==0) {
      throw new UgyldigListeIndeks(pos);
    }
  //her henter vi inn noden som vi skal returnere dataen til
    Node p = start;
    for (int i = 0; i < pos; i++) {
      p = p.neste;
    }
    return p.data;
}

public Node hentObjekt(int pos) {
  if (pos >= stoerrelse() || pos < 0){
    throw new UgyldigListeIndeks(pos);
  }
  if (stoerrelse()==0) {
    throw new UgyldigListeIndeks(pos);
  }
//her henter vi inn noden som vi skal returnere dataen til
  Node p = start;
  for (int i = 0; i < pos; i++) {
    p = p.neste;
  }
  return p;
}


  @Override
  public T fjern(int pos) {
    if (pos >= stoerrelse() || pos < 0){
      throw new UgyldigListeIndeks(pos);
    }
    if (start == null){
      throw new UgyldigListeIndeks(-1);
    }
    if (pos == 0) {
      return fjern();
    }
    Node fjern = start; //ved å lete opp både hvilken node vi skal fjerne
    for (int i = 0; i < pos; i++) {  //og hvilken som skal referere forbi den blir det
      fjern = fjern.neste; //enkelt å returnere dataen til den vi har fjernet
    }
    Node behold = fjern.neste;
    Node hopp = start;
    for (int i = 0; i< pos-1; i++) {
      hopp = hopp.neste;
    }
    hopp.neste = behold;
    return fjern.data;
}

    public T fjern() {
    if (start == null) {
      throw new UgyldigListeIndeks(-1);
    }
    T peker = start.data;//her får vi bare startnodens neste til å bli referert til av start
    start = start.neste;
    return peker;
  }

  public void slettListe(){
    start = null;
  }
}
