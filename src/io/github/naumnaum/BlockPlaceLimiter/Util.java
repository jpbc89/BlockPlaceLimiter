package io.github.naumnaum.BlockPlaceLimiter;

public class Util {
		
	public static boolean isNum(String s) {
		int size = s.length();

		for (int i = 0; i < size; i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		return size > 0;
	}
	
	public static String toLine(String[] s){
		String linha="";
		for (String i:s){
			linha=linha+i+'\n';
		}
		//linha = linha.substring(0, linha.length()-1);
		return linha;
	}
}
