angular.module('market').controller('ordersController', function ($scope, $http) {
    const corePath = 'http://localhost:5555/core';

    $scope.loadOrders = function () {
        $http.get(corePath + '/api/v1/orders').then(function (response) {
            console.log(response);
            $scope.orders = response.data;
        });
    }

    $scope.loadOrders();
});