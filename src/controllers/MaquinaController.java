package controllers;



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
            if (m.getSubred() < umbral) {  // corregido menor que umbral
                pila.push(m);
            }
        }
        return pila;
    }
    public Set<Maquina> ordenarPorSubred(Stack<Maquina> maquinas) {
        Comparator<Maquina> comp = Comparator
            .comparingInt(Maquina::getSubred)
            .thenComparing(Maquina::getNombre);
    
        TreeSet<Maquina> ordenado = new TreeSet<>(comp);
        ordenado.addAll(maquinas);
        return ordenado;
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

        for (Map.Entry<Integer, Queue<Maquina>> entry : mapa.entrySet()) {
            int riesgo = entry.getKey();
            int cantidad = entry.getValue().size();

            if (cantidad > maxCantidad || (cantidad == maxCantidad && riesgo > maxRiesgo)) {
                maxCantidad = cantidad;
                maxRiesgo = riesgo;
                grupoSeleccionado = entry.getValue();
            }
        }

        Stack<Maquina> pila = new Stack<>();
        if (grupoSeleccionado != null) {
            List<Maquina> lista = new LinkedList<>(grupoSeleccionado);
            for (int i = lista.size() - 1; i >= 0; i--) {
                pila.push(lista.get(i));
            }
        }
        return pila;
    }

}
