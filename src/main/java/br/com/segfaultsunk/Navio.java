package br.com.segfaultsunk;

import java.util.List;

public final class Navio extends Unidade {
    public static final int TAMANHO = 3;

    public Navio(String id, List<Coordenada> posicoes) {
        super(id, posicoes);
        if (posicoes.size() != TAMANHO || !alinhadasEContiguas(posicoes)) {
            throw new IllegalArgumentException("Cada navio deve ocupar três células contíguas em linha ou coluna.");
        }
    }

    @Override public int getTamanho() { return TAMANHO; }

    private static boolean alinhadasEContiguas(List<Coordenada> posicoes) {
        boolean mesmaLinha = posicoes.stream().allMatch(c -> c.getLinha() == posicoes.get(0).getLinha());
        boolean mesmaColuna = posicoes.stream().allMatch(c -> c.getColuna() == posicoes.get(0).getColuna());
        int menorLinha = posicoes.stream().mapToInt(Coordenada::getLinha).min().getAsInt();
        int maiorLinha = posicoes.stream().mapToInt(Coordenada::getLinha).max().getAsInt();
        int menorColuna = posicoes.stream().mapToInt(Coordenada::getColuna).min().getAsInt();
        int maiorColuna = posicoes.stream().mapToInt(Coordenada::getColuna).max().getAsInt();
        return (mesmaLinha && maiorColuna - menorColuna == TAMANHO - 1)
                || (mesmaColuna && maiorLinha - menorLinha == TAMANHO - 1);
    }
}
