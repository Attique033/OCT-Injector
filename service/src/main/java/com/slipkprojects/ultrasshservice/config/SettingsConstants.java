package com.slipkprojects.ultrasshservice.config;

public interface SettingsConstants
{

	// Geral
	public static final String
	AUTO_CLEAR_LOGS_KEY = "autoClearLogs",
	HIDE_LOG_KEY = "hideLog",
	MODO_DEBUG_KEY = "modeDebug",
	MODO_NOTURNO_KEY = "modeNight",
	BLOQUEAR_ROOT_KEY = "blockRoot",
	IDIOMA_KEY = "idioma",
	TETHERING_SUBNET = "tetherSubnet",
	DISABLE_DELAY_KEY = "disableDelaySSH",
	MAXIMO_THREADS_KEY = "numberMaxThreadSocks",
	CUSTOM_SNI ="customSNI",
	FILTER_APPS = "filterApps",
	FILTER_BYPASS_MODE = "filterBypassMode",
	FILTER_APPS_LIST = "filterAppsList",
	DNSRESOLVER_PRIMARY_KEY = "dnsResolver",
    DNSRESOLVER_SECONDARY_KEY = "dnsResolverSecondary",
	PROXY_IP_KEY = "proxyRemoto",
	PROXY_PORTA_KEY = "proxyRemotoPorta",
	CUSTOM_PAYLOAD_KEY = "proxyPayload",
	PROXY_USAR_DEFAULT_PAYLOAD = "usarDefaultPayload",
	PROXY_USAR_AUTENTICACAO_KEY = "usarProxyAutenticacao"
	;

	public static final String
	CONFIG_PROTEGER_KEY = "protegerConfig",
	CONFIG_MENSAGEM_KEY = "mensagemConfig",
	CONFIG_VALIDADE_KEY = "validadeConfig",
	CONFIG_MENSAGEM_EXPORTAR_KEY = "mensagemConfigExport",
	CONFIG_INPUT_PASSWORD_KEY = "inputPassword"
	;

	// Vpn
	public static final String
	DNSFORWARD_KEY = "dnsForward",
	DNSRESOLVER_KEY = "dnsResolver",
	UDPFORWARD_KEY = "udpForward",
	BYPASS_KEY = "bypassKey",
	UDPRESOLVER_KEY = "udpResolver";

	// SSH
	public static final String
	SERVIDOR_KEY = "sshServer",
	SERVIDOR_PORTA_KEY = "sshPort",
	USUARIO_KEY = "sshUser",
	SENHA_KEY = "sshPass",
	KEYPATH_KEY = "keyPath",
	PORTA_LOCAL_KEY = "sshPortaLocal",
	SSH_COMPRESSION = "data_compression",
	CHAVE_KEY = "chaveKey",
	NAMESERVER_KEY = "serverNameKey",
	DNS_KEY = "dnsKey",
	AUTO_PINGER = "auto_ping",
	PINGER_KEY = "pingerSSH";

	public static final String
	PAYLOAD_DEFAULT = "CONNECT [host_port] [protocol][crlf][crlf]",
	SSLTLS_DEFAULT = "com.google.com";

	// Tunnel Type
	public static final String
	TUNNELTYPE_KEY = "tunnelType",
	TUNNEL_TYPE_SSH_DIRECT = "sshDirect",
	TUNNEL_TYPE_SSH_PROXY = "sshProxy",
	TUNNEL_TYPE_SSH_SSLTUNNEL = "sshSslTunnel",
	TUNNEL_TYPE_PAY_SSL = "sslPayload"
	;

	public static final int
	bTUNNEL_TYPE_SSH_DIRECT = 1,
	bTUNNEL_TYPE_SSH_PROXY = 2,
	bTUNNEL_TYPE_SSL_TLS = 3,
	bTUNNEL_TYPE_PAY_SSL = 4,
	bTUNNEL_TYPE_SSH = 5,
	bTUNNEL_TYPE_DNS = 6,
	bTUNNEL_TYPE_SLOWDNS = 7;
	
}
