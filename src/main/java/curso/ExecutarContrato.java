package curso;

import java.io.File;
import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

public class ExecutarContrato {

	private static final String ACCOUNT = "0x9EC8265BF42aC8EDC18Df212e2B5EA9a9eD30b8e";
	private static final String PASSWORD = "teste";
	private static final String ENDERECO_CONTRATO = "0xC2dCa61F8d5429fF375AD24E418EB5C218B69F3a";
	final static Web3j web3 = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
	private static final String ENDEREO_ARQUIVO_PK = "/home/asimas/.local/share/io.parity.ethereum/keys/kovan/UTC--2018-03-11T13-09-26Z--71e5003b-434c-1f16-13f5-c65eca65e246";

	public static void main(String[] args) {
		lerVersao();
		
		/**
		 * Chama o método do contrato 
		 */
		ExecutarContrato e = new ExecutarContrato();
		e.callContract();
	}

	/**
	 * 
	 */
	private static void lerVersao() {
		try {
			Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println(clientVersion);
		}catch(Exception e) {}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public Credentials carregarCredenciais() throws Exception {
		File accountFile = new File(ENDEREO_ARQUIVO_PK);
		Credentials credentials = WalletUtils.loadCredentials(PASSWORD, accountFile);
		return credentials;
	}

	/**
	 * 
	 */
	public void callContract() {

		try {
			Credentials credentials = carregarCredenciais();
			HelloWorld contrato        = HelloWorld.load(ENDERECO_CONTRATO, web3, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
			contrato.setNome("JAVA").sendAsync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean unlockAccount() throws Exception {
		Admin admin = Admin.build(new HttpService()); 
		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(ACCOUNT, PASSWORD).sendAsync().get();
		return personalUnlockAccount.accountUnlocked();
	}

	/**
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private BigInteger getNonce(String address) throws Exception {
		Web3j web3 = Web3j.build(new HttpService());
		EthGetTransactionCount ethGetTransactionCount = web3
				.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		return ethGetTransactionCount.getTransactionCount();
	}

}
