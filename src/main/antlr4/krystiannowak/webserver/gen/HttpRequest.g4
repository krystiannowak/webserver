grammar HttpRequest;

// based on https://tools.ietf.org/html/rfc2616
// with quite big simplifications

fieldValue
	: ( fieldContent | SP )*
	;

fieldContent
	: TEXT
	;

generalHeader
	: connection
	;

request
	: requestLine
	( ( generalHeader | requestHeader ) CRLF )*
	CRLF
	;

requestLine
	: method SP requestURI SP httpVersion CRLF
	;

method
	: 'OPTIONS'
	| 'GET'
	| 'HEAD'
	| 'POST'
	| 'PUT'
	| 'DELETE'
	| 'TRACE'
	| 'CONNECT'
	| extensionMethod
	;

extensionMethod
	: TEXT
	;

requestURI
	: '*' | TEXT
	;

requestHeader
	: accept
	| acceptEncoding
	| acceptLanguage
	| host
	| userAgent
	;

httpVersion
	: HTTPVersion
	;
	
HTTPVersion
	: 'HTTP' '/' DIGIT '.' DIGIT
	;

connection
	: 'Connection:' fieldValue
	;

accept
	: 'Accept:' fieldValue
	;

acceptEncoding
	: 'Accept-Encoding:' fieldValue
	;
	
acceptLanguage
	: 'Accept-Language:' fieldValue
	;

host
	: 'Host:' fieldValue
	; 

userAgent
	: 'User-Agent:' fieldValue
	; 

fragment
CR
	: '\r'
	;

fragment
LF
	: '\n'
	;

SP
	: ' '
	;
	
fragment
HT
	: '\t'
	;

CRLF
	: CR LF
	;
 
LWS
	: CRLF? ( SP | HT )
	;

TEXT
	: [a-zA-Z0-9/.,:;*()_+-=?]+
	;

DIGIT
	: [0-9]
	;
