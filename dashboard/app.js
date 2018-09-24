const path = require('path')
const express = require('express')
const app = express()
const port = process.env.PORT || 3000
const serviceName = 'dashboard'

// Distributed tracing
const zipkinEndpoint = process.env.ZIPKIN_ENDPOINT || null;
const {ConsoleRecorder, BatchRecorder, Tracer, ExplicitContext, jsonEncoder: {JSON_V2}} = require('zipkin')
const {HttpLogger} = require('zipkin-transport-http');
const {wrapExpressHttpProxy} = require('zipkin-instrumentation-express')
const proxy = require('express-http-proxy')
const ctxImpl = new ExplicitContext();
const recorder = function() {
    if (!zipkinEndpoint) {
        console.log('ZIPKIN_ENDPOINT not defined - not shipping traces');
        return new ConsoleRecorder();
    }

    console.log('Shipping of distributed traces enabled to ' + zipkinEndpoint);

    return new BatchRecorder({
        logger: new HttpLogger({ endpoint: zipkinEndpoint, jsonEncoder: JSON_V2 })
      });
}();
const tracer = new Tracer({ctxImpl, recorder, serviceName});

// Upstream services (discover connection strings by k8s injection of env variables)
const customerService = (process.env.CUSTOMER_SERVICE_SERVICE_HOST || 'localhost') + ':' + (process.env.CUSTOMER_SERVICE_SERVICE_PORT || 5000)
const customerProxy = wrapExpressHttpProxy(proxy, { tracer: tracer, serviceName: serviceName, remoteServiceName: 'customer-service' })
const cartService = (process.env.SHOPPING_CART_SERVICE_SERVICE_HOST || 'localhost') + ':' + (process.env.SHOPPING_CART_SERVICE_SERVICE_PORT || 3400)
const cartProxy = wrapExpressHttpProxy(proxy, { tracer: tracer, serviceName: serviceName, remoteServiceName: 'shopping-cart-service' })


// Routing
app.all('/api/customers', customerProxy(customerService))
app.all('/api/customers/:id', customerProxy(customerService))
app.all('/api/customers/:id/cart', cartProxy(cartService))

// Health check
app.get('/healthz', (req, res) => res.send('All good in da sistahhood'))

// Start serving requests
app.use(express.static(path.join(__dirname, 'public')))
app.listen(port, () => console.log(`Customer dashboard running on port ${port}`))