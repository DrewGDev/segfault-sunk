package br.com.segfaultsunk;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Base abstrata das unidades. Métodos de instância concretos são sobrescrevíveis em Java. */
public abstract class Unidade {
    private final String id;
    private final Set<Coordenada> posicoes;
    private final Set<Coordenada> impactos = new HashSet<>();

    protected Unidade(String id, List<Coordenada> posicoes) {
        if (id == null || id.trim().isEmpty() || posicoes == null || posicoes.isEmpty()
                || posicoes.contains(null) || new HashSet<>(posicoes).size() != posicoes.size()) {
            throw new IllegalArgumentException("Unidade com identificação ou posições inválidas.");
        }
        this.id = id;
        this.posicoes = new HashSet<>(posicoes);
    }

    public abstract int getTamanho();

    public String getId() { return id; }
    public Set<Coordenada> getPosicoes() { return Collections.unmodifiableSet(posicoes); }
    public Set<Coordenada> getImpactos() { return Collections.unmodifiableSet(impactos); }
    public boolean ocupa(Coordenada coordenada) { return posicoes.contains(coordenada); }

    public boolean receberDisparo(Coordenada coordenada) {
        if (!ocupa(coordenada)) return false;
        impactos.add(coordenada);
        return true;
    }

    public boolean estaDestruida() { return impactos.size() == posicoes.size(); }
}
