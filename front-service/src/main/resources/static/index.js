angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const corePath = 'http://localhost:5555/core';
    const cartPath = 'http://localhost:5555/cart';
    const authPath = 'http://localhost:5555/auth';

    $scope.tryToAuth = function () {
        $http.post(authPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.winterMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    };

    $scope.clearUser = function () {
        delete $localStorage.winterMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.winterMarketUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.authCheck = function () {
        $http.get(corePath + '/auth_check').then(function (response) {
            alert(response.data.value);
        });
    };

    if ($localStorage.winterMarketUser) {
        try {
            let jwt = $localStorage.winterMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.winterMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.winterMarketUser.token;
    }

    $scope.filter = function () {
        $scope.title = document.getElementById("title").value;
        $scope.minPrice = document.getElementById("minPrice").value;
        $scope.maxPrice = document.getElementById("maxPrice").value;
        $scope.loadProducts();
        $scope.title = null;
        $scope.minPrice = null;
        $scope.maxPrice = null;
    }

    $scope.loadProducts = function () {
        $http.get(corePath + '/api/v1/products/', $scope.title, $scope.minPrice, $scope.maxPrice).then(function (response) {
            $scope.productsList = response.data;
        });
    }


    $scope.showProductInfo = function (productId) {
        $http.get(corePath + '/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(corePath + '/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.loadCart = function () {
        $http.get(cartPath + '/api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.addToCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.increaseProductQuantityInCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/increase/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.decreaseProductQuantityInCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/decrease/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.deleteProductFromCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/delete/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http.get(cartPath + '/api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.makeOrder = function () {
        $http.post(corePath + '/api/v1/orders').then(function (response) {
            alert("Заказ создан");
            $scope.loadCart();
        });
    }

    $scope.loadProducts();
    $scope.loadCart();
});