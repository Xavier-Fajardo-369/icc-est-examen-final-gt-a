package controllers;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

import models.Maquina;

public class MaquinaController {

    // Método A: filtrarPorSubred (subred < umbral), mantiene orden original, retorna Stack
    public Stack<Maquina> filtrarPorSubred(List<Maquina> maquinas, int umbral) {
        Stack<Maquina> resultado = new Stack<>();
        for (Maquina m : maquinas) {
            if (m.getSubred() < umbral) {
                resultado.push(m);
            }
        }
        return resultado;
    }

    // Método B: ordenarPorSubred - TreeSet ordenado por subred DESC, nombre ASC
    public TreeSet<Maquina> ordenarPorSubred(Stack<Maquina> pila) {
        // Comparator: subred DESC, nombre ASC
        Comparator<Maquina> cmp = (m1, m2) -> {
            int cmpSubred = Integer.compare(m2.getSubred(), m1.getSubred()); // DESC
            if (cmpSubred != 0) return cmpSubred;
            return m1.getNombre().compareTo(m2.getNombre()); // ASC
        };

        TreeSet<Maquina> ordenado = new TreeSet<>(cmp);
        ordenado.addAll(pila);
        return ordenado;
    }

    // Método C: agruparPorRiesgo (TreeMap<Integer, Queue<Maquina>>)
    public TreeMap<Integer, Queue<Maquina>> agruparPorRiesgo(List<Maquina> maquinas) {
        TreeMap<Integer, Queue<Maquina>> mapa = new TreeMap<>();
        for (Maquina m : maquinas) {
            int riesgo = m.getRiesgo();
            mapa.putIfAbsent(riesgo, new LinkedList<>());
            mapa.get(riesgo).add(m);
        }
        return mapa;
    }

    // Método D: explotarGrupo - grupo con más máquinas; si empate, mayor riesgo; retorna Stack LIFO
    public Stack<Maquina> explotarGrupo(Map<Integer, Queue<Maquina>> mapa) {
        int maxCantidad = -1;
        int maxRiesgo = -1;
        Queue<Maquina> grupoSeleccionado = null;

        for (Map.Entry<Integer, Queue<Maquina>> entry : mapa.entrySet()) {
            int riesgo = entry.getKey();
            int cantidad = entry.getValue().size();

            if (cantidad > maxCantidad || (cantidad == maxCantidad && riesgo > maxRiesgo)) {
                maxCantidad = cantidad;
                maxRiesgo = riesgo;
                grupoSeleccionado = entry.getValue();
            }
        }

        Stack<Maquina> resultado = new Stack<>();
        if (grupoSeleccionado != null) {
            // Para LIFO invertimos el orden de la cola
            LinkedList<Maquina> aux = new LinkedList<>(grupoSeleccionado);
            Iterator<Maquina> it = aux.descendingIterator();
            while (it.hasNext()) {
                resultado.push(it.next());
            }
        }

        return resultado;
    }
}