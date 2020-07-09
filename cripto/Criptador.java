package cripto;

class Criptador {

    private final String abc = ",;.·:-/¡!¿?\"()[]{}'@#$%&=+*^€ºª0123456789 aAáÁàÀbBcCdDeEéÉèÈfFgGhHiIíÍìÌïjJkKlLmMnN" +
            "ñÑoOóÓòÒpPqQrRsStTuUúÚùÙüvVwWxXyYzZçÇ";

    private String hash;

    Criptador() {
    }

    // Convierte la clave en un string Hash (así puede ser lo largo que se quiera)
    protected void setClave(char[] clave) {
        /*
        Si la clave es la cadena vacía, el hash será "0" para que no dé errores a la hora de dividir entre 0 en el
        proceso de encriptado / descifrado
         */
        if (clave.length == 0) {
            hash = "0";
            return;
        }

        /*
         Matriz que contendrá los números que identifican cada carácter de la clave
         Tendrá dos columnas y tantas filas como se requieran
         Si alguna fila tiene un elemento de más, se rellenará con un 0
        */
        Matriz matriz = new Matriz((int) Math.ceil(clave.length / 2.), 2);

        int indiceClave = 0;
        int valor;
        for (int i = 0; i < matriz.getFilas(); i++) {
            for (int j = 0; j < matriz.getColumnas(); j++) {
                valor = (indiceClave < clave.length) ? clave[indiceClave++] : 0;
                matriz.setElemento(i, j, valor);
            }
        }

        /*
        El hash se obtendrá multiplicando la matriz anterior con una nueva (p) que se obtendrá a partir de la suma de
         los caracteres de la clave y la longitud de la clave
         */
        int sumaChars = sumaChars(clave);
        final int[][] p = {{clave.length, sumaChars % 10 + 2}, {(sumaChars / 10) % 10 + 2, clave.length + 1}};
        Matriz producto = matriz.producto(new Matriz(p));

        /*
        Para obtener el hash, se suman todos los elementos de la matriz producto y se suma a cada elemento de la
        matriz, posteriormente se invertirá el orden de los caracteres del string formado
         */
        int suma = producto.sumaElementos();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < producto.getFilas(); i++) {
            for (int j = 0; j < producto.getColumnas(); j++) {
                sb.append(producto.getElemento(i, j) + suma);
            }
        }

        hash = sb.reverse().toString();
    }

    // Descifra el texto con la clave proporcionada al Criptador
    protected String descifrar(String texto) {
        // Si el texto está compuesto por párrafos, los trata por separado
        if (texto.contains(System.lineSeparator())) {
            String[] parrafos = texto.split(System.lineSeparator());
            StringBuilder sb = new StringBuilder();

            for (String parrafo: parrafos) {
                sb.append(descifrar(parrafo));
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        }

        int[] arrayEncriptado = textoToIntArray(texto); // array de posiciones en abc de los caracteres de texto
        int[] arrayDescifrado = new int[arrayEncriptado.length]; // array de posiciones en abc de los caracteres
        // descifrados
        int[] arrayHash = hashToArray();

        int indice; // contendrá el valor de la posición en abc del carácter descifrado
        for (int i = 0; i < arrayEncriptado.length; i++) {
            indice = arrayEncriptado[i] + arrayHash[i % arrayHash.length] - i * i; // fórmula para descifrar

            // indice debe estar dentro de los límites de abc
            while (indice < 0) indice += abc.length();
            while (indice >= abc.length()) indice -= abc.length();

            arrayDescifrado[i] = indice;
        }

        return arrayToTexto(arrayDescifrado);
    }

    // Encripta el texto con la clave proporcionada al Criptador
    protected String encriptar(String texto) {
        // Si el texto está compuesto por párrafos, los trata por separado
        if (texto.contains(System.lineSeparator())) {
            String[] parrafos = texto.split(System.lineSeparator());
            StringBuilder sb = new StringBuilder();

            for (String parrafo: parrafos) {
                sb.append(encriptar(parrafo));
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        }

        int[] arrayDescifrado = textoToIntArray(texto); // array de posiciones en abc de los caracteres descifrados
        int[] arrayEncriptado = new int[arrayDescifrado.length]; // array de posiciones en abc de los caracteres de texto
        int[] arrayHash = hashToArray();

        int indice; // contendrá el valor de la posición en abc del carácter encriptado
        for (int i = 0; i < arrayDescifrado.length; i++) {
            indice = arrayDescifrado[i] - arrayHash[i % arrayHash.length] + i * i; // fórmula para encriptar

            // indice debe estar dentro de los límites de abc
            while (indice < 0) indice += abc.length();
            while (indice >= abc.length()) indice -= abc.length();

            arrayEncriptado[i] = indice;
        }

        return arrayToTexto(arrayEncriptado);
    }

    // Pasa el array de enteros (posiciones en abc) a un string
    private String arrayToTexto(int[] array) {
        StringBuilder texto = new StringBuilder();

        for (int i: array) texto.append(abc.charAt(i));

        return texto.toString();
    }

    // Pasa la variable miembro hash a un array formado por los enteros que representan sus caracteres
    private int[] hashToArray() {
        char[] arrayChar = hash.toCharArray();
        int[] arrayInt = new int[arrayChar.length];

        for (int i = 0; i < arrayChar.length; i++) arrayInt[i] = arrayChar[i];

        return arrayInt;
    }

    // Devuelve la suma de los códigos ASCII de todos los caracteres del array pasado por parámetro
    private int sumaChars(char[] texto) {
        int suma = 0;
        for (char c: texto) {
            suma += c;
        }

        return suma;
    }

    // Pasa el texto a un array de enteros formado por las posiciones de cada carácter en la variable miembro abc
    private int[] textoToIntArray(String texto) {
        int[] array = new int[texto.length()];

        for (int i = 0; i < array.length; i++) array[i] = abc.indexOf(texto.charAt(i));

        return array;
    }
}
