package model;

public class Temperatura {
    private Integer id;
    private String valor;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer _id) {
		this.id = _id;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Temperatura(Integer id, String valor) {
		this.id = id;
		this.valor = valor;
	}
	public Temperatura() {
		
	}
    
    
}