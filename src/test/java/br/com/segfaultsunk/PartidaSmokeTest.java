package br.com.segfaultsunk;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** Verifica regras centrais sem bibliotecas de teste externas. */
public final class PartidaSmokeTest {
    private PartidaSmokeTest() { }

    public static void main(String[] args) {
        conferir(Coordenada.interpretar(" j10 ").equals(new Coordenada(9, 9)), "leitura de coordenada");
        try {
            Coordenada.interpretar("K1");
            throw new AssertionError("coordenada inválida foi aceita");
        } catch (IllegalArgumentException esperado) { }

        Partida aleatoria = new FabricaPartida(new Random(3)).novaPartida();
        conferir(aleatoria.getHumano().getTabuleiro().getUnidades().size() == 3, "frota humana");
        conferir(aleatoria.getComputador().getTabuleiro().getUnidades().size() == 3, "frota do computador");
        for (Jogador jogador : Arrays.asList(aleatoria.getHumano(), aleatoria.getComputador())) {
            Set<Coordenada> ocupadas = new HashSet<>();
            for (Unidade unidade : jogador.getTabuleiro().getUnidades()) {
                conferir(unidade.getTamanho() == 3, "tamanho de navio");
                for (Coordenada posicao : unidade.getPosicoes()) {
                    conferir(ocupadas.add(posicao), "navios sobrepostos");
                }
            }
        }

        Tabuleiro humano = new Tabuleiro();
        Tabuleiro computador = new Tabuleiro();
        for (String coluna : Arrays.asList("A", "B", "C")) {
            humano.posicionar(new Navio("H" + coluna, Arrays.asList(
                    Coordenada.interpretar(coluna + "1"), Coordenada.interpretar(coluna + "2"),
                    Coordenada.interpretar(coluna + "3"))));
        }
        for (String coluna : Arrays.asList("D", "E", "F")) {
            computador.posicionar(new Navio("C" + coluna, Arrays.asList(
                    Coordenada.interpretar(coluna + "1"), Coordenada.interpretar(coluna + "2"),
                    Coordenada.interpretar(coluna + "3"))));
        }
        Partida partida = new Partida(new Jogador("Humano", TipoJogador.HUMANO, humano),
                new Jogador("Computador", TipoJogador.COMPUTADOR, computador));
        String[] tiros = {"D1", "D2", "D3", "E1", "E2", "E3", "F1", "F2", "F3"};
        for (int i = 0; i < tiros.length; i++) {
            ResultadoDisparo resultado = partida.disparar(Coordenada.interpretar(tiros[i]));
            conferir(resultado == (i % 3 == 2 ? ResultadoDisparo.AFUNDADO : ResultadoDisparo.ACERTO),
                    "resultado do disparo " + tiros[i]);
            if (i == tiros.length - 1) break;
            conferir(partida.getTurno() == TipoJogador.COMPUTADOR, "turno do computador");
            Coordenada tiroComputador = Coordenada.interpretar((char) ('A' + i) + "10");
            conferir(partida.disparar(tiroComputador) == ResultadoDisparo.AGUA, "tiro em água");
            conferir(partida.getTurno() == TipoJogador.HUMANO, "turno humano");
            if (i == 0) {
                try {
                    partida.disparar(Coordenada.interpretar("D1"));
                    throw new AssertionError("disparo repetido foi aceito");
                } catch (IllegalArgumentException esperado) {
                    conferir(partida.getTurno() == TipoJogador.HUMANO, "turno após disparo repetido");
                }
            }
        }
        conferir(partida.terminou(), "fim de partida");
        conferir(partida.getStatus() == StatusPartida.VITORIA_HUMANO, "vitória humana");
        conferir(computador.todosDestruidos(), "três navios destruídos");
        IAComputador ia = new IAComputador(new Random(1));
        conferir(!humano.getDisparos().containsKey(ia.escolherAlvo(humano.getDisparos().keySet())),
                "IA repetiu um disparo");
        System.out.println("Regras centrais: OK");
    }

    private static void conferir(boolean condicao, String descricao) {
        if (!condicao) throw new AssertionError(descricao);
    }
}
