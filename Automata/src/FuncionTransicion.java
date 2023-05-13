
public class FuncionTransicion {
	Nodo estadoInput;
	String simbolo;
	Nodo output;
	
	public FuncionTransicion(Nodo estadoInput, String simbolo, Nodo output) {
		this.estadoInput = estadoInput;
		this.simbolo = simbolo;
		this.output = output;
	}
	
	@Override
	public String toString() {
		return "(" + estadoInput.nombre + "," + simbolo + "," + output.nombre + ")";
	}
}
