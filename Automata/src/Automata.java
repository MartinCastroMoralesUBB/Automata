import java.util.ArrayList;

public class Automata {
	//Informacion del automata
	private ArrayList<Nodo> Nodos;
	private String estados;
	private String lenguaje;
	private String estadoInicial;
	private String estadosFinales;
	private String transicion;
	
	//Informacion del automata en este instante.
	private String estadoActual;

	public Automata(String estados, String lenguaje, String estadoInicial, String estadosFinales, String delta) throws ErrorAutomata{
		this.Nodos = new ArrayList<Nodo>();
		
		this.setEstados(estados);
		
		
		
		this.setLenguaje(lenguaje);
		this.setEstadoInicial(estadoInicial);
		System.out.println("estado inicial1 " + estadoInicial + " estado Inicial: " + this.getEstadoInicial());
		this.setEstadosFinales(estadosFinales);
		
		this.crearNodos(this.getEstados(), this.getEstadosFinales());
		
		this.setTransicion(delta);
		this.crearDelta(delta);
		printNodes();
		//System.out.println("transiciones: " + transicion);
		
		
		
		
		
	}
	
	public void Leer(String palabra) {
		String estadoActual = this.getEstadoInicial();
		int indexNodo = buscarNodoPorEstado(estadoActual);
		System.out.println("_" + palabra + estadoActual);
		for(int i = 0; i < palabra.length(); i++) {
			OutputStep(estadoActual, palabra, i);
		}
	}
	public void OutputStep(String estadoActual, String palabra, int index) {
		int indexOffset = index + 1;
		String start = palabra.substring(0, indexOffset);
		String end = palabra.substring(indexOffset);
		String msg = start + "_" + end + estadoActual; 
		//System.out.println("start: " + start + " end: " + end + " palabra: " + palabra);
		System.out.println(msg);
	}
	
	public void crearDelta(String delta) {
		String[] grupoDelta = delta.split(" ");
		//Ahora grupoDelta (A,0,B)
		for(int i = 0; i < grupoDelta.length; i++) {
			
			String funcion = grupoDelta[i];
			String nodoInput = String.valueOf(funcion.charAt(1));
			String nodoOutput = String.valueOf(funcion.charAt(5));
			String simbolo = String.valueOf(funcion.charAt(3));
			
			int indexOfState = buscarNodoPorEstado(nodoInput);
			
			Nodos.get(indexOfState).AñadirFuncionTransicion(simbolo, nodoOutput);
		}
	}
	
	public int buscarNodoPorEstado(String nombreEstado) {
		for(int i = 0; i < Nodos.size(); i++) {
			if(Nodos.get(i).nombre.equals(nombreEstado)) {
				return i;
			}
		}
		return -1;
	}
	public void printNodes() {
		for(int i = 0; i < Nodos.size(); i++) {
			System.out.println("node: " + Nodos.get(i) + " \n¿Es Final? " + Nodos.get(i).esFinal + "\n");
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
			if(!checkIfElementIsIn(sPartida[i], estadosPartida)) {
				return false;
			}
		}
		return true;
	}
	public boolean checkIfElementIsIn(String sToCheck, String[] grupo) {
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
			
			if(checkIfElementIsIn(estado[i], grupoEstadosFinales)) {
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
		this.estadoInicial = estadoInicial;
		
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

	public String getTransicion() {
		return transicion;
	}

	public void setTransicion(String transicion) throws ErrorAutomata {
		String[] transicionGrupo = transicion.split(" ");
		
		for(int i = 0; i < transicionGrupo.length; i++) {
			String funcion = transicionGrupo[i];
			if(!(funcion.charAt(0) == '(' && funcion.charAt(2) == ',' && funcion.charAt(4) == ',' && funcion.charAt(6) == ')') ) {
				throw new ErrorAutomata(IErrores.ERROR_FUNCION_TRANSICION_EN_FORMATO_EQUIVOCADO, "Error En la linea 5\nFuncion Transicion no esta en el formato correcto");
			}
			String input = String.valueOf(funcion.charAt(1));
			String output = String.valueOf(funcion.charAt(5));
			String[] estadosGrupo = this.getEstados().split(" ");
			if(!(checkIfElementIsIn(input, estadosGrupo) && checkIfElementIsIn(output, estadosGrupo)) ) {
				//System.out.println("input: " + input + " output: " + output + " transicion: " + transicion + "transicion[" + i + "]: '" + funcion + "'");
				throw new ErrorAutomata(IErrores.ERROR_ESTADO_NO_EXISTE, "Error en la linea 5 \nEstado no existe");
			}
			String simbolo = String.valueOf(funcion.charAt(3));
			String[] lenguajeGrupo = this.getLenguaje().split(" ");
			
			if(!(checkIfElementIsIn(simbolo, lenguajeGrupo)) ) {
				throw new ErrorAutomata(IErrores.ERROR_CARACTER_NO_ES_PARTE_DEL_LENGUAJE, "Error en la linea 5\nCaracter no existe");
			}
	
		}
		
		this.transicion = transicion;
		
	}
	
	
	
	
	
	
}
