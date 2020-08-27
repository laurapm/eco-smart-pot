/*
// Include MQTT Server
var mqtt = require('mqtt');

// MongoDB
var mongodb = require('mongodb');
var mongoClient = mongodb.MongoClient;
var mongoURI = 'mongodb://gardener:eco-app-plant@ch1r0n.duckdns.org:10072,ch1r0n.duckdns.org:20072,ch1r0n.duckdns.org:30072/admin';
*/

const mosca = require('mosca');
    function setupMqttServer() {
        var mongoUrl = "mongodb://127.0.0.1:27017/mqtt";
        var moscaSettings = {
        port: 1883,
        backend: {
            type: 'mongo',
            url: mongoUrl,
            pubsubCollection: 'moscadata',
            mongo: {}
        },
        persistence: {
            factory: mosca.persistence.Mongo,
            url: mongoUrl
        }
    };

    this.server = new mosca.Server(moscaSettings);
    this.server.on('ready', function () {
        console.log('Mosca server is up and running');
    });
    this.server.on('clientConnected', function (client) {
        console.log('client connected', client.id);
    });
    this.server.on('published', (packet, client) => {
        console.log('Message received : ', `topic-${packet.topic}`, `payload-${packet.payload.toString()}`);
    // You can process received message here.
    });
    this.server.on('subscribed', function (topic, client) {
      console.log('subscribed : ', topic);
    });
    this.server.on('unsubscribed', (topic, client) => {
        console.log('unsubscribed : ', topic);
    });
    this.server.on('clientDisconnecting', (client) => {
        console.log('clientDisconnecting : ', client.id);
    });
    this.server.on('clientDisconnected', (client) => {
        console.log('clientDisconnected : ', client.id);
    });
}

