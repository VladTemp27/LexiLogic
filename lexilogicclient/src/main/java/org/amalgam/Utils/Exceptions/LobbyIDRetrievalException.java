package org.amalgam.Utils.Exceptions;


/**
* org/amalgam/Utils/Exceptions/LobbyIDRetrievalException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ProgramUtilities.idl
* Wednesday, May 29, 2024 9:39:28 PM PST
*/

public final class LobbyIDRetrievalException extends org.omg.CORBA.UserException
{
  public String message = null;

  public LobbyIDRetrievalException ()
  {
    super(LobbyIDRetrievalExceptionHelper.id());
  } // ctor

  public LobbyIDRetrievalException (String _message)
  {
    super(LobbyIDRetrievalExceptionHelper.id());
    message = _message;
  } // ctor


  public LobbyIDRetrievalException (String $reason, String _message)
  {
    super(LobbyIDRetrievalExceptionHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class LobbyIDRetrievalException
