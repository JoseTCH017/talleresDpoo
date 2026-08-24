package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SandboxMapas {

    private Map<String, String> mapaCadenas;

    public SandboxMapas() {
        mapaCadenas = new HashMap<String, String>();
    }

    public List<String> getValoresComoLista() {
        List<String> listaValores = new ArrayList<String>(mapaCadenas.values());
        Collections.sort(listaValores);
        return listaValores;
    }

    public List<String> getLlavesComoListaInvertida() {
        List<String> listaLlaves = new ArrayList<String>(mapaCadenas.keySet());
        Collections.sort(listaLlaves, Collections.reverseOrder());
        return listaLlaves;
    }

    public String getPrimera() {
        if (mapaCadenas.isEmpty()) return null;
        List<String> llaves = new ArrayList<String>(mapaCadenas.keySet());
        Collections.sort(llaves);
        return llaves.get(0);
    }

    public String getUltima() {
        if (mapaCadenas.isEmpty()) return null;
        List<String> valores = new ArrayList<String>(mapaCadenas.values());
        Collections.sort(valores);
        return valores.get(valores.size() - 1);
    }

    public Collection<String> getLlaves() {
        List<String> llavesMayusculas = new ArrayList<String>();
        for (String llave : mapaCadenas.keySet()) {
            llavesMayusculas.add(llave.toUpperCase());
        }
        return llavesMayusculas;
    }

    public int getCantidadCadenasDiferentes() {
        // Al meter los valores en una lista y mirar repetidos (o en un Set)
        ArrayList<String> unicos = new ArrayList<String>();
        for (String valor : mapaCadenas.values()) {
            if (!unicos.contains(valor)) {
                unicos.add(valor);
            }
        }
        return unicos.size();
    }

    public void agregarCadena(String cadena) {
        String invertida = "";
        for (int i = cadena.length() - 1; i >= 0; i--) {
            invertida += cadena.charAt(i);
        }
        mapaCadenas.put(invertida, cadena);
    }

    public void eliminarCadenaConLLave(String llave) {
        mapaCadenas.remove(llave);
    }

    public void eliminarCadenaConValor(String valor) {
        String llaveAEliminar = null;
        for (Map.Entry<String, String> pareja : mapaCadenas.entrySet()) {
            if (pareja.getValue().equals(valor)) {
                llaveAEliminar = pareja.getKey();
                break;
            }
        }
        if (llaveAEliminar != null) {
            mapaCadenas.remove(llaveAEliminar);
        }
    }

    public void reiniciarMapaCadenas(List<Object> objetos) {
        mapaCadenas.clear();
        for (Object obj : objetos) {
            String cadena = obj.toString();
            agregarCadena(cadena); // Reutilizamos la lógica de invertir
        }
    }

    public void volverMayusculas() {
        Map<String, String> nuevoMapa = new HashMap<String, String>();
        for (Map.Entry<String, String> pareja : mapaCadenas.entrySet()) {
            nuevoMapa.put(pareja.getKey().toUpperCase(), pareja.getValue());
        }
        mapaCadenas = nuevoMapa;
    }

    public boolean compararValores(String[] otroArreglo) {
        for (String elemento : otroArreglo) {
            if (!mapaCadenas.containsValue(elemento)) {
                return false;
            }
        }
        return true;
    }
}