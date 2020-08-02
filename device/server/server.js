const http2 = require('http2');
const fs = require('fs');
const mysql = require('mysql');

// Connect server to ESP32
function onRequest (req, resp) {
    resp.end("Hello World");
}

const server = http2.createSecureServer({
  key: fs.readFileSync('localhost-privkey.pem'),
  cert: fs.readFileSync('localhost-cert.pem')
}, onRequest);

server.listen(8443);

// Connect server to database
const db = mysql.createConnection({
  host: "???",
  user: "root",
  password: "root"
});

db.connect(function(err) {
  if (err) throw err;
  console.log("Connected");
}

