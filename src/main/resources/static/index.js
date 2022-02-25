angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/api/v1/products').then(function (response) {
            $scope.productsList = response.data;
        });
    }

    $scope.showProductInfo = function (productId) {
        $http.get(contextPath + '/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.loadCart = function () {
        $http.get(contextPath + '/api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.increaseProductQuantityInCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/increase/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.decreaseProductQuantityInCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/decrease/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.deleteProductFromCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/delete/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http.get(contextPath + '/api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.loadProducts();
    $scope.loadCart();
});