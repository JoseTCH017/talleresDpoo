package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SandboxListas {

    private List<Integer> listaEnteros;
    private List<String> listaCadenas;

    public SandboxListas() {
        listaEnteros = new ArrayList<Integer>();
        listaCadenas = new LinkedList<String>();
    }

    public List<Integer> getCopiaEnteros() {
        return new ArrayList<Integer>(listaEnteros);
    }

    public List<String> getCopiaCadenas() {
        return new LinkedList<String>(listaCadenas);
    }

    public int[] getEnterosComoArreglo() {
        int[] arreglo = new int[listaEnteros.size()];
        for (int i = 0; i < listaEnteros.size(); i++) {
            arreglo[i] = listaEnteros.get(i);
        }
        return arreglo;
    }

    public int getCantidadEnteros() {
        return listaEnteros.size();
    }

    public int getCantidadCadenas() {
        return listaCadenas.size();
    }

    public void agregarEntero(int entero) {
        listaEnteros.add(entero);
    }

    public void agregarCadena(String cadena) {
        listaCadenas.add(cadena);
    }

    public void eliminarEntero(int valor) {
        // Usamos removeIf o iteramos hacia atrás para evitar saltarnos elementos
        for (int i = listaEnteros.size() - 1; i >= 0; i--) {
            if (listaEnteros.get(i) == valor) {
                listaEnteros.remove(i);
            }
        }
    }

    public void eliminarCadena(String cadena) {
        for (int i = listaCadenas.size() - 1; i >= 0; i--) {
            if (listaCadenas.get(i).equalsIgnoreCase(cadena)) {
                listaCadenas.remove(i);
            }
        }
    }

    public void insertarEntero(int entero, int posicion) {
        if (posicion < 0) {
            listaEnteros.add(0, entero);
        } else if (posicion >= listaEnteros.size()) {
            listaEnteros.add(entero);
        } else {
            listaEnteros.add(posicion, entero);
        }
    }

    public void eliminarEnteroPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < listaEnteros.size()) {
            listaEnteros.remove(posicion);
        }
    }

    public void reiniciarArregloEnteros(double[] valores) {
        listaEnteros.clear();
        for (int i = 0; i < valores.length; i++) {
            listaEnteros.add((int) valores[i]);
        }
    }

    public void reiniciarArregloCadenas(List<Object> objetos) {
        listaCadenas.clear();
        for (int i = 0; i < objetos.size(); i++) {
            listaCadenas.add(objetos.get(i).toString());
        }
    }

    public void volverPositivos() {
        for (int i = 0; i < listaEnteros.size(); i++) {
            if (listaEnteros.get(i) < 0) {
                listaEnteros.set(i, listaEnteros.get(i) * -1);
            }
        }
    }

    public void organizarEnteros() {
        Collections.sort(listaEnteros, Collections.reverseOrder());
    }

    public void organizarCadenas() {
        Collections.sort(listaCadenas);
    }

    public int contarApariciones(int valor) {
        int conteo = 0;
        for (int num : listaEnteros) {
            if (num == valor) conteo++;
        }
        return conteo;
    }

    public int contarApariciones(String cadena) {
        int conteo = 0;
        for (String str : listaCadenas) {
            if (str.equalsIgnoreCase(cadena)) conteo++;
        }
        return conteo;
    }

    public int contarEnterosRepetidos() {
        int repetidos = 0;
        List<Integer> revisados = new ArrayList<Integer>();
        
        for (int i = 0; i < listaEnteros.size(); i++) {
            int numero = listaEnteros.get(i);
            if (!revisados.contains(numero)) {
                if (contarApariciones(numero) > 1) {
                    repetidos++;
                }
                revisados.add(numero);
            }
        }
        return repetidos;
    }

    public boolean compararArregloEnteros(int[] otroArreglo) {
        if (listaEnteros.size() != otroArreglo.length) return false;
        for (int i = 0; i < listaEnteros.size(); i++) {
            if (listaEnteros.get(i) != otroArreglo[i]) return false;
        }
        return true;
    }

    public void generarEnteros(int cantidad, int minimo, int maximo) {
        listaEnteros.clear();
        for (int i = 0; i < cantidad; i++) {
            int aleatorio = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
            listaEnteros.add(aleatorio);
        }
    }
}