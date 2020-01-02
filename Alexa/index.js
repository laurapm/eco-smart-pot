const Alexa = require('ask-sdk-core');
const handlers = require('./handlers')
const persistence = require('./persistence');
const interceptors = require('./interceptors');

exports.handler = Alexa.SkillBuilders.custom()
    .addRequestHandlers(handlers.LaunchRequestHandler, handlers.RegisterDataIntentHandler,
        handlers.SayBirthdayIntentHandler, handlers.RemindBirthdayIntentHandler,
        handlers.HelpIntentHandler, handlers.CancelAndStopIntentHandler,
        handlers.FallbackIntentHandler, handlers.SessionEndedRequestHandler,
        handlers.IntentReflectorHandler)
    .addErrorHandlers(
        handlers.ErrorHandler)
    .addRequestInterceptors(
        interceptors.LocalisationRequestInterceptor, interceptors.LoggingRequestInterceptor,
        interceptors.LoadAttributesRequestInterceptor, interceptors.LoadNameRequestInterceptor,
        interceptors.LoadTimezoneRequestInterceptor)
    .addResponseInterceptors(
        interceptors.LoggingResponseInterceptor, interceptors.SaveAttributesResponseInterceptor)
    .withPersistenceAdapter(persistence.getPersistenceAdapter())
    .withApiClient(new Alexa.DefaultApiClient())
    .lambda();