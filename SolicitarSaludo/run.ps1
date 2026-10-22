$ErrorActionPreference = "Stop"
$env:JAVA_HOME = "C:\Program Files\Java\jdk-26.0.2.1"
Set-Location $PSScriptRoot
& "$PSScriptRoot\mvnw.cmd" javafx:run
