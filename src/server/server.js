const http2 = require('http2');
const fs = require('fs');
 
function onRequest (req, resp) {
    resp.end("Hello World");
}
 
const server = http2.createSecureServer({
  key: fs.readFileSync('localhost-privkey.pem'),
  cert: fs.readFileSync('localhost-cert.pem')
}, onRequest);
 
server.listen(8443);