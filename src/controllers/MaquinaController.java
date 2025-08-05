package controllers;



import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import models.Maquina;

public class MaquinaController {
    public Stack<Maquina> filtrarPorSubred(List<Maquina> maquinas, int umbral) {
        Stack<Maquina> pila = new Stack<>();
        for (Maquina m : maquinas) {
            if (m.getSubred() > umbral) {
                pila.push(m);
            }
        }
        return pila;
    }

    public Set<Maquina> ordenarPorSubred(Stack<Maquina> maquinas) {
        TreeSet<Maquina> ordenado = new TreeSet<>(Comparator.comparingInt(m -> m.getSubred()));
        ordenado.addAll(maquinas);
        return ordenado;
    }

    public Map<Integer, Queue<Maquina>> agruparPorRiesgo(List<Maquina> maquinas) {
        Map<Integer, Queue<Maquina>> mapa = new HashMap<>();
        for (Maquina m : maquinas) {
            int riesgo = m.getRiesgo();
            mapa.putIfAbsent(riesgo, new LinkedList<>());
            mapa.get(riesgo).add(m);
        }
        return mapa;
    }

    public Stack<Maquina> explotarGrupo(Map<Integer, Queue<Maquina>> mapa) {
        Stack<Maquina> pila = new Stack<>();
        for (Queue<Maquina> queue : mapa.values()) {
            while (!queue.isEmpty()) {
                pila.push(queue.poll());
            }
        }
        return pila;
    }

}
