import java.util.ArrayList;

public class Automata {	//TODO: convertir de no finito a finito.Comprobar si un conjunto repite elementos
	//Informacion del automata
	private ArrayList<Nodo> Nodos;
	private String estados;
	private String lenguaje;
	private String estadoInicial;
	private String estadosFinales;
	private String transicion;
	private ArrayList<Nodo> NodosCombinacion;
	
	Nodo ClausuraInicial;
	private Nodo[][] matrizTransicion;
	
	//Informacion del automata en este instante.
	private String estadoActual;

	public Automata(String estados, String lenguaje, String estadoInicial, String estadosFinales, String delta) throws ErrorAutomata{
		this.Nodos = new ArrayList<Nodo>();
		
		this.setEstados(estados);
		
		
		
		this.setLenguaje(lenguaje);
		this.setEstadoInicial(estadoInicial);
		//System.out.println("estado inicial1 " + estadoInicial + " estado Inicial: " + this.getEstadoInicial());
		this.setEstadosFinales(estadosFinales);
		
		this.crearNodos(this.getEstados(), this.getEstadosFinales());
		
		this.setTransicion(delta);
		this.crearDelta(delta);
		
		this.matrizTransicion = CrearMatriz(estados, estadoInicial, estadosFinales, lenguaje, delta);
		
		//System.out.println("es Determinista: " + comprobarSiEsDeterminista());
		convertirADeterministaFinito(this.getLenguaje(), this.getEstados(), this.Nodos);
		
		this.NodosCombinacion = new ArrayList<Nodo>();
		//printNodes();
		
		
		
	}
	
	
	public Automata convertirADeterministaFinito(String lenguaje, String estados, ArrayList<Nodo> Nodos) {
		//Crear nodos combinados, agregar transicion hacia nodos combinados
		ArrayList<Nodo> NodosCombinados = new ArrayList<Nodo>();
		ArrayList<Nodo> ListaClausuras = new ArrayList<Nodo>();
		
		for(Nodo nodo : Nodos) {
			ListaClausuras = eliminaPalabrasVacias(nodo, ListaClausuras);
			
		}
		AñadirCombinados(ListaClausuras);
		this.printNodes();
		for(Nodo nodo : Nodos) {
			NodosCombinados = encontrarTransicionesDeUnSimbolo(nodo, lenguaje, NodosCombinados);
			
		}
		AñadirCombinados(NodosCombinados);
		return null;
		
	}
	
	public void AñadirCombinados(ArrayList<Nodo> NodosCombinados) {
		for(Nodo nodo : NodosCombinados) {
			//System.out.println("convertirADeterministaFinito: " + nodo.nombre);
			String TransicionDelNodoCombinado = "";
			String[] nombreGrupo = nodo.nombre.split("");
			for(int i = 0; i < nombreGrupo.length; i++) {
				Nodo nodoParcial = this.Nodos.get(this.buscarNodoPorEstado(nombreGrupo[i]));
				for(FuncionTransicion funcion: nodoParcial.deltaDelNodo) {
					if(!funcion.simbolo.equals("#")) {
						nodo.AñadirFuncionTransicion(funcion.simbolo, funcion.output);
					}
					
					
				}
				//TransicionDelNodoCombinado += nodo.deltaDelNodo.get(i);
				this.Nodos.add(nodo);
			}
			//System.out.println("TransicionDelNodoCombinado: " + TransicionDelNodoCombinado);
		}
	}
	
	public ArrayList<Nodo> eliminaPalabrasVacias(Nodo nodo, ArrayList<Nodo> ListaClausuras) {
		ArrayList<Nodo> clausuraDelEstado = new ArrayList<Nodo>();
		clausuraDelEstado.add(nodo);
		for(int i = 0; i < nodo.deltaDelNodo.size(); i++) {
			FuncionTransicion funcion = nodo.deltaDelNodo.get(i);
			if(funcion.simbolo.equals("#")) {
				clausuraDelEstado.add(funcion.output);
			}
		}
		if(clausuraDelEstado.size() >= 2) {
			Nodo clausura = new Nodo(clausuraDelEstado);
			if(nodo.esInicial) {
				this.ClausuraInicial = clausura;
			}
			nodo.nodoClausura = clausura;
			System.out.println("Clausura es" + clausura);
			ListaClausuras.add(clausura);
		}
		
		return ListaClausuras;
		
		
	}

