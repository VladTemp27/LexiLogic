package org.amalgam;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.UIControllers.PlayerCallbackPOA;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.io.Serializable;

public class PlayerCallbackImpl extends PlayerCallbackPOA implements PlayerCallback, Serializable {
	@Override
	public String username() {
		return null;
	}

	@Override
	public void username(String newUsername) {

	}

	@Override
	public void uiCall(String jsonString) throws InvalidRequestException {

	}

	@Override
	public boolean _is_equivalent(Object other) {
		return false;
	}

	@Override
	public int _hash(int maximum) {
		return 0;
	}

	@Override
	public Object _duplicate() {
		return null;
	}

	@Override
	public void _release() {

	}

	@Override
	public Request _request(String operation) {
		return null;
	}

	@Override
	public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
		return null;
	}

	@Override
	public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exclist, ContextList ctxlist) {
		return null;
	}

	@Override
	public Policy _get_policy(int policy_type) {
		return null;
	}

	@Override
	public DomainManager[] _get_domain_managers() {
		return new DomainManager[0];
	}

	@Override
	public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
		return null;
	}
}
