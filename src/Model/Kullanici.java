package Model;

public abstract class Kullanici {
	private int id;
	String tc,sifre,isim;
	public Kullanici(int id, String tc, String sifre, String isim) {
		super();
		this.id = id;
		this.tc = tc;
		this.sifre = sifre;
		this.isim = isim;
	}
	public Kullanici() {}
	
    public abstract void girisYap();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getSifre() {
		return sifre;
	}
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
}