	public ArrayList<Nodo> encontrarTransicionesDeUnSimbolo(Nodo nodo, String lenguaje, ArrayList<Nodo> listaNodosCombinacion) {
		//Añadir Nodos Combinados
		String[] lenguajeGrupo = lenguaje.split(" ");
		ArrayList<Nodo> listaEstados = new ArrayList<Nodo>();
		
		
		for(int i = 0; i < lenguajeGrupo.length; i++) {
			
			listaEstados = new ArrayList<Nodo>();
			for(int j = 0; j <  nodo.deltaDelNodo.size(); j++) {
				FuncionTransicion funcion = nodo.deltaDelNodo.get(j);
				//System.out.println("simboloFuncion: " + funcion.simbolo + " lenguajeGrupo " + lenguajeGrupo[i]);
				
				if(funcion.simbolo.equals(lenguajeGrupo[i])) {
					listaEstados.add(funcion.output);
				}
				
				
			}
			if(listaEstados.size() >= 2) {
				Nodo nodoCombinacion = new Nodo(listaEstados);
				//System.out.println("nodoCombinacion" + nodoCombinacion + " \n\n nodo " + nodo);
				nodo.QuitarFuncionTransicion(lenguajeGrupo[i]);
				//System.out.println("\n\n nodo " + nodo);
				if(AñadirSiNoExisteConElMismoNombre(listaNodosCombinacion, nodoCombinacion)) {
					nodo.AñadirFuncionTransicion(lenguajeGrupo[i], nodoCombinacion);
				}else {
					for(Nodo n : listaNodosCombinacion) {
						if(n.equals(nodoCombinacion)) {
							nodo.AñadirFuncionTransicion(lenguajeGrupo[i], n);
						}
					}
				}
				
				//System.out.println("\n\n nodo " + nodo);
				
			}
			
			
			
		}
		
		return listaNodosCombinacion;
	}
	
	public boolean AñadirSiNoExisteConElMismoNombre(ArrayList<Nodo> listaNodosCombinacion, Nodo nodoCombinacion) {
		for(Nodo n : listaNodosCombinacion) {
			//System.out.println("\n\n# " + n.nombre + " "  + nodoCombinacion.nombre + "\n#\n\n");
			if(n.equals(nodoCombinacion)) {
				return false;
			}
		}
		listaNodosCombinacion.add(nodoCombinacion);
		return true;
	}
	
	
	

	
	public boolean comprobarSiEsDeterminista() {
		
		
		for(int i = 0; i < Nodos.size(); i++) {
			Nodo nodo = Nodos.get(i);
			String simbolosUsados = "#";
			for(int j = 0; j < nodo.deltaDelNodo.size(); j++) {
				String simbolo = nodo.deltaDelNodo.get(j).simbolo;
				
				
				if(simbolosUsados.contains(simbolo)) {
					return false;
				}else {
					simbolosUsados += simbolo;
				}
				
			}
		}
		return true;
	}
	
	
	
