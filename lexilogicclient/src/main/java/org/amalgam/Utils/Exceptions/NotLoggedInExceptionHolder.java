package org.amalgam.Utils.Exceptions;

/**
* org/amalgam/Utils/Exceptions/NotLoggedInExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ProgramUtilities.idl
* Wednesday, May 29, 2024 9:39:28 PM PST
*/

public final class NotLoggedInExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public NotLoggedInException value = null;

  public NotLoggedInExceptionHolder ()
  {
  }

  public NotLoggedInExceptionHolder (NotLoggedInException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = NotLoggedInExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    NotLoggedInExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return NotLoggedInExceptionHelper.type ();
  }

}
