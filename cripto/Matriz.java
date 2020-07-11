package cripto;

class Matriz {

    private final int filas;
    private final int columnas;
    private final int[][] matriz;

    Matriz(int filas, int columnas) {
        this.columnas = columnas;
        this.filas = filas;
        this.matriz = new int[filas][columnas];
    }

    Matriz(int[][] matriz) {
        this.columnas = matriz[0].length;
        this.filas = matriz.length;
        this.matriz = matriz;
    }

    protected int getColumnas() {
        return this.columnas;
    }

    protected int getElemento(int i, int j) {
        return this.matriz[i][j];
    }

    protected void setElemento(int i, int j, int valor) {
        this.matriz[i][j] = valor;
    }

    protected int getFilas() {
        return this.filas;
    }

    // Devuelve el producto de la matriz (variable miembro) y la matriz pasada por parámetro
    protected Matriz producto(Matriz matriz) {
        if (columnas != matriz.getFilas()) return null; // las matrices no se pueden multiplicar

        Matriz producto = new Matriz(filas, matriz.getColumnas());

        int valor;
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++) {
                valor = 0;
                for (int k = 0; k < matriz.getColumnas(); k++)
                    valor += this.matriz[i][k] * matriz.getElemento(k, j);

                producto.setElemento(i, j, valor);
            }

        return producto;
    }

    // Devuelve la suma de todos los elementos de la matriz
    protected int sumaElementos() {
        int suma = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                suma += matriz[i][j];
            }
        }
        return suma;
    }
}
