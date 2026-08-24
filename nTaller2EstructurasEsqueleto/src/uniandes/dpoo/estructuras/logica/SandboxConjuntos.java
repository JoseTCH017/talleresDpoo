package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class SandboxConjuntos {

    private NavigableSet<String> arbolCadenas;

    public SandboxConjuntos() {
        arbolCadenas = new TreeSet<String>();
    }

    public List<String> getCadenasComoLista() {
        return new ArrayList<String>(arbolCadenas);
    }

    public List<String> getCadenasComoListaInvertida() {
        List<String> lista = new ArrayList<String>(arbolCadenas);
        Collections.reverse(lista);
        return lista;
    }

    public String getPrimera() {
        if (arbolCadenas.isEmpty()) return null;
        return arbolCadenas.first();
    }

    public String getUltima() {
        if (arbolCadenas.isEmpty()) return null;
        return arbolCadenas.last();
    }

    public Collection<String> getSiguientes(String cadena) {
        return arbolCadenas.tailSet(cadena, true);
    }

    public int getCantidadCadenas() {
        return arbolCadenas.size();
    }

    public void agregarCadena(String cadena) {
        arbolCadenas.add(cadena);
    }

    public void eliminarCadena(String cadena) {
        arbolCadenas.remove(cadena);
    }

    public void eliminarCadenaSinMayusculasOMinusculas(String cadena) {
        String aEliminar = null;
        for (String elemento : arbolCadenas) {
            if (elemento.equalsIgnoreCase(cadena)) {
                aEliminar = elemento;
                break;
            }
        }
        if (aEliminar != null) {
            arbolCadenas.remove(aEliminar);
        }
    }

    public void eliminarPrimera() {
        if (!arbolCadenas.isEmpty()) {
            arbolCadenas.pollFirst();
        }
    }

    public void reiniciarConjuntoCadenas(List<Object> objetos) {
        arbolCadenas.clear();
        for (Object obj : objetos) {
            arbolCadenas.add(obj.toString());
        }
    }

    public void volverMayusculas() {
        NavigableSet<String> temporal = new TreeSet<String>();
        for (String cadena : arbolCadenas) {
            temporal.add(cadena.toUpperCase());
        }
        arbolCadenas = temporal;
    }

    public TreeSet<String> invertirCadenas() {
        TreeSet<String> invertido = new TreeSet<String>(Collections.reverseOrder());
        invertido.addAll(arbolCadenas);
        return invertido;
    }

    public boolean compararElementos(String[] otroArreglo) {
        for (String elemento : otroArreglo) {
            if (!arbolCadenas.contains(elemento)) {
                return false;
            }
        }
        return true;
    }
}