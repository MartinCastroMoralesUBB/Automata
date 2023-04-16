import java.util.ArrayList;

public class Automata {
	//Informacion del automata
	private ArrayList<Nodo> Nodos;
	private String estados;
	private String lenguaje;
	private String estadoInicial;
	private String estadosFinales;
	
	//Informacion del automata en este instante.
	private String estadoActual;

	public Automata(String estados, String lenguaje, String estadoInicial, String estadosFinales) throws ErrorAutomata{
		this.Nodos = new ArrayList<Nodo>();
		
		this.setEstados(estados);
		
		
		
		this.setLenguaje(lenguaje);
		this.setEstadoInicial(estadoInicial);
		this.setEstadosFinales(estadosFinales);
		
		this.crearNodos(this.getEstados(), this.getEstadosFinales());
		printNodes();
		
	}
	public void printNodes() {
		for(int i = 0; i < Nodos.size(); i++) {
			System.out.println("node: " + Nodos.get(i).nombre + " ¿Es Final? " + Nodos.get(i).esFinal);
		}
	}
	
	
	public boolean checkStringValidity(String sToCheck){
		String[] sPartida = sToCheck.split(" ");
		for(int i = 0; i < sPartida.length; i++) {
			String c = sPartida[i];
			if(c.equals("#") || c.equals("\"") || c.equals("'") || c.equals(",") || c.equals(".") || c.equals("_")) {
				return false;
			}
		}
		return true;
		
		
	}
	
	public boolean checkIfStateExists(String sToCheck) {
		//System.out.println("los estados del automata son: " + this.getEstados());
		String[] sPartida = sToCheck.split(" ");
		String[] estadosPartida = this.getEstados().split(" ");
		
		for(int i = 0; i < sPartida.length; i++) {
			//System.out.println("array con estados input: " + sPartida[i]);
			if(!checkIfStateIsIn(sPartida[i], estadosPartida)) {
				return false;
			}
		}
		return true;
	}
	public boolean checkIfStateIsIn(String sToCheck, String[] grupo) {
		for(int i = 0; i < grupo.length; i++) {
			
			//System.out.println("Check State: estado de entrada: " + sPartida[i] + " estado existente: " + estadosPartida[ii]);
			if(sToCheck.equals(grupo[i])) {
				return true;
			}
		}
		return false;
	}
	
	public void crearNodos(String estados, String estadosFinales) {
		boolean esFinal;
		String[] estado = estados.split(" ");
		String[] grupoEstadosFinales = estadosFinales.split(" ");
		for(int i = 0; i < estado.length; i++) {
			
			if(checkIfStateIsIn(estado[i], grupoEstadosFinales)) {
				esFinal = true;
			}else {
				esFinal = false;
			}
			Nodo nodo = new Nodo(estado[i], esFinal);
			Nodos.add(nodo);
		}
		
		
		
	}


	public String getEstados() {
		return estados;
	}

	public void setEstados(String estados) throws ErrorAutomata {
		if(checkStringValidity(estados)) {
			this.estados = estados;
		}else {
			throw new ErrorAutomata(IErrores.ERROR_CARACTER_NO_PERMITIDO, "Error en la linea 1");
		}
		
	}

	public String getLenguaje() {
		return lenguaje;
	}

	public void setLenguaje(String lenguaje) throws ErrorAutomata {
		if(checkStringValidity(lenguaje)) {
			this.lenguaje = lenguaje;
		}else {
			throw new ErrorAutomata(IErrores.ERROR_CARACTER_NO_PERMITIDO, "Error en la linea 2\nCharacter no permitido");
		}
		
	}

	public String getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(String estadoInicial) throws ErrorAutomata {
		if(estadoInicial.length() != 1) {
			throw new ErrorAutomata(IErrores.ERROR_MAS_DE_UN_ESTADO_INICIAL, "Error en la linea 3\nMas de un estado inicial");
		}
		
		if(!checkStringValidity(estadoInicial)) {
			throw new ErrorAutomata(IErrores.ERROR_CARACTER_NO_PERMITIDO, "Error en la linea 3\nCharacter no permitido");
		}
		if(!checkIfStateExists(estadoInicial)) {
			throw new ErrorAutomata(IErrores.ERROR_ESTADO_NO_EXISTE, "Error en la linea 3 \nEstado no existe");
		}
		
	}

	public String getEstadosFinales() {
		return estadosFinales;
	}

	public void setEstadosFinales(String estadosFinales) throws ErrorAutomata {
		if(!checkStringValidity(estadosFinales)) {
			throw new ErrorAutomata(IErrores.ERROR_CARACTER_NO_PERMITIDO, "Error en la linea 4\nCharacter no permitido");
		}
		if(!checkIfStateExists(estadosFinales)) {
			throw new ErrorAutomata(IErrores.ERROR_ESTADO_NO_EXISTE, "Error en la linea 4 \nEstado no existe");
		}
		this.estadosFinales = estadosFinales;
		
	}
	
	
	
	
}
