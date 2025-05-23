package org.amalgam.Service.GameServiceModule;


/**
* org/amalgam/Service/GameServiceModule/_GameServiceStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from GameService.idl
* Wednesday, May 29, 2024 9:39:28 PM PST
*/

public class _GameServiceStub extends org.omg.CORBA.portable.ObjectImpl implements GameService
{

  public String matchMake (org.amalgam.UIControllers.PlayerCallback player_callback) throws org.amalgam.Utils.Exceptions.MatchCreationFailedException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("matchMake", true);
                org.amalgam.Service.GameServiceModule.GameServicePackage.PlayerCallbackHelper.write ($out, player_callback);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/MatchCreationFailedException:1.0"))
                    throw org.amalgam.Utils.Exceptions.MatchCreationFailedExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return matchMake (player_callback        );
            } finally {
                _releaseReply ($in);
            }
  } // matchMake

  public void readyHandshake (String username, int callback)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("readyHandshake", true);
                $out.write_string (username);
                $out.write_long (callback);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                readyHandshake (username, callback        );
            } finally {
                _releaseReply ($in);
            }
  } // readyHandshake

  public char[][] fetchWordBox (int roomID) throws org.amalgam.Utils.Exceptions.WordFetchFailedException, org.amalgam.Utils.Exceptions.InvalidRoomIDException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("fetchWordBox", true);
                $out.write_long (roomID);
                $in = _invoke ($out);
                char $result[][] = org.amalgam.Service.GameServiceModule.GameServicePackage.MatrixHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/WordFetchFailedException:1.0"))
                    throw org.amalgam.Utils.Exceptions.WordFetchFailedExceptionHelper.read ($in);
                else if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/InvalidRoomIDException:1.0"))
                    throw org.amalgam.Utils.Exceptions.InvalidRoomIDExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return fetchWordBox (roomID        );
            } finally {
                _releaseReply ($in);
            }
  } // fetchWordBox

  public String getLeaderboards () throws org.amalgam.Utils.Exceptions.EmptyLeaderBoardException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getLeaderboards", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/EmptyLeaderBoardException:1.0"))
                    throw org.amalgam.Utils.Exceptions.EmptyLeaderBoardExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getLeaderboards (        );
            } finally {
                _releaseReply ($in);
            }
  } // getLeaderboards

  public void verifyWord (String word, String username, int gameRoomID) throws org.amalgam.Utils.Exceptions.InvalidWordFormatException, org.amalgam.Utils.Exceptions.DuplicateWordException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("verifyWord", true);
                $out.write_string (word);
                $out.write_string (username);
                $out.write_long (gameRoomID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/InvalidWordFormatException:1.0"))
                    throw org.amalgam.Utils.Exceptions.InvalidWordFormatExceptionHelper.read ($in);
                else if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/DuplicateWordException:1.0"))
                    throw org.amalgam.Utils.Exceptions.DuplicateWordExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                verifyWord (word, username, gameRoomID        );
            } finally {
                _releaseReply ($in);
            }
  } // verifyWord

  public int validateTotalPoints () throws org.amalgam.Utils.Exceptions.InsufficientWordPointsException, org.amalgam.Utils.Exceptions.InvalidTotalPointsException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("validateTotalPoints", true);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/InsufficientWordPointsException:1.0"))
                    throw org.amalgam.Utils.Exceptions.InsufficientWordPointsExceptionHelper.read ($in);
                else if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/InvalidTotalPointsException:1.0"))
                    throw org.amalgam.Utils.Exceptions.InvalidTotalPointsExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return validateTotalPoints (        );
            } finally {
                _releaseReply ($in);
            }
  } // validateTotalPoints

  public String fetchWinner (int lobbyId) throws org.amalgam.Utils.Exceptions.LobbyDoesNotExistException, org.amalgam.Utils.Exceptions.WinnerDoesNotExistException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("fetchWinner", true);
                $out.write_long (lobbyId);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/LobbyDoesNotExistException:1.0"))
                    throw org.amalgam.Utils.Exceptions.LobbyDoesNotExistExceptionHelper.read ($in);
                else if (_id.equals ("IDL:org/amalgam/Utils/Exceptions/WinnerDoesNotExistException:1.0"))
                    throw org.amalgam.Utils.Exceptions.WinnerDoesNotExistExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return fetchWinner (lobbyId        );
            } finally {
                _releaseReply ($in);
            }
  } // fetchWinner

  public String playerReady (String username, int gameRoomID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("playerReady", true);
                $out.write_string (username);
                $out.write_long (gameRoomID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return playerReady (username, gameRoomID        );
            } finally {
                _releaseReply ($in);
            }
  } // playerReady

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:org/amalgam/Service/GameServiceModule/GameService:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     com.sun.corba.se.impl.orbutil.IORCheckImpl.check(str, "org.amalgam.Service.GameServiceModule._GameServiceStub");
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _GameServiceStub
