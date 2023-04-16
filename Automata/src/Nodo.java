import java.util.ArrayList;

public class Nodo {
	String nombre;
	boolean esFinal;
	ArrayList<FuncionTransicion> deltaDelNodo;
	
	public Nodo(String nombre, boolean esFinal) {
		this.nombre = nombre;
		this.esFinal = esFinal;
		deltaDelNodo = new ArrayList<FuncionTransicion>();
	}
	
	
	public void AñadirFuncionTransicion(String simbolo, String nodoOutput) {
		FuncionTransicion funcion = new FuncionTransicion(this.nombre, simbolo, nodoOutput);
		deltaDelNodo.add(funcion);
	}
	
	@Override
	public String toString() {
		String msg = "\nEstado: " + nombre + "\n fuciones: ";
		//System.out.println("delta? " + deltaDelNodo.get(0));
		for(int i = 0; i < deltaDelNodo.size(); i++) {
			msg += deltaDelNodo.get(i) + " ";
		}
		return msg;
	}
	
}
