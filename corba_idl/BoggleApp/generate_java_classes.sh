#!/bin/bash
#



deleteExistingCompiled() {
	if [ -d "compiled" ]; then
		rm -r compiled/
		echo "deleted existing compiled classes"
	fi
}

compileServer(){
	idlj -td compiled/server -fall GameService.idl
	idlj -td compiled/server -fall PlayerService.idl
	idlj -td compiled/server -fall ProgramUtilities.idl
	idlj -td compiled/server -fclient UIControllers.idl
}

compileClient(){
	idlj -td compiled/client -fclient GameService.idl
	idlj -td compiled/client -fclient PlayerService.idl
	idlj -td compiled/client -fclient ProgramUtilities.idl
	idlj -td compiled/client -fall UIControllers.idl
}


deleteExistingCompiled
mkdir compiled
mkdir compiled/server
mkdir compiled/client
compileServer
compileClient
#
