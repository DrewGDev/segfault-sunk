package br.com.segfaultsunk;

public final class Jogador {
    private final String nome;
    private final TipoJogador tipo;
    private final Tabuleiro tabuleiro;

    public Jogador(String nome, TipoJogador tipo, Tabuleiro tabuleiro) {
        if (nome == null || nome.trim().isEmpty() || tipo == null || tabuleiro == null) {
            throw new IllegalArgumentException("Jogador inválido.");
        }
        this.nome = nome;
        this.tipo = tipo;
        this.tabuleiro = tabuleiro;
    }

    public String getNome() { return nome; }
    public TipoJogador getTipo() { return tipo; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
}
