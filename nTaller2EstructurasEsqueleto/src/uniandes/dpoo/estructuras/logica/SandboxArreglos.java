package uniandes.dpoo.estructuras.logica;

import java.util.HashMap;

public class SandboxArreglos {

    private int[] arregloEnteros;
    private String[] arregloCadenas;

    public SandboxArreglos() {
        arregloEnteros = new int[]{};
        arregloCadenas = new String[]{};
    }

    public int[] getCopiaEnteros() {
        int[] copia = new int[arregloEnteros.length];
        for (int i = 0; i < arregloEnteros.length; i++) {
            copia[i] = arregloEnteros[i];
        }
        return copia;
    }

    public String[] getCopiaCadenas() {
        String[] copia = new String[arregloCadenas.length];
        for (int i = 0; i < arregloCadenas.length; i++) {
            copia[i] = arregloCadenas[i];
        }
        return copia;
    }

    public int getCantidadEnteros() {
        return arregloEnteros.length;
    }

    public int getCantidadCadenas() {
        return arregloCadenas.length;
    }

    public void agregarEntero(int entero) {
        int[] nuevo = new int[arregloEnteros.length + 1];
        for (int i = 0; i < arregloEnteros.length; i++) {
            nuevo[i] = arregloEnteros[i];
        }
        nuevo[arregloEnteros.length] = entero;
        arregloEnteros = nuevo;
    }

    public void agregarCadena(String cadena) {
        String[] nuevo = new String[arregloCadenas.length + 1];
        for (int i = 0; i < arregloCadenas.length; i++) {
            nuevo[i] = arregloCadenas[i];
        }
        nuevo[arregloCadenas.length] = cadena;
        arregloCadenas = nuevo;
    }

