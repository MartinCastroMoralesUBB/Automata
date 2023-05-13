import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Main {
	Automata miAutomata;
	public static void main(String[] args) {
		
		//ReadFromCMD();
		System.out.println("####### Input #######");
		ReadFromFile("test");
		
		
	}
	public static void ReadFromFile(String text) {
		Automata miAutomata;
		try {
			File file = new File("src/" + text + ".txt"); 
			Scanner myReader = new Scanner(file);
				String estados = myReader.nextLine();
				System.out.println(estados);
				
				String lenguaje = myReader.nextLine();
				System.out.println(lenguaje);
				
				String estadoInicial = myReader.nextLine();
				System.out.println(estadoInicial);
				
				String estadosFinales = myReader.nextLine();
				System.out.println(estadosFinales);
				
				String transicion = myReader.nextLine();
				System.out.println(transicion);
				
				String palabra = myReader.nextLine();
				System.out.println(palabra);
			
				System.out.println("####### Testing Phase #######");
			
				try {
					miAutomata = new Automata(estados, lenguaje, estadoInicial, estadosFinales, transicion);
					System.out.println("####### Output #######");
					miAutomata.Leer(palabra);
				} catch (ErrorAutomata e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			
			myReader.close();
			
			
			
		} catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		      e.printStackTrace();
		      
		}
		
		
		
		
			
		
	}
	public static void ReadFromCMD() {
		System.out.println("hello");
		Scanner myScanner = new Scanner(System.in);
		System.out.println("input");
		
		String estados = myScanner.nextLine();
		String lenguaje = myScanner.nextLine();
		String estadoInicial = myScanner.nextLine();
		String estadosFinales = myScanner.nextLine();
		String transicion = myScanner.nextLine();
		String palabra = myScanner.nextLine();
		
		System.out.println("linea1 is : " + estados);
		System.out.println("linea1 is : " + lenguaje);
		System.out.println("linea1 is : " + estadoInicial);
		System.out.println("linea1 is : " + estadosFinales);
		System.out.println("linea1 is : " + transicion);
		System.out.println("linea1 is : " + palabra);
		
		try {
			Automata miAutomata = new Automata(estados, lenguaje, estadoInicial, estadosFinales, transicion);
		} catch (ErrorAutomata e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
