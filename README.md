# Segfault Sunk - primeira versão

Protótipo de Batalha Naval em Java para console. Uma pessoa joga contra o computador em um tabuleiro 10 x 10. Cada lado tem três navios de três células. A cada turno, um lado dispara uma vez. O jogo informa água, acerto e navio afundado, e termina quando os três navios de um lado são destruídos.

## Como executar

É necessário ter um JDK 11 ou superior. No PowerShell, a partir da raiz do repositório:

```powershell
$arquivosJava = Get-ChildItem src/main/java -Recurse -Filter *.java | ForEach-Object FullName
New-Item -ItemType Directory -Force build/classes | Out-Null
javac -encoding UTF-8 -d build/classes $arquivosJava
java -cp build/classes br.com.segfaultsunk.App
```

Use uma coordenada de `A1` a `J10` para atacar. `S` encerra o programa. Seu tabuleiro revela seus navios (`N`); o tabuleiro adversário mostra apenas os disparos realizados. `X` indica acerto, `o` indica água e `.` indica posição ainda desconhecida. Uma coordenada inválida ou repetida não consome o turno.

## Estrutura

- `model`: `Partida`, `Jogador`, `Tabuleiro`, `Unidade`, `Navio`, `Coordenada` e `IAComputador` mantêm estado e regras.
- `view`: `TelaConsole` apresenta tabuleiros e mensagens e recebe a entrada digitada.
- `controller`: `ControladorJogo` coordena os turnos e a interação.

As classes estão no mesmo pacote Java para facilitar a compilação sem ferramentas adicionais. A separação de responsabilidades segue MVC. `Unidade` é abstrata, e `Navio` implementa seu método abstrato.

Esta é a primeira versão para demonstração e coleta de feedback. Salvar e retomar partidas, com classes exclusivas de leitura e gravação de arquivo, são requisitos da versão completa e ainda não estão implementados neste protótipo.

## Verificação das regras

```powershell
$arquivosTeste = Get-ChildItem src/test/java -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -cp build/classes -d build/classes $arquivosTeste
java -cp build/classes br.com.segfaultsunk.PartidaSmokeTest
```
