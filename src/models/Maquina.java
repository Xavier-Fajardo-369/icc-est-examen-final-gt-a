package models;

import java.util.List;
import java.util.Objects;

public class Maquina {
    private String nombre;
    private String ip;
    private List<Integer> codigos;
    private int subred;
    private int riesgo;

    public Maquina(String nombre, String ip, List<Integer> codigos) {
        this.nombre = nombre;
        this.ip = ip;
        this.codigos = codigos;
        this.subred = calcularSubred();
        this.riesgo = calcularRiesgo();
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
        this.subred = calcularSubred(); // recalcula subred si cambia ip
    }

    public List<Integer> getCodigos() {
        return codigos;
    }
    public void setCodigos(List<Integer> codigos) {
        this.codigos = codigos;
        this.riesgo = calcularRiesgo(); // recalcula riesgo si cambian codigos
    }

    public int getSubred() {
        return subred;
    }
    public void setSubred(int subred) {
        this.subred = subred;
    }

    public int getRiesgo() {
        return riesgo;
    }
    public void setRiesgo(int riesgo) {
        this.riesgo = riesgo;
    }

    @Override
    public String toString() {
        return "Maquina [nombre=" + nombre + ", ip=" + ip + ", codigos=" + codigos + ", subred=" + subred + ", riesgo=" + riesgo + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, ip);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Maquina))
            return false;
        Maquina other = (Maquina) obj;
        return Objects.equals(nombre, other.nombre) && Objects.equals(ip, other.ip);
    }

    private int calcularSubred() {
        String[] partes = ip.split("\\.");
        if (partes.length != 4) {
            throw new IllegalArgumentException("IP inválida");
        }
        int subredSum = 0;
        for (String parte : partes) {
            subredSum += Integer.parseInt(parte);
        }
        return subredSum % 256;
    }

    private int calcularRiesgo() {
        int riesgo = 0;
        for (int codigo : codigos) {
            if (codigo >= 100 && codigo < 200) {
                riesgo += 1; // Riesgo bajo
            } else if (codigo >= 200 && codigo < 300) {
                riesgo += 2; // Riesgo medio
            } else if (codigo >= 300) {
                riesgo += 3; // Riesgo alto
            }
        }
        return riesgo;
    }
}

