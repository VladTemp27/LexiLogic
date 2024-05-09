@echo off

call :main
endlocal

:generateServer

idlj -td compiled\server -fall PlayerService.idl
idlj -td compiled\server -fall GameService.idl
idlj -td compiled\server -fall ProgramUtilities.idl
idlj -td compiled\server -fclient UIControllers.idl

goto :eof

:generateClient

idlj -td compiled\client -fclient PlayerService.idl
idlj -td compiled\client -fclient GameService.idl
idlj -td compiled\client -fall ProgramUtilities.idl
idlj -td compiled\client -fall UIControllers.idl

goto :eof


:main
    if exist compiled\ (
        rd compiled /s /q
    )
    call :generateServer
    call :generateClient