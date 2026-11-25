$raizProjeto = Split-Path -Parent $MyInvocation.MyCommand.Path
$compilador = $null

if ($env:JAVA_HOME) {
    $candidato = Join-Path $env:JAVA_HOME 'bin\javac.exe'
    if (Test-Path -LiteralPath $candidato) { $compilador = $candidato }
}

if (-not $compilador) {
    $comando = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($comando) { $compilador = $comando.Source }
}

if (-not $compilador) {
    $candidato = Get-ChildItem -Path 'C:\Program Files\JetBrains\*\jbr\bin\javac.exe' -ErrorAction SilentlyContinue |
        Select-Object -First 1 -ExpandProperty FullName
    if ($candidato) { $compilador = $candidato }
}

if (-not $compilador) {
    throw 'JDK não encontrado. Instale um JDK 11 ou superior ou configure JAVA_HOME.'
}

$executador = Join-Path (Split-Path -Parent $compilador) 'java.exe'
if (-not (Test-Path -LiteralPath $executador)) { throw 'java.exe não foi encontrado ao lado do compilador.' }

Push-Location $raizProjeto
try {
    $fontes = Get-ChildItem 'src\main\java' -Recurse -Filter '*.java' | ForEach-Object FullName
    New-Item -ItemType Directory -Force 'build\classes' | Out-Null
    & $compilador -encoding UTF-8 -d 'build\classes' $fontes
    if ($LASTEXITCODE -ne 0) { throw 'Falha na compilação do jogo.' }
    & $executador -cp 'build\classes' br.com.segfaultsunk.App
} finally {
    Pop-Location
}
