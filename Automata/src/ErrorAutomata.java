
public class ErrorAutomata extends Exception implements IErrores{
	private byte codigo;
	
	
	public ErrorAutomata(byte codigo, String mensaje) {
		super(mensaje);
		this.setCodigo(codigo);
	}

	private void setCodigo(byte codigo) {
		this.codigo = codigo;
		
	}
	public byte getCodigo() {
		return codigo;
	}

}
