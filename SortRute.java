class SortRute extends Rute {

  public SortRute(int kolonne, int rad){
    super(kolonne, rad);
    status = "#";
  }

  public void gaa(String kolonneRad){
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
