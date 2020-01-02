const util = require('./util')

const LaunchRequestHandler = {
    canHandle(handlerInput) {
        return handlerInput.getRequestType(handlerInput.requestEnvelope) === 'LaunchRequest';
    },
    handle(handlerInput) {
        const { attributesManager } = handlerInput;
        const sessionAttributes = attributesManager.getSessionAttributes();

        const humedad = sessionAttributes['humedad'];
        const luz = sessionAttributes['luz'];
        const temperatura = sessionAttributes['temperatura'];
        const humedadAmbiente = sessionAttributes['humedadAmbiente'];
        const temperaturaAmbiente = sessionAttributes['temperaturaAmbiente'];

        const name = sessionAttributes['name'] ? sessionAttributes['name'] + '.' : '';
        let speechText = handlerInput.t('WELCOME_MSG', { name: name + '.' });

        const dataAvailable = humedad && luz && temperatura && humedadAmbiente && temperaturaAmbiente;
        if (dataAvailable) {
            speechText = handlerInput.t('REGISTER_MSG', { name: name, humedad: humedad, temperatura: temperatura, humedadAmbiente: humedadAmbiente, temperaturaAmbiente: temperaturaAmbiente }) + handlerInput.t('SHORT_HELP_MSG');
        } else {
            if (util.supportsAPL(handlerInput)) {
                speechText += handlerInput.t('MISSING_MSG');
            } else {
                handlerInput.responseBuilder.addDelegateDirective({
                    name: 'RegisterDataIntent',
                    confirmationStatus: 'NONE',
                    slots: {}
                })
            }
        }

        if (util.supportsAPL(handlerInput)) {
            handlerInput.responseBuilder.addDirective({
                type: 'Alexa.Presentation.APL.RenderDocument',
                version: '1.0',
                datasources: {
                    launchData: {
                        type: 'object',
                        properties: {
                            headerTitle: handlerInput.t('LAUNCH_HEADER_MSG'),
                            mainText: dataAvailable ? handlerInput.t('LAUNCH_TEXT_FILLED_MSG', { name: name, humedad: humedad, temperatura: temperatura, humedadAmbiente: humedadAmbiente, temperaturaAmbiente: temperaturaAmbiente })
                                : handlerInput.t('LAUNCH_TEXT_EMPTY_MSG'),
                            hintString: handlerInput.t('LAUNCH_HINT_MSG'),
                        },
                        transformers: [{ inputPath: 'hintString', transformer: 'textToHint', }]
                    },
                },
            });
        }

        return handlerInput.responseBuilder.speak(speechText).reprompt(handlerInput.t('HELP_MSG')).getResponse();
    }
};

const RegisterDataIntentHandler = {
    canHandle(handlerInput) {
        return handlerInput.requestEnvelope.request.type === 'IntentRequest'
            && handlerInput.requestEnvelope.request.intent.name === 'RegisterDataIntent';
    },
    handle(handlerInput) {
        const { attributesManager, requestEnvelope } = handlerInput;
        const sessionAttributes = attributesManager.getSessionAttributes();
        const { intent } = requestEnvelope.request;

        const humedad = intent.slots.humedad.value;
        const temperatura = intent.slots.temperatura.value;
        const luz = intent.slots.luz.value;
        const temperaturaAmbiente = intent.slots.temperaturaAmbiente.value;
        const humedadAmbiente = intent.slots.humedadAmbiente.value;

        sessionAttributes['humedad'] = humedad;
        sessionAttributes['luz'] = luz;
        sessionAttributes['temperatura'] = temperatura;
        sessionAttributes['humedadAmbiente'] = humedadAmbiente;
        sessionAttributes['temperaturaAmbiente'] = temperaturaAmbiente;
        const name = sessionAttributes['name'] ? sessionAttributes['name'] + '. ' : '';

        //coger datos de la base de datos

        const speechText = handlerInput.t('REGISTER_MSG', { name: name, humedad: humedad, temperatura: temperatura, humedadAmbiente: humedadAmbiente, temperaturaAmbiente: temperaturaAmbiente }) + handlerInput.t('SHORT_HELP_MSG');

        if (util.supportsAPL(handlerInput)) {
            handlerInput.responseBuilder.addDirective({
                type: 'Alexa.Presentation.APL.RenderDocument',
                version: '1.0',
                datasources: {
                    launchData: {
                        type: 'object',
                        properties: {
                            headerTitle: handlerInput.t('LAUNCH_HEADER_MSG'),
                            mainText: handlerInput.t('LAUNCH_TEXT_FILLED_MSG', { name: name, humedad: humedad, temperatura: temperatura, humedadAmbiente: humedadAmbiente, temperaturaAmbiente: temperaturaAmbiente }),
                            hintString: handlerInput.t('LAUNCH_HINT_MSG'),
                        },
                        transformers: [{
                            inputPath: 'hintString',
                            transformer: 'textToHint',
                        }]
                    },
                },
            });
        }

        return handlerInput.responseBuilder.speak(speechText).reprompt(handlerInput.t('HELP_MSG')).getResponse();
    }
};


const HelpIntentHandler = {
    canHandle(handlerInput) {
        return handlerInput.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' && handlerInput.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.HelpIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'Puedes saber informacion sobre el estado de la planta,';

        return handlerInput.responseBuilder.speak(speakOutput).reprompt(speakOutput).getResponse();
    }
};
const CancelAndStopIntentHandler = {
    canHandle(handlerInput) {
        return handlerInput.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' && (handlerInput.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.CancelIntent'
            || handlerInput.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.StopIntent');
    },
    handle(handlerInput) {
        const speakOutput = 'Adios';
        return handlerInput.responseBuilder.speak(speakOutput).getResponse();
    }
};

const SessionEndedRequestHandler = {
    canHandle(handlerInput) {
        return handlerInput.getRequestType(handlerInput.requestEnvelope) === 'SessionEndedRequest';
    },
    handle(handlerInput) {
        return handlerInput.responseBuilder.getResponse();
    }
};

const IntentReflectorHandler = {
    canHandle(handlerInput) {
        return handlerInput.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest';
    },
    handle(handlerInput) {
        const intentName = handlerInput.getIntentName(handlerInput.requestEnvelope);
        const speakOutput = `You just triggered ${intentName}`;

        return handlerInput.responseBuilder.speak(speakOutput).getResponse();
    }
};

const ErrorHandler = {
    canHandle() {
        return true;
    },
    handle(handlerInput, error) {
        console.log(`~~~~ Error handled: ${error.stack}`);
        const speakOutput = `Lo siento, a ocurrido un error. Por favor prueba otra vez.`;

        return handlerInput.responseBuilder.speak(speakOutput).reprompt(speakOutput).getResponse();
    }
};