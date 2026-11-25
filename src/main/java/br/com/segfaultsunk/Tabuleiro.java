package br.com.segfaultsunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Tabuleiro {
    public static final int TAMANHO = 10;
    public static final int NAVIOS_POR_LADO = 3;

    private final List<Unidade> unidades = new ArrayList<>();
    private final Map<Coordenada, ResultadoDisparo> disparos = new HashMap<>();

    public void posicionar(Unidade unidade) {
        if (unidade == null || unidades.size() >= NAVIOS_POR_LADO || unidade.getTamanho() != Navio.TAMANHO) {
            throw new IllegalArgumentException("Não é possível adicionar esta unidade ao tabuleiro.");
        }
        for (Coordenada posicao : unidade.getPosicoes()) {
            if (unidades.stream().anyMatch(existente -> existente.ocupa(posicao))) {
                throw new IllegalArgumentException("Navios não podem ocupar a mesma célula.");
            }
        }
        unidades.add(unidade);
    }

    public ResultadoDisparo receberDisparo(Coordenada alvo) {
        if (alvo == null || disparos.containsKey(alvo)) {
            throw new IllegalArgumentException("Coordenada inválida ou já utilizada. Escolha outra.");
        }
        ResultadoDisparo resultado = ResultadoDisparo.AGUA;
        for (Unidade unidade : unidades) {
            if (unidade.receberDisparo(alvo)) {
                resultado = unidade.estaDestruida() ? ResultadoDisparo.AFUNDADO : ResultadoDisparo.ACERTO;
                break;
            }
        }
        disparos.put(alvo, resultado);
        return resultado;
    }

    public boolean todosDestruidos() {
        return unidades.size() == NAVIOS_POR_LADO && unidades.stream().allMatch(Unidade::estaDestruida);
    }

    public List<Unidade> getUnidades() { return Collections.unmodifiableList(unidades); }
    public Map<Coordenada, ResultadoDisparo> getDisparos() { return Collections.unmodifiableMap(disparos); }
    public boolean temNavio(Coordenada posicao) { return unidades.stream().anyMatch(u -> u.ocupa(posicao)); }
}
