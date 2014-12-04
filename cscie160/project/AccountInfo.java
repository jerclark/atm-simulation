package cscie160.project;

import java.io.Serializable;


/** 
 * AccountInfo data class that stores an accountID and pin combination to be passed between the ATM and the Security authority.
 * Overrides equals() and hashCode() (using the eclipse generated versions) so that comparisons are performed on the values stored in the state.
 * 12/8/2011
 * @author  Jeremy Clark
 * @version 1.2
 */

public class AccountInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private final Integer _accountId;
	private Integer _pin;
	
	public AccountInfo(Integer accountId, Integer pin){
		_accountId = accountId;
		_pin = pin;
	}
	
	public int getAccountId(){
		return _accountId;
	}

	
	/**
	 * Eclipse generated hashCode override using both _accountId and _pin members
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_accountId == null) ? 0 : _accountId.hashCode());
		result = prime * result + ((_pin == null) ? 0 : _pin.hashCode());
		return result;
	}

	
	/**
	 * Eclipse generated equals override using both _accountId and _pin members
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountInfo other = (AccountInfo) obj;
		if (_accountId == null) {
			if (other._accountId != null)
				return false;
		} else if (!_accountId.equals(other._accountId))
			return false;
		if (_pin == null) {
			if (other._pin != null)
				return false;
		} else if (!_pin.equals(other._pin))
			return false;
		return true;
	}
	
	
}
