package ejb.test;

import java.util.Arrays;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote(RemoteInterface.class)
@DenyAll
@Stateless
public class RemoteBean implements RemoteInterface {

	public RemoteBean() {
	}

	@PermitAll
	@Override
	public boolean checkNames(String fsName) {
		List<String> moListOfNames = Arrays.asList("Kevin", "Jiten", "Martina",
				"Brian");
		boolean lboolNamePresent = false;
		if (fsName != null) {
			lboolNamePresent = moListOfNames.contains(fsName);
		}

		return lboolNamePresent;
	}

}
