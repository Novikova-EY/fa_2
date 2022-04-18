angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {
    const corePath = 'http://localhost:5555/core';
    const cartPath = 'http://localhost:5555/cart';

    $scope.loadCart = function () {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.increaseProductQuantityInCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId + '/increase/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.decreaseProductQuantityInCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId + '/decrease/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.deleteProductFromCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId + '/delete/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId + '/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.makeOrder = function () {
        $http.post(corePath + '/api/v1/orders').then(function (response) {
            alert("Заказ создан");
            $scope.loadCart();
        });
    }

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();
});