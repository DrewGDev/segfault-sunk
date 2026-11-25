package br.com.segfaultsunk;

public final class ControladorJogo {
    private final TelaConsole tela;
    private final FabricaPartida fabrica;
    private final IAComputador ia;

    public ControladorJogo(TelaConsole tela, FabricaPartida fabrica, IAComputador ia) {
        this.tela = tela;
        this.fabrica = fabrica;
        this.ia = ia;
    }

    public void executar() {
        Partida partida = fabrica.novaPartida();
        tela.mostrarMensagem("BATALHA NAVAL - PRIMEIRA VERSÃO");
        tela.mostrarMensagem("Você e o computador têm três navios de três células cada.");
        tela.mostrarMensagem("Informe um alvo entre A1 e J10. Digite S para sair.");
        while (!partida.terminou()) {
            tela.mostrarPartida(partida);
            if (partida.getTurno() == TipoJogador.HUMANO) {
                tela.mostrarMensagem("Seu turno. Coordenada do disparo: ");
                String entrada = tela.lerEntrada();
                if (entrada == null || entrada.trim().equalsIgnoreCase("S")) {
                    tela.mostrarMensagem("Partida encerrada.");
                    return;
                }
                try {
                    Coordenada alvo = Coordenada.interpretar(entrada);
                    ResultadoDisparo resultado = partida.disparar(alvo);
                    tela.mostrarResultado("Você", alvo, resultado);
                } catch (IllegalArgumentException erro) {
                    tela.mostrarMensagem(erro.getMessage() + " Seu turno continua.");
                }
            } else {
                Coordenada alvo = ia.escolherAlvo(partida.getHumano().getTabuleiro().getDisparos().keySet());
                ResultadoDisparo resultado = partida.disparar(alvo);
                tela.mostrarResultado("O computador", alvo, resultado);
            }
        }
        tela.mostrarPartida(partida);
        tela.mostrarMensagem(partida.getStatus() == StatusPartida.VITORIA_HUMANO
                ? "Fim da partida: você venceu!" : "Fim da partida: o computador venceu.");
    }
}
