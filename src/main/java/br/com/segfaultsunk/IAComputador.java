package br.com.segfaultsunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** Recebe apenas o histórico de disparos; não conhece os navios do jogador. */
public final class IAComputador {
    private final Random aleatorio;

    public IAComputador(Random aleatorio) { this.aleatorio = aleatorio; }

    public Coordenada escolherAlvo(Set<Coordenada> tentativas) {
        List<Coordenada> disponiveis = new ArrayList<>();
        for (int linha = 0; linha < Tabuleiro.TAMANHO; linha++) {
            for (int coluna = 0; coluna < Tabuleiro.TAMANHO; coluna++) {
                Coordenada alvo = new Coordenada(linha, coluna);
                if (!tentativas.contains(alvo)) disponiveis.add(alvo);
            }
        }
        if (disponiveis.isEmpty()) throw new IllegalStateException("Não restam coordenadas para disparar.");
        return disponiveis.get(aleatorio.nextInt(disponiveis.size()));
    }
}
