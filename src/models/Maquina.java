package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Maquina {
    private String nombre;
    private String ip;
    private List<Integer> codigos;
    private int subred;
    private int riesgo;

    public Maquina(String nombre, String ip, List<Integer> codigos) {
        if (nombre == null || ip == null || codigos == null) {
            throw new IllegalArgumentException("Argumentos no pueden ser nulos");
        }
        this.nombre = nombre;
        this.ip = ip;
        this.codigos = new ArrayList<>(codigos);
        this.subred = calcularSubred();
        this.riesgo = calcularRiesgo();
    }

    public String getNombre() { return nombre; }
    public String getIp() { return ip; }
    public List<Integer> getCodigos() { return new ArrayList<>(codigos); }
    public int getSubred() { return subred; }
    public int getRiesgo() { return riesgo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maquina)) return false;
        Maquina m = (Maquina) o;
        return subred == m.subred && nombre.equals(m.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, subred);
    }

    @Override
    public String toString() {
        return String.format("Maquina[nombre=%s, ip=%s, subred=%d, riesgo=%d]",
                nombre, ip, subred, riesgo);
    }

    // --- Implementación según README ---

    private int calcularSubred() {
        String[] partes = ip.split("\\.");
        if (partes.length != 4) {
            throw new IllegalArgumentException("IP inválida: " + ip);
        }
        return Integer.parseInt(partes[3]);
    }

    private int calcularRiesgo() {
        int sumaDiv5 = 0;
        for (int c : codigos) {
            if (c % 5 == 0) {
                sumaDiv5 += c;
            }
        }

        Set<Character> unicos = new HashSet<>();
        for (char ch : nombre.toCharArray()) {
            if (ch != ' ') {
                unicos.add(ch);
            }
        }

        return sumaDiv5 * unicos.size();
    }
}
