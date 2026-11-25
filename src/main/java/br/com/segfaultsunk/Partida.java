package br.com.segfaultsunk;

public final class Partida {
    private final Jogador humano;
    private final Jogador computador;
    private TipoJogador turno = TipoJogador.HUMANO;
    private StatusPartida status = StatusPartida.EM_ANDAMENTO;

    public Partida(Jogador humano, Jogador computador) {
        if (humano == null || computador == null || humano.getTipo() != TipoJogador.HUMANO
                || computador.getTipo() != TipoJogador.COMPUTADOR
                || humano.getTabuleiro().getUnidades().size() != Tabuleiro.NAVIOS_POR_LADO
                || computador.getTabuleiro().getUnidades().size() != Tabuleiro.NAVIOS_POR_LADO) {
            throw new IllegalArgumentException("A partida precisa de três navios para cada lado.");
        }
        this.humano = humano;
        this.computador = computador;
    }

    public ResultadoDisparo disparar(Coordenada alvo) {
        if (terminou()) throw new IllegalStateException("A partida já terminou.");
        ResultadoDisparo resultado = getAdversario().getTabuleiro().receberDisparo(alvo);
        if (getAdversario().getTabuleiro().todosDestruidos()) {
            status = turno == TipoJogador.HUMANO
                    ? StatusPartida.VITORIA_HUMANO : StatusPartida.VITORIA_COMPUTADOR;
        } else {
            turno = turno == TipoJogador.HUMANO ? TipoJogador.COMPUTADOR : TipoJogador.HUMANO;
        }
        return resultado;
    }

    public Jogador getAtual() { return turno == TipoJogador.HUMANO ? humano : computador; }
    public Jogador getAdversario() { return turno == TipoJogador.HUMANO ? computador : humano; }
    public Jogador getHumano() { return humano; }
    public Jogador getComputador() { return computador; }
    public TipoJogador getTurno() { return turno; }
    public StatusPartida getStatus() { return status; }
    public boolean terminou() { return status != StatusPartida.EM_ANDAMENTO; }
}
