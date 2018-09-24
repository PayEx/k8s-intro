const path = require('path')
const express = require('express')
const app = express()
const proxy = require('express-http-proxy')
const port = process.env.PORT || 3000

// Upstream services (discover connection strings by k8s injection of env variables)
const customerService = (process.env.CUSTOMER_SERVICE_SERVICE_HOST || 'localhost') + ':' + (process.env.CUSTOMER_SERVICE_SERVICE_PORT || 5000)
const cartService = (process.env.SHOPPING_CART_SERVICE_SERVICE_HOST || 'localhost') + ':' + (process.env.SHOPPING_CART_SERVICE_SERVICE_PORT || 3400)

// Routing
app.all('/api/customers', proxy(customerService))
app.all('/api/customers/:id', proxy(customerService))
app.all('/api/customers/:id/cart', proxy(cartService))

// Start serving requests
app.use(express.static(path.join(__dirname, 'public')))
app.listen(port, () => console.log(`Customer dashboard running on port ${port}!`))