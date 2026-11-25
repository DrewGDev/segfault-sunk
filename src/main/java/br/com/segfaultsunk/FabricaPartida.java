package br.com.segfaultsunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Cria posições válidas para os seis navios no início do protótipo. */
public final class FabricaPartida {
    private final Random aleatorio;

    public FabricaPartida(Random aleatorio) { this.aleatorio = aleatorio; }

    public Partida novaPartida() {
        return new Partida(
                new Jogador("Você", TipoJogador.HUMANO, criarTabuleiro("H")),
                new Jogador("Computador", TipoJogador.COMPUTADOR, criarTabuleiro("C")));
    }

    private Tabuleiro criarTabuleiro(String prefixo) {
        Tabuleiro tabuleiro = new Tabuleiro();
        for (int numero = 1; numero <= Tabuleiro.NAVIOS_POR_LADO; numero++) {
            boolean posicionado = false;
            while (!posicionado) {
                boolean horizontal = aleatorio.nextBoolean();
                int linha = aleatorio.nextInt(Tabuleiro.TAMANHO - (horizontal ? 0 : Navio.TAMANHO - 1));
                int coluna = aleatorio.nextInt(Tabuleiro.TAMANHO - (horizontal ? Navio.TAMANHO - 1 : 0));
                List<Coordenada> posicoes = new ArrayList<>();
                for (int i = 0; i < Navio.TAMANHO; i++) {
                    posicoes.add(new Coordenada(linha + (horizontal ? 0 : i), coluna + (horizontal ? i : 0)));
                }
                try {
                    tabuleiro.posicionar(new Navio(prefixo + numero, posicoes));
                    posicionado = true;
                } catch (IllegalArgumentException sobreposicao) {
                    // Tenta outra posição para evitar sobreposição.
                }
            }
        }
        return tabuleiro;
    }
}
