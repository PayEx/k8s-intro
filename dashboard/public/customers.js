var dashboard = angular.module('dashboard', ['ngResource']);

dashboard.service('CustomerService', ['$q', '$resource', function($q, $resource) {
    var self = this;
    
    self.customers = $resource('/api/customers/:id', {id: '@id'}, {
        '$add': { method: 'POST' },
        '$delete': { method: 'DELETE' }
    });
    
    self.getAll = function() {
        return self.customers.query().$promise;
    };
    
    self.delete = function(id) {
        return self.customers.$delete({ id: id}).$promise;
    }
    
    self.add = function(customer) {
        return self.customers.$add(customer).$promise;
    };
}]);

dashboard.service('CartService', ['$q', '$resource', function($q, $resource) {
    var self = this;
    
    self.customers = $resource('/api/customers/:id/cart', {id: 'id'}, {
        '$add': { method: 'POST'},
        '$delete': { method: 'DELETE', hasBody: true, headers: {"Content-Type": "application/json;charset=UTF-8"} }
    });
    
    self.getForCustomer = function(id) {
        return self.customers.get({ id: id }).$promise;
    }
    
    self.delete = function(customerId, item) {
        var req = { item: item };
        
        return self.customers.$delete({ id: customerId }, req).$promise;
    };
    
    self.add = function(customerId, item) {
        var req = { item: item };
        
        return self.customers.$add({ id: customerId }, req).$promise;
    };
    
    return self;
}]);

dashboard.controller('CustomerCtrl', ['$scope', '$rootScope', 'CustomerService', function($scope, $rootScope, CustomerService) {
    $scope.customers = [];
    $scope.customer = {};
    
    var refreshState = function() {
        CustomerService.getAll()
        .then(function(customers) {
            $scope.customers = customers;
        })
        .catch(function(err) {
            px.toast({ html: 'Got error while fetching customers', type: 'danger' });
            console.error('Got error while fetching list of all customers', err);
        })
    }
    
    $scope.delete = function(id) {
        CustomerService.delete(id)
        .then(function() {
            px.toast({ html: 'Successfully deleted customer with id ' + id, type: 'success' });
            refreshState();
        })
        .catch(function(err) {
            px.toast({ html: 'Error while deleting customer with id ' + id, type: 'danger' });
            console.error("Error while deleting customer " + id, err);
        });
    }
    
    $scope.select = function(id) {
        $rootScope.$broadcast('cartForCustomer', $scope.customers.filter(function(c) {
            return c.id == id;
        })[0]);
    }
    
    $scope.add = function(customer) {
        console.info('Adding customer', customer);
        
        CustomerService.add(customer)
        .then(function(response) {
            $scope.customer = {};
            px.toast({ html: 'Successfully added customer', type: 'success' });
            refreshState();
        })
        .catch(function(err) {
            px.toast({ html: 'Error while adding customer', type: 'danger' });
            console.error("Error while adding customer", err);
        })
    }
    
    // Init with customers
    refreshState();
}]);

dashboard.controller('CartCtrl', ['$scope', 'CartService', function($scope, CartService) {
    $scope.customer = null;
    $scope.cart = null;
    $scope.item = '';
    
    $scope.$on('cartForCustomer', function(event, customer) {
        console.debug('Request to display cart for customer', customer);
        
        CartService.getForCustomer(customer.id)
        .then(function(cart) {
            $scope.cart = cart;
            $scope.customer = customer;
        })
        .catch(function(err) {
            px.toast({ html: 'Could not get cart for customer', type: 'danger' });
            console.error("Got error while fetching cart for customer " + id, err);
        });
    });
    
    $scope.delete = function(item) {
        console.debug('Trying to delete from cart', item);
        CartService.delete($scope.customer.id, item)
        .then(function(updatedCart) {
            px.toast({ html: 'Successfully removed "' + item + '" from cart', type: 'success' });
            $scope.cart = updatedCart;
        })
        .catch(function(err) {
            px.toast({ html: 'Could not delete item from cart', type: 'danger' });
            console.error('Got error while deleting item from cart', err);
        })
    }
    
    $scope.add = function(item) {
        CartService.add($scope.customer.id, item)
        .then(function(updatedCart) {
            $scope.cart = updatedCart;
            $scope.item = '';
            px.toast({ html: 'Successfully added "' + item + '"', type: 'success'});
        })
        .catch(function(err) {
            px.toast({ html: 'Could not add item to cart', type: 'danger' });
            console.error('Got error while adding item to cart', err);
        });
    }
}]);