    public void eliminarEntero(int valor) {
        int conteo = contarApariciones(valor);
        if (conteo == 0) return; // Si no existe, no hacemos nada

        int[] nuevo = new int[arregloEnteros.length - conteo];
        int indice = 0;
        for (int i = 0; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] != valor) {
                nuevo[indice++] = arregloEnteros[i];
            }
        }
        arregloEnteros = nuevo;
    }

    public void eliminarCadena(String cadena) {
        int conteo = contarApariciones(cadena); // Cuenta ignorando mayúsculas/minúsculas
        if (conteo == 0) return;

        String[] nuevo = new String[arregloCadenas.length - conteo];
        int indice = 0;
        for (int i = 0; i < arregloCadenas.length; i++) {
            if (!arregloCadenas[i].equalsIgnoreCase(cadena)) {
                nuevo[indice++] = arregloCadenas[i];
            }
        }
        arregloCadenas = nuevo;
    }

    public void insertarEntero(int entero, int posicion) {
        if (posicion < 0) posicion = 0;
        if (posicion > arregloEnteros.length) posicion = arregloEnteros.length;

        int[] nuevo = new int[arregloEnteros.length + 1];
        for (int i = 0; i < posicion; i++) {
            nuevo[i] = arregloEnteros[i];
        }
        nuevo[posicion] = entero;
        for (int i = posicion; i < arregloEnteros.length; i++) {
            nuevo[i + 1] = arregloEnteros[i];
        }
        arregloEnteros = nuevo;
    }

    public void eliminarEnteroPorPosicion(int posicion) {
        if (posicion < 0 || posicion >= arregloEnteros.length) return;

        int[] nuevo = new int[arregloEnteros.length - 1];
        for (int i = 0; i < posicion; i++) {
            nuevo[i] = arregloEnteros[i];
        }
        for (int i = posicion + 1; i < arregloEnteros.length; i++) {
            nuevo[i - 1] = arregloEnteros[i];
        }
        arregloEnteros = nuevo;
    }

    public void reiniciarArregloEnteros(double[] valores) {
        arregloEnteros = new int[valores.length];
        for (int i = 0; i < valores.length; i++) {
            arregloEnteros[i] = (int) valores[i]; // Truncamiento automático al hacer casting
        }
    }

    public void reiniciarArregloCadenas(Object[] objetos) {
        arregloCadenas = new String[objetos.length];
        for (int i = 0; i < objetos.length; i++) {
            arregloCadenas[i] = objetos[i].toString();
        }
    }

    public void volverPositivos() {
        for (int i = 0; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] < 0) {
                arregloEnteros[i] = arregloEnteros[i] * -1;
            }
        }
    }

    public void organizarEnteros() {
        // Algoritmo de Burbuja básico para organizar de menor a mayor
        for (int i = 0; i < arregloEnteros.length - 1; i++) {
            for (int j = 0; j < arregloEnteros.length - 1 - i; j++) {
                if (arregloEnteros[j] > arregloEnteros[j + 1]) {
                    int temp = arregloEnteros[j];
                    arregloEnteros[j] = arregloEnteros[j + 1];
                    arregloEnteros[j + 1] = temp;
                }
            }
        }
    }

    public void organizarCadenas() {
        // Algoritmo de Burbuja utilizando compareTo para Strings
        for (int i = 0; i < arregloCadenas.length - 1; i++) {
            for (int j = 0; j < arregloCadenas.length - 1 - i; j++) {
                if (arregloCadenas[j].compareTo(arregloCadenas[j + 1]) > 0) {
                    String temp = arregloCadenas[j];
                    arregloCadenas[j] = arregloCadenas[j + 1];
                    arregloCadenas[j + 1] = temp;
                }
            }
        }
    }

    public int contarApariciones(int valor) {
        int contador = 0;
        for (int num : arregloEnteros) {
            if (num == valor) contador++;
        }
        return contador;
    }

    public int contarApariciones(String cadena) {
        int contador = 0;
        for (String str : arregloCadenas) {
            if (str.equalsIgnoreCase(cadena)) contador++; // equalsIgnoreCase omite mayúsculas/minúsculas
        }
        return contador;
    }

    public int[] buscarEntero(int valor) {
        int conteo = contarApariciones(valor);
        int[] posiciones = new int[conteo];
        int indice = 0;
        for (int i = 0; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] == valor) {
                posiciones[indice++] = i;
            }
        }
        return posiciones;
    }

    public int[] calcularRangoEnteros() {
        if (arregloEnteros.length == 0) return new int[]{};
        
        int min = arregloEnteros[0];
        int max = arregloEnteros[0];
        for (int i = 1; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] < min) min = arregloEnteros[i];
            if (arregloEnteros[i] > max) max = arregloEnteros[i];
        }
        return new int[]{min, max};
    }

    public HashMap<Integer, Integer> calcularHistograma() {
        HashMap<Integer, Integer> mapa = new HashMap<>();
        for (int num : arregloEnteros) {
            if (mapa.containsKey(num)) {
                mapa.put(num, mapa.get(num) + 1);
            } else {
                mapa.put(num, 1);
            }
        }
        return mapa;
    }

    public int contarEnterosRepetidos() {
        HashMap<Integer, Integer> histograma = calcularHistograma();
        int repetidos = 0;
        for (int cantidad : histograma.values()) {
            if (cantidad > 1) repetidos++;
        }
        return repetidos;
    }

    public boolean compararArregloEnteros(int[] otroArreglo) {
        if (arregloEnteros.length != otroArreglo.length) return false;
        for (int i = 0; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] != otroArreglo[i]) return false;
        }
        return true;
    }

    public boolean mismosEnteros(int[] otroArreglo) {
        if (arregloEnteros.length != otroArreglo.length) return false;
        
        // Creamos copias locales y las ordenamos para comparar frecuencias y valores
        int[] copiaLocal = getCopiaEnteros();
        int[] copiaOtro = new int[otroArreglo.length];
        for (int i = 0; i < otroArreglo.length; i++) copiaOtro[i] = otroArreglo[i];
        
        java.util.Arrays.sort(copiaLocal);
        java.util.Arrays.sort(copiaOtro);
        
        for (int i = 0; i < copiaLocal.length; i++) {
            if (copiaLocal[i] != copiaOtro[i]) return false;
        }
        return true;
    }

    public void generarEnteros(int cantidad, int minimo, int maximo) {
        arregloEnteros = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            // (Math.random() * (max - min + 1)) + min
            arregloEnteros[i] = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
        }
    }
}
