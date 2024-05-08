@echo off

call :main
endlocal

:generateServer

idlj -td compiled\server -fall PlayerService.idl
idlj -td compiled\server -fall GameService.idl
idlj -td compiled\server -fall ProgramUtilities.idl

goto :eof

:generateClient

idlj -td compiled\client -fclient PlayerService.idl
idlj -td compiled\client -fclient GameService.idl
idlj -td compiled\client -fall ProgramUtilities.idl

goto :eof


:main
    call :generateServer
    call :generateClient