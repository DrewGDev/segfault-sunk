package br.com.segfaultsunk;

import java.util.Objects;

/** Posição imutável de um tabuleiro 10 x 10. */
public final class Coordenada {
    private final int linha;
    private final int coluna;

    public Coordenada(int linha, int coluna) {
        if (linha < 0 || linha >= Tabuleiro.TAMANHO || coluna < 0 || coluna >= Tabuleiro.TAMANHO) {
            throw new IllegalArgumentException("Coordenada fora do tabuleiro.");
        }
        this.linha = linha;
        this.coluna = coluna;
    }

    public static Coordenada interpretar(String entrada) {
        if (entrada == null || !entrada.trim().toUpperCase().matches("[A-J](10|[1-9])")) {
            throw new IllegalArgumentException("Use uma coordenada entre A1 e J10, por exemplo B4.");
        }
        String valor = entrada.trim().toUpperCase();
        return new Coordenada(Integer.parseInt(valor.substring(1)) - 1, valor.charAt(0) - 'A');
    }

    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }

    @Override public boolean equals(Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof Coordenada)) return false;
        Coordenada c = (Coordenada) outro;
        return linha == c.linha && coluna == c.coluna;
    }

    @Override public int hashCode() { return Objects.hash(linha, coluna); }
    @Override public String toString() { return "" + (char) ('A' + coluna) + (linha + 1); }
}
