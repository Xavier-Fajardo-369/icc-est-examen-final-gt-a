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

import models.Maquina;

public class MaquinaController {
    public Stack<Maquina> filtrarPorSubred(List<Maquina> maquinas, int umbral) {
        Stack<Maquina> pila = new Stack<>();
        for (Maquina m : maquinas) {
            if (m.getSubred() > umbral) {  // corregido menor que umbral
                pila.push(m);
            }
        }
        return pila;
    }
    public Set<Maquina> ordenarPorSubred(Stack<Maquina> pila) {
        Comparator<Maquina> comp = (m1, m2) -> {
            int cmp = Integer.compare(m2.getSubred(), m1.getSubred()); // DESC
            if (cmp != 0) return cmp;
            return m1.getNombre().compareTo(m2.getNombre()); // ASC
        };

        return new TreeSet<>(comp) {{ addAll(pila); }};
    }

   
    public Map<Integer, Queue<Maquina>> agruparPorRiesgo(List<Maquina> maquinas) {
        Map<Integer, Queue<Maquina>> mapa = new TreeMap<>();
        for (Maquina m : maquinas) {
            int riesgo = m.getRiesgo();
            mapa.putIfAbsent(riesgo, new LinkedList<>());
            mapa.get(riesgo).add(m);
        }
        return mapa;
    }

    public Stack<Maquina> explotarGrupo(Map<Integer, Queue<Maquina>> mapa) {
        int maxCantidad = 0;
        int maxRiesgo = Integer.MIN_VALUE;
        Queue<Maquina> grupoSeleccionado = null;

        for (Integer riesgo : mapa.keySet()) {
            Queue<Maquina> grupo = mapa.get(riesgo);
            int cantidad = grupo.size();

            if (cantidad > maxCantidad || (cantidad == maxCantidad && riesgo > maxRiesgo)) {
                maxCantidad = cantidad;
                maxRiesgo = riesgo;
                grupoSeleccionado = grupo;
            }
        }

        Stack<Maquina> pila = new Stack<>();
        if (grupoSeleccionado != null) {
            List<Maquina> lista = new ArrayList<>(grupoSeleccionado);
            for (int i = lista.size() - 1; i >= 0; i--) {
                pila.push(lista.get(i));
            }
        }

        return pila;
    }  

}
