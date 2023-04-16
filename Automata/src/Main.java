import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Main {
	public static void main(String[] args) {
		
		
		//ReadFromCMD();
		System.out.println("####### Input #######");
		ReadFromFile("test");
		System.out.println("####### Output #######");
		
	}
	public static void ReadFromFile(String text) {
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
				
				String Transicion = myReader.nextLine();
				System.out.println(Transicion);
				
				String palabra = myReader.nextLine();
				System.out.println(palabra);
			
			
				try {
					Automata miAutomata = new Automata(estados, lenguaje, estadoInicial, estadosFinales);
				} catch (ErrorAutomata e) {
					System.out.println(e.getMessage());
				}
			
			myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		      
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
		String Transicion = myScanner.nextLine();
		String palabra = myScanner.nextLine();
		
		System.out.println("linea1 is : " + estados);
		System.out.println("linea1 is : " + lenguaje);
		System.out.println("linea1 is : " + estadoInicial);
		System.out.println("linea1 is : " + estadosFinales);
		System.out.println("linea1 is : " + Transicion);
		System.out.println("linea1 is : " + palabra);
		
		try {
			Automata miAutomata = new Automata(estados, lenguaje, estadoInicial, estadosFinales);
		} catch (ErrorAutomata e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
