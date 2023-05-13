import java.util.ArrayList;

public class Nodo {
	String nombre;
	boolean esFinal;
	boolean esInicial = false;
	ArrayList<FuncionTransicion> deltaDelNodo;
	Nodo nodoClausura;
	
	
	public Nodo(String nombre, boolean esFinal) {
		this.nombre = nombre;
		this.esFinal = esFinal;
		deltaDelNodo = new ArrayList<FuncionTransicion>();
	}
	
	public Nodo(ArrayList<Nodo> Nodos) {
		String nombre = "";
		deltaDelNodo = new ArrayList<FuncionTransicion>();
		
		for(Nodo nodo : Nodos) {
			nombre += nodo.nombre;
			if(nodo.esFinal) {
				this.esFinal = true;
			}
		}
		this.nombre = nombre;
		
	}
	
	public void QuitarFuncionTransicion(String simboloAQuitar) {
		ArrayList<FuncionTransicion> aEliminar = new ArrayList<FuncionTransicion>();
		for(int i = 0; i < this.deltaDelNodo.size(); i++) {
			FuncionTransicion delta = deltaDelNodo.get(i);
			if(delta.simbolo.equals(simboloAQuitar)) {
				aEliminar.add(delta);
			}
		}
		deltaDelNodo.removeAll(aEliminar);
	}
	public void AñadirFuncionTransicion(String simbolo, Nodo nodoOutput) {
		FuncionTransicion funcion = new FuncionTransicion(this, simbolo, nodoOutput);
		deltaDelNodo.add(funcion);
	}
	
	@Override
	public String toString() {
		String msg = "\n ###Nodo### \nEstado: " + nombre + "\n fuciones: ";
		//System.out.println("delta? " + deltaDelNodo.get(0));
		for(int i = 0; i < deltaDelNodo.size(); i++) {
			msg += deltaDelNodo.get(i) + " ";
		}
		return msg;
	}
	
	public Nodo AplicarTransicion(String simbolo) {
		for(int i = 0; i < deltaDelNodo.size(); i++) {
			FuncionTransicion funcion = deltaDelNodo.get(i);
			//System.out.print("AplicarTransicion " + funcion.simbolo + " " + simbolo + "\n");
			if(funcion.simbolo.equals(simbolo)) {
				if(funcion.output.nodoClausura != null) {
					return funcion.output.nodoClausura;
				}
				return funcion.output;
			}
		}
		return null;
		
	}
	public boolean equals(Nodo nodo) {
		String nombreDelNodoAComparar = nodo.nombre;
		String nombreDelNodoACompararCopia = nodo.nombre;
		String[] nombreGrupo = this.nombre.split("");
		int n = nombreGrupo.length;
		for(int i = 0; i < n; i++) {
			String s = String.valueOf(nombreDelNodoACompararCopia.charAt(i));
			//System.out.println("test " + s);
			if(nombreDelNodoAComparar.contains(s)) {
				
				nombreDelNodoAComparar = nombreDelNodoAComparar.replace(s, "");
			}
			//System.out.println("nombre: " + nombreDelNodoAComparar);
		}
		if(nombreDelNodoAComparar.equals("")) {
			//System.out.println("true");
			return true;
		}
		return false;
	}
	
	
}
