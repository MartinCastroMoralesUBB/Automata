
public class FuncionTransicion {
	String estadoInput;
	String simbolo;
	String output;
	
	public FuncionTransicion(String estadoInput, String simbolo, String output) {
		this.estadoInput = estadoInput;
		this.simbolo = simbolo;
		this.output = output;
	}
	
	@Override
	public String toString() {
		return "(" + estadoInput + "," + simbolo + "," + output + ")";
	}
}