	public void Leer(String palabra) {
		String estadoActual = this.getEstadoInicial();
		int indexNodo = buscarNodoPorEstado(estadoActual);
		System.out.println("_" + palabra + estadoActual);
		boolean SeUsoClausuraInicial = false;
		
		
		Nodo nodo = Nodos.get(indexNodo);
		
		
		for(int i = 0; i < palabra.length(); i++) {
			
			//System.out.println("simbolo: " + String.valueOf(palabra.charAt(i)));
			//System.out.println("estado " + estadoActual + " " + estadoActual);
			Nodo NodoOutput = nodo.AplicarTransicion(String.valueOf(palabra.charAt(i)));
			if(NodoOutput != null) {
				estadoActual = nodo.AplicarTransicion(String.valueOf(palabra.charAt(i))).nombre;
			}else {
				if(this.ClausuraInicial != null && this.ClausuraInicial.AplicarTransicion(String.valueOf(palabra.charAt(i))) != null && !SeUsoClausuraInicial) {
					System.out.println("_" + palabra + this.ClausuraInicial.nombre);
					estadoActual = this.ClausuraInicial.AplicarTransicion(String.valueOf(palabra.charAt(i))).nombre;
					SeUsoClausuraInicial = true;
					
				}else {
					System.out.println("Rechazado");
					return;
				}
			}
			
			
			if(estadoActual == null) {
				System.out.println("Rechazado");
				return;
			}
			
			indexNodo = buscarNodoPorEstado(estadoActual);
			
			nodo = Nodos.get(indexNodo);
			OutputStep(estadoActual, palabra, i);
		}
		
		if(nodo.esFinal) {
			System.out.println("Aceptado");
		}else {
			System.out.println("Rechazado");
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
			int indexOfStateOutput = buscarNodoPorEstado(nodoOutput);
			
			Nodos.get(indexOfState).AñadirFuncionTransicion(simbolo, Nodos.get(indexOfStateOutput));
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
		System.out.println("\n Print Nodes \n");
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
		
		String[] sPartida = sToCheck.split(" ");
		String[] estadosPartida = this.getEstados().split(" ");
		
		for(int i = 0; i < sPartida.length; i++) {
			
			if(!checkIfElementIsIn(sPartida[i], estadosPartida)) {
				return false;
			}
		}
		return true;
	}
	public boolean checkIfElementIsIn(String elemento, String[] conjunto) {
		for(int i = 0; i < conjunto.length; i++) {
			
			if(elemento.equals(conjunto[i])) {
				return true;
			}
		}
		return false;
	}
	
	public void crearNodos(String estados, String estadosFinales) {
		boolean esFinal;
		boolean esInicial = false;
		String[] estado = estados.split(" ");
		String[] grupoEstadosFinales = estadosFinales.split(" ");
		for(int i = 0; i < estado.length; i++) {
			if(estado[i].equals(this.estadoInicial)){ 
				esInicial = true;
			}
			if(checkIfElementIsIn(estado[i], grupoEstadosFinales)) {
				esFinal = true;
			}else {
				esFinal = false;
			}
			Nodo nodo = new Nodo(estado[i], esFinal);
			nodo.esInicial = esInicial;
			Nodos.add(nodo);
		}	
	}
	
	public Nodo[][] CrearMatriz(String estados, String estadoInicial, String estadosFinales, String lenguaje, String transicion) {
		
		String listaEstados = estados.replaceAll("\\s", "");
		String listaLenguaje = lenguaje.replaceAll("\\s", "") + "#";
		
		int value = listaEstados.indexOf("A");
				
		
		Nodo[][] matriz = new Nodo[listaLenguaje.length()][listaEstados.length()];
		
		String[] transicionGrupo = transicion.split(" ");		
		for(int i = 0; i < transicionGrupo.length; i++) {
			String FromState = String.valueOf(transicionGrupo[i].charAt(1));
			String simbolo = String.valueOf(transicionGrupo[i].charAt(3));
			Nodo nodoOutput = this.Nodos.get(buscarNodoPorEstado(String.valueOf(transicionGrupo[i].charAt(5))));
	
			
			int indexLenguaje = listaLenguaje.indexOf(simbolo);
			int indexEstado = listaEstados.indexOf(FromState);
			
			matriz[indexLenguaje][indexEstado] = nodoOutput;
		}
		/*System.out.println("matriz: ");
		for(int i = 0; i < listaLenguaje.length(); i++) {
			String line = "[";
			for(int j = 0; j < listaEstados.length(); j++) {
				if(matriz[i][j] != null ) {
					line += matriz[i][j].nombre;
				}else line += "null"; 
				line += " ";
			}
			line += "]";
			System.out.println(line);
		}
		
		*/
		
		return matriz;
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
			if(!(checkIfElementIsIn(input, estadosGrupo) && checkIfElementIsIn(output, estadosGrupo)) ) {				//System.out.println("input: " + input + " output: " + output + " transicion: " + transicion + "transicion[" + i + "]: '" + funcion + "'");
				throw new ErrorAutomata(IErrores.ERROR_ESTADO_NO_EXISTE, "Error en la linea 5 \nEstado no existe");
			}
			String simbolo = String.valueOf(funcion.charAt(3));
			String[] lenguajeGrupo = this.getLenguaje().split(" ");
			
			if(!(checkIfElementIsIn(simbolo, lenguajeGrupo) || simbolo.equals("#")) ) {
				throw new ErrorAutomata(IErrores.ERROR_CARACTER_NO_ES_PARTE_DEL_LENGUAJE, "Error en la linea 5\nCaracter no existe");
			}
	
		}
		
		this.transicion = transicion;
		
	}
	
	
	
	
	
	
}
