package br.com.segfaultsunk;

import java.util.Random;
import java.util.Scanner;

public final class App {
    private App() { }

    public static void main(String[] args) {
        Random aleatorio = new Random();
        TelaConsole tela = new TelaConsole(new Scanner(System.in), System.out);
        new ControladorJogo(tela, new FabricaPartida(aleatorio), new IAComputador(aleatorio)).executar();
    }
}
