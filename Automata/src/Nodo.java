
public class Nodo {
	String nombre;
	boolean esFinal;
	
	public Nodo(String nombre, boolean esFinal) {
		this.nombre = nombre;
		this.esFinal = esFinal;
	}
	
	FuncionTransicion[] deltaDelNodo;
	
}
