package org.amalgam.Utils.Exceptions;

/**
* org/amalgam/Utils/Exceptions/LobbyIDRetrievalExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ProgramUtilities.idl
* Wednesday, May 29, 2024 9:39:27 PM PST
*/

public final class LobbyIDRetrievalExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public LobbyIDRetrievalException value = null;

  public LobbyIDRetrievalExceptionHolder ()
  {
  }

  public LobbyIDRetrievalExceptionHolder (LobbyIDRetrievalException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = LobbyIDRetrievalExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    LobbyIDRetrievalExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return LobbyIDRetrievalExceptionHelper.type ();
  }

}
