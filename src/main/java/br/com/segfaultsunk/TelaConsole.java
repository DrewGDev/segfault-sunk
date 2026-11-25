package br.com.segfaultsunk;

import java.io.PrintStream;
import java.util.Scanner;

public final class TelaConsole {
    private final Scanner scanner;
    private final PrintStream saida;

    public TelaConsole(Scanner scanner, PrintStream saida) {
        this.scanner = scanner;
        this.saida = saida;
    }

    public String lerEntrada() { return scanner.hasNextLine() ? scanner.nextLine() : null; }
    public void mostrarMensagem(String mensagem) { saida.println(mensagem); }

    public void mostrarPartida(Partida partida) {
        saida.println();
        saida.println("=== SEU TABULEIRO ===");
        mostrarTabuleiro(partida.getHumano().getTabuleiro(), true);
        saida.println("=== SEUS DISPAROS NO COMPUTADOR ===");
        mostrarTabuleiro(partida.getComputador().getTabuleiro(), false);
        saida.println("Legenda: N = navio, X = acerto, o = água, . = desconhecido");
        saida.println();
    }

    public void mostrarResultado(String autor, Coordenada alvo, ResultadoDisparo resultado) {
        String descricao;
        switch (resultado) {
            case AGUA: descricao = "água"; break;
            case ACERTO: descricao = "acertou um navio"; break;
            case AFUNDADO: descricao = "afundou um navio"; break;
            default: throw new IllegalStateException("Resultado desconhecido.");
        }
        saida.println(autor + " disparou em " + alvo + ": " + descricao + ".");
    }

    private void mostrarTabuleiro(Tabuleiro tabuleiro, boolean revelarNavios) {
        saida.println("    A B C D E F G H I J");
        for (int linha = 0; linha < Tabuleiro.TAMANHO; linha++) {
            saida.printf("%2d  ", linha + 1);
            for (int coluna = 0; coluna < Tabuleiro.TAMANHO; coluna++) {
                Coordenada posicao = new Coordenada(linha, coluna);
                ResultadoDisparo resultado = tabuleiro.getDisparos().get(posicao);
                char simbolo = resultado == null
                        ? (revelarNavios && tabuleiro.temNavio(posicao) ? 'N' : '.')
                        : (resultado == ResultadoDisparo.AGUA ? 'o' : 'X');
                saida.print(simbolo + " ");
            }
            saida.println();
        }
    }
}